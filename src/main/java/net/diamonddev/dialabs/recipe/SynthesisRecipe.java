package net.diamonddev.dialabs.recipe;

import net.diamonddev.dialabs.block.inventory.SynthesisInventory;
import net.diamonddev.dialabs.recipe.serializer.SynthesisRecipeSerializer;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SynthesisRecipe implements Recipe<SynthesisInventory> {

    private final Ingredient inputA;
    private final Ingredient inputB;
    private final Ingredient inputC;
    private final int payment;
    private final Enchantment result;
    private final int resultLvl;

    public SynthesisRecipe(Enchantment resultEnchantment, int enchantmentLevel, Ingredient inputA, Ingredient inputB, Ingredient inputC, int lapisRequirement) {
        this.inputA = inputA;
        this.inputB = inputB;
        this.inputC = inputC;
        this.payment = lapisRequirement;
        this.result = resultEnchantment;
        this.resultLvl = enchantmentLevel;
    }

    // Inputs & Output

    public Ingredient getInputA() {
        return this.inputA;
    }

    public Ingredient getInputB() {
        return this.inputB;
    }

    public Ingredient getInputC() {
        return inputC;
    }

    public int getLapisRequirement() {
        return payment;
    }

    public Enchantment getResultEnchantment() {
        return result;
    }

    public int getResultLvl() {
        return resultLvl;
    }

    @Override
    public ItemStack getOutput() {
        ItemStack stack = new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC);
        EnchantHelper.storeEnchantment(stack, new EnchantmentLevelEntry(getResultEnchantment(), getResultLvl()));
        return stack;
    }

    // Matches
    @Override
    public boolean matches(SynthesisInventory inv, World world) {
        if (inv.size() < 5) return false;
        return inputA.test(inv.getStack(3)) &&
                inputB.test(inv.getStack(4)) &&
                inputC.test(inv.getStack(5)) &&
                inv.getStack(2).getCount() >= getLapisRequirement();
    }

    // misc stuff lol
    @Override
    public ItemStack craft(SynthesisInventory inv) {
        return this.getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public Identifier getId() {
        return new net.diamonddev.dialabs.api.Identifier(Type.ID);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SynthesisRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SynthesisRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "enchantment_synthesis";
    }
}
