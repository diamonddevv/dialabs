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
    private final Identifier id;
    private final int cA;
    private final int cB;
    private final int cC;

    public SynthesisRecipe(
            Identifier id,
            Enchantment resultEnchantment, int enchantmentLevel,
            Ingredient inputA, Ingredient inputB, Ingredient inputC,
            int countA, int countB, int countC,
            int lapisRequirement) {

        this.id = id;

        this.inputA = inputA;
        this.inputB = inputB;
        this.inputC = inputC;

        this.cA = countA;
        this.cB = countB;
        this.cC = countC;

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

    public int getCountA() {
        return this.cA;
    }

    public int getCountB() {
        return this.cB;
    }

    public int getCountC() {
        return this.cC;
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
        if (world.isClient) {
            return false;
        }

        return inv.getStack(0).getItem() == InitItem.SYNTHETIC_ENCHANTMENT_DISC &&
                getInputA().test(inv.getStack(2)) &&
                getInputB().test(inv.getStack(3)) && // Ingredient Item Checks
                getInputC().test(inv.getStack(4)) &&
                getCountA() <= inv.getStack(2).getCount() &&
                getCountB() <= inv.getStack(3).getCount() && // Ingredient Count Checks
                getCountC() <= inv.getStack(4).getCount() &&
                getLapisRequirement() <= inv.getStack(1).getCount(); // Lapis Count Check
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
        return this.id;
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
