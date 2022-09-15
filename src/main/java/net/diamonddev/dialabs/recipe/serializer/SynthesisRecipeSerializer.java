package net.diamonddev.dialabs.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
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

        Ingredient inputA = Ingredient.fromJson(format.inputA);
        Ingredient inputB = Ingredient.fromJson(format.inputB);
        Ingredient inputC = Ingredient.fromJson(format.inputC);

        if (format.countA == 0 && inputA != Ingredient.EMPTY) format.countA = 1;
        if (format.countB == 0 && inputB != Ingredient.EMPTY) format.countB = 1;
        if (format.countC == 0 && inputC != Ingredient.EMPTY) format.countC = 1;

        int cA = format.countA;
        int cB = format.countB;
        int cC = format.countC;

        int lapis = format.lapis_count;
        int level = format.level;
        Enchantment ench = readEnchantment(format);

        return new SynthesisRecipe(id, ench, level, inputA, inputB, inputC, cA, cB, cC,  lapis);
    }

    @Override // Turns PacketByteBuf into Recipe
    public SynthesisRecipe read(Identifier id, PacketByteBuf buf) {

        Ingredient a = Ingredient.fromPacket(buf);
        Ingredient b = Ingredient.fromPacket(buf);
        Ingredient c = Ingredient.fromPacket(buf);

        int ca = buf.readInt();
        int cb = buf.readInt();
        int cc = buf.readInt();

        int lapis = buf.readInt();
        Enchantment enchantment = buf.readRegistryValue(Registry.ENCHANTMENT);
        int lvl = buf.readInt();


        return new SynthesisRecipe(id, enchantment, lvl, a, b, c, ca, cb, cc, lapis);
    }

    @Override // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf buf, SynthesisRecipe recipe) {
        recipe.getInputA().write(buf);
        recipe.getInputB().write(buf);
        recipe.getInputC().write(buf);

        buf.writeInt(recipe.getCountA());
        buf.writeInt(recipe.getCountB());
        buf.writeInt(recipe.getCountC());

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

        int countA;
        int countB;
        int countC;

        int lapis_count;
        String enchantment;
        int level;
    }
}
