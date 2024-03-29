package net.diamonddev.dialabs.recipe.serializer;

import com.google.gson.*;
import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.recipe.objects.ChancedEnchantment;
import net.diamonddev.dialabs.recipe.objects.CountedIngredient;
import net.diamonddev.dialabs.recipe.objects.MiscObjectSerializers;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class SynthesisRecipeSerializer implements RecipeSerializer<SynthesisRecipe> {
    private SynthesisRecipeSerializer() {}
    public static final SynthesisRecipeSerializer INSTANCE = new SynthesisRecipeSerializer();
    public static final String ID = SynthesisRecipe.Type.ID;


    @Override // Turns JSON into Recipe
    public SynthesisRecipe read(Identifier id, JsonObject json) {
        // Pass JSON through Format
        SynthesisRecipeJsonFormat format = new Gson().fromJson(json, SynthesisRecipeJsonFormat.class);

        if (format.level == 0) format.level = 1;

        CountedIngredient inputA = CountedIngredient.fromJson(format.inputA);
        CountedIngredient inputB = CountedIngredient.fromJson(format.inputB);
        CountedIngredient inputC = CountedIngredient.fromJson(format.inputC);

        ArrayList<ChancedEnchantment> chancedEnchantments = new ArrayList<>();

        if (format.chancedEnchantments != null) {
            for (JsonElement obj : format.chancedEnchantments) {
                ChancedEnchantment chancedEnchantment = ChancedEnchantment.fromJson(obj);
                if (chancedEnchantment != ChancedEnchantment.EMPTY) {
                    chancedEnchantments.add(chancedEnchantment);
                }
            }
        }

        if (inputA == CountedIngredient.EMPTY && inputB == CountedIngredient.EMPTY && inputC == CountedIngredient.EMPTY) {
            throw new JsonSyntaxException("Recipe '" + id + "' invalid; No defined ingredients found!");
        }

        int lapis = format.lapis_count;
        int level = format.level;
        Enchantment ench = readEnchantment(format);

        ArrayList<String> modids = new ArrayList<>();
        if (format.modRequirementIds != null) format.modRequirementIds.forEach(element -> modids.add(element.getAsString()));

        return new SynthesisRecipe(id, modids, ench, level, inputA, inputB, inputC, chancedEnchantments, lapis);
    }

    @Override // Turns PacketByteBuf into Recipe
    public SynthesisRecipe read(Identifier id, PacketByteBuf buf) {
        CountedIngredient a = CountedIngredient.fromPacket(buf);
        CountedIngredient b = CountedIngredient.fromPacket(buf);
        CountedIngredient c = CountedIngredient.fromPacket(buf);

        ArrayList<ChancedEnchantment> chances = ChancedEnchantment.readArrayListPacket(buf);

        int lapis = buf.readInt();
        Enchantment enchantment = MiscObjectSerializers.readEnchantment(buf);
        int lvl = buf.readInt();

        ArrayList<String> strings = MiscObjectSerializers.readStringArray(buf);

        return new SynthesisRecipe(id, strings, enchantment, lvl, a, b, c, chances, lapis);
    }

    @Override // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf buf, SynthesisRecipe recipe) {
        recipe.getInputA().toPacket(buf);
        recipe.getInputB().toPacket(buf);
        recipe.getInputC().toPacket(buf);

        ChancedEnchantment.writeArrayListPacket(recipe.getChancedEnchantments(), buf);

        buf.writeInt(recipe.getLapisRequirement());
        MiscObjectSerializers.writeEnchantment(buf, recipe.getResultEnchantment());
        buf.writeInt(recipe.getResultLvl());

        MiscObjectSerializers.writeStringArray(buf, recipe.getModids());
    }

    private Enchantment readEnchantment(SynthesisRecipeJsonFormat format) {

        boolean canLoad = true;
        if (format.modRequirementIds != null) {
            for (JsonElement element : format.modRequirementIds) {
                String id = element.getAsString();
                if (canLoad) {
                    canLoad = FabricLoaderImpl.INSTANCE.isModLoaded(id);
                }
            }
        }

        if (canLoad) {
            Enchantment candidate = Registries.ENCHANTMENT.getOrEmpty(new Identifier(format.enchantment)).orElseThrow(() ->
                    new JsonSyntaxException("No such valid enchantment in registry: " + format.enchantment));

            if (SyntheticEnchantment.validSyntheticEnchantments.contains(candidate)) {
                return candidate;
            } else {
                throw new JsonSyntaxException("Enchantment " + format.enchantment + " was found in registry, but was not validly considered Synthetic!");
            }
        }
        return null;
    }


    static class SynthesisRecipeJsonFormat {
        JsonObject inputA;
        JsonObject inputB;
        JsonObject inputC;

        JsonArray chancedEnchantments;

        int lapis_count;
        String enchantment;
        int level;
        JsonArray modRequirementIds;
    }
}
