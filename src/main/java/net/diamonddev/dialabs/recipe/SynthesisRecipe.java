package net.diamonddev.dialabs.recipe;

import net.diamonddev.dialabs.block.inventory.SynthesisInventory;
import net.diamonddev.dialabs.recipe.objects.ChancedEnchantment;
import net.diamonddev.dialabs.recipe.objects.CountedIngredient;
import net.diamonddev.dialabs.recipe.serializer.SynthesisRecipeSerializer;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.DataDrivenTagKeys;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SynthesisRecipe implements Recipe<SynthesisInventory> {

    public static final SynthesisRecipe PASSED_RECIPE = new SynthesisRecipe(
            null, null,
            null,
            0,
            null, null, null,
            null,
            0); // ALL NULL

    private final int payment;
    private final Enchantment result;
    private final int resultLvl;
    private final Identifier id;
    private final CountedIngredient inputA;
    private final CountedIngredient inputB;
    private final CountedIngredient inputC;
    private final ArrayList<ChancedEnchantment> chancedEnchants;
    private final ArrayList<String> modids;

    public SynthesisRecipe(
            Identifier id,
            @Nullable ArrayList<String> modRequirements,
            Enchantment resultEnchantment, int enchantmentLevel,
            CountedIngredient inputA, CountedIngredient inputB, CountedIngredient inputC,
            ArrayList<ChancedEnchantment> chancedEnchantments,
            int lapisRequirement
    ) {
        this.id = id;

        this.modids = modRequirements == null ? new ArrayList<>() : modRequirements;

        this.inputA = inputA;
        this.inputB = inputB;
        this.inputC = inputC;

        this.chancedEnchants = chancedEnchantments;

        this.payment = lapisRequirement;
        this.result = resultEnchantment;
        this.resultLvl = enchantmentLevel;
    }

    public boolean canLoad() {
        final boolean[] loadable = {true};
        if (!modids.isEmpty()) modids.forEach(s -> {
            if (loadable[0]) {
                loadable[0] = FabricLoaderImpl.INSTANCE.isModLoaded(s);
            }
        });
        return loadable[0];
    }

    // Inputs & Output
    public CountedIngredient getInputA() {
        return this.inputA;
    }

    public CountedIngredient getInputB() {
        return this.inputB;
    }

    public CountedIngredient getInputC() {
        return inputC;
    }

    public ArrayList<ChancedEnchantment> getChancedEnchantments() {
        return chancedEnchants;
    }

    public ArrayList<EnchantmentLevelEntry> getOutputRolledEnchants() {
        ArrayList<EnchantmentLevelEntry> eles = new ArrayList<>();
        for (ChancedEnchantment ce : this.getChancedEnchantments()) {
            EnchantmentLevelEntry ele = ce.rollAndGet();
            if (ele != null) {
                eles.add(ele);
            }
        }
        return eles;
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

    public ArrayList<String> getModids() {
        return modids;
    }

    // Matches
    @Override
    public boolean matches(SynthesisInventory inv, World world) {
        if (world.isClient) {
            return false;
        }

        return inv.getStack(0).getItem() == InitItem.SYNTHETIC_ENCHANTMENT_DISC &&
                getInputA().test(inv.getStack(2)) &&
                getInputB().test(inv.getStack(3)) && // CountedIngredient Checks
                getInputC().test(inv.getStack(4));

        // the lapis requirement is seperate
    }

    public boolean lapisReqMet(SynthesisInventory inv) {
        return getLapisRequirement() <= inv.getStack(1).getCount();
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


    public ArrayList<CountedIngredient> getCountedIngredients() {
        ArrayList<CountedIngredient> ingredients = new ArrayList<>();

       ingredients.add(new CountedIngredient(Ingredient.ofItems(InitItem.SYNTHETIC_ENCHANTMENT_DISC), 1));
       ingredients.add(new CountedIngredient(Ingredient.fromTag(DataDrivenTagKeys.SYNTHETIC_ENCHANTMENT_PAYMENT_ITEMS), getLapisRequirement()));
       ingredients.add(getInputA());
       ingredients.add(getInputB());
       ingredients.add(getInputC());

       return ingredients;
    }
}
