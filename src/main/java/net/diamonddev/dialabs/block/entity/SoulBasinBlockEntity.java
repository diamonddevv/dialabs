package net.diamonddev.dialabs.block.entity;

import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeManager;
import net.diamonddev.dialabs.recipe.ddv.SoulFireEnrichmentRecipe;
import net.diamonddev.dialabs.registry.InitBlockEntity;
import net.diamonddev.dialabs.registry.InitRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class SoulBasinBlockEntity extends BlockEntity {

    public static final int ALPHA_INDEX = 0;
    public static final int BETA_INDEX = 1;


    public final ArrayList<ItemStack> stacks = new ArrayList<>();
    public ItemStack outStack;
    public SoulBasinBlockEntity(BlockPos pos, BlockState state) {
        super(InitBlockEntity.SOUL_BASIN.blockEntityType(), pos, state);

        outStack = ItemStack.EMPTY;
    }


    public void recipe() {
        DialabsRecipeManager.forEachRecipe(InitRecipe.SOUL_FIRE_ENRICHING, recipe -> {
            Identifier alpha = recipe.getIdentifier(SoulFireEnrichmentRecipe.ALPHA_IN);
            Identifier beta = recipe.getIdentifier(SoulFireEnrichmentRecipe.BETA_IN);

            if (containsIdentifiedItem(alpha) && containsIdentifiedItem(beta)) {
                outStack = getStackFromIdentifier(recipe.getIdentifier(SoulFireEnrichmentRecipe.OUTPUT));
                stacks.clear();
            }
        });
    }

    private boolean containsIdentifiedItem(Identifier identifier) {
        return stacks.stream().map(stack -> Registries.ITEM.getId(stack.getItem())).anyMatch(id -> id == identifier);
    }

    private ItemStack getStackFromIdentifier(Identifier id) {
        return new ItemStack(Registries.ITEM.get(id));
    }

    public void addStack(ItemStack stack) {
        if (!(stacks.size() >= 2)) {
            stacks.add(stack);
        }
    }
    public boolean hasStacks() {
        return !this.stacks.isEmpty();
    }
    public boolean hasOutput() {
        return !this.outStack.isEmpty();
    }
    public ItemStack removeLastStack() {
        if (hasOutput()) {
            ItemStack ret = outStack;
            outStack = ItemStack.EMPTY;
            return ret;
        }
        return stacks.remove(stacks.size() - 1);
    }
}
