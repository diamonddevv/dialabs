package net.diamonddev.dialabs.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SynthesisRecipeSerializer implements RecipeSerializer<SynthesisRecipe> {

    private SynthesisRecipeSerializer() {}
    public static final SynthesisRecipeSerializer INSTANCE = new SynthesisRecipeSerializer();
    public static final Identifier ID = new net.diamonddev.dialabs.api.Identifier("enchantment_synthesis");


    @Override // Turns JSON into Recipe
    public SynthesisRecipe read(Identifier id, JsonObject json) {
        SynthesisRecipeJsonFormat format = new Gson().fromJson(json, SynthesisRecipeJsonFormat.class);

        if (format.lapis_count == 0) format.lapis_count = 1;
        if (format.level == 0) format.level = 1;

        Ingredient inputA = Ingredient.fromJson(format.inputA);
        Ingredient inputB = Ingredient.fromJson(format.inputB);
        Ingredient inputC = Ingredient.fromJson(format.inputC); // todo: Make it possible to not use all three slots in a non-janky way

        int lapis = format.lapis_count;
        int level = format.level;
        Enchantment ench = Registry.ENCHANTMENT.getOrEmpty(new Identifier(format.enchantment)).orElseThrow(() ->
                new JsonSyntaxException("No such valid synthetic enchantment: " + format.enchantment +""));

        return new SynthesisRecipe(ench, level, inputA, inputB, inputC, lapis);
    }

    @Override // Turns PacketByteBuf into Recipe
    public SynthesisRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient a = Ingredient.fromPacket(buf);
        Ingredient b = Ingredient.fromPacket(buf);
        Ingredient c = Ingredient.fromPacket(buf);
        int lapis = buf.readInt();
        Enchantment enchantment = buf.readRegistryValue(Registry.ENCHANTMENT);
        int lvl = buf.readInt();
        return new SynthesisRecipe(enchantment, lvl, a, b, c, lapis);
    }

    @Override // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf buf, SynthesisRecipe recipe) {
        recipe.getInputA().write(buf);
        recipe.getInputB().write(buf);
        recipe.getInputC().write(buf);
        buf.writeInt(recipe.getLapisRequirement());
        buf.writeRegistryValue(Registry.ENCHANTMENT, recipe.getResultEnchantment());
        buf.writeInt(recipe.getResultLvl());
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
