package net.diamonddev.dialabs.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.recipe.objects.CountedIngredient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SynthesisRecipeSerializer implements RecipeSerializer<SynthesisRecipe> {

    private SynthesisRecipeSerializer() {}
    public static final SynthesisRecipeSerializer INSTANCE = new SynthesisRecipeSerializer();
    public static final String ID = SynthesisRecipe.Type.ID;


    @Override // Turns JSON into Recipe
    public SynthesisRecipe read(Identifier id, JsonObject json) {
        SynthesisRecipeJsonFormat format = new Gson().fromJson(json, SynthesisRecipeJsonFormat.class);

        if (format.level == 0) format.level = 1;

        CountedIngredient inputA = CountedIngredient.fromJson(format.inputA);
        CountedIngredient inputB = CountedIngredient.fromJson(format.inputB);
        CountedIngredient inputC = CountedIngredient.fromJson(format.inputC);

        if (inputA == CountedIngredient.EMPTY && inputB == CountedIngredient.EMPTY && inputC == CountedIngredient.EMPTY) {
            throw new JsonSyntaxException("Recipe '" + id + "' invalid; No defined ingredients found!");
        }

        int lapis = format.lapis_count;
        int level = format.level;
        Enchantment ench = readEnchantment(format);

        return new SynthesisRecipe(id, ench, level, inputA, inputB, inputC, lapis);
    }

    @Override // Turns PacketByteBuf into Recipe
    public SynthesisRecipe read(Identifier id, PacketByteBuf buf) {
        CountedIngredient a = CountedIngredient.fromPacket(buf);
        CountedIngredient b = CountedIngredient.fromPacket(buf);
        CountedIngredient c = CountedIngredient.fromPacket(buf);

        int lapis = buf.readInt();
        Enchantment enchantment = buf.readRegistryValue(Registry.ENCHANTMENT);
        int lvl = buf.readInt();

        return new SynthesisRecipe(id, enchantment, lvl, a, b, c, lapis);
    }

    @Override // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf buf, SynthesisRecipe recipe) {
        recipe.getInputA().writeToPacketBuf(buf);
        recipe.getInputB().writeToPacketBuf(buf);
        recipe.getInputC().writeToPacketBuf(buf);

        buf.writeInt(recipe.getLapisRequirement());
        buf.writeRegistryValue(Registry.ENCHANTMENT, recipe.getResultEnchantment());
        buf.writeInt(recipe.getResultLvl());
    }

    private Enchantment readEnchantment(SynthesisRecipeJsonFormat format) {
        Enchantment candidate = Registry.ENCHANTMENT.getOrEmpty(new Identifier(format.enchantment)).orElseThrow(() ->
                new JsonSyntaxException("No such valid enchantment in registry: " + format.enchantment));

        if (SyntheticEnchantment.validSyntheticEnchantments.contains(candidate)) {
            return candidate;
        } else {
            throw new JsonSyntaxException("Enchantment " + format.enchantment + " was found in registry, but was not validly considered Synthetic!");
        }
    }


    static class SynthesisRecipeJsonFormat {
        JsonObject inputA;
        JsonObject inputB;
        JsonObject inputC;

        int lapis_count;
        String enchantment;
        int level;
    }
}
