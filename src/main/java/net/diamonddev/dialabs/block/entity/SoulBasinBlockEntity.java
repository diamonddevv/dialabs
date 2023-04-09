package net.diamonddev.dialabs.block.entity;

import net.diamonddev.dialabs.registry.InitBlockEntity;
import net.diamonddev.dialabs.registry.InitResourceListener;
import net.diamonddev.dialabs.resource.InitDataResourceTypes;
import net.diamonddev.dialabs.resource.recipe.SoulFireEnrichmentRecipe;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoulBasinBlockEntity extends BlockEntity {

    private static final String NBT_ALPHA = "AlphaItem";
    private static final String NBT_BETA = "BetaItem";
    private static final String NBT_OUT = "OutItem";


    public ItemStack alphaStack;
    public ItemStack betaStack;

    public ItemStack outStack;
    public SoulBasinBlockEntity(BlockPos pos, BlockState state) { // probably needs another rework lol
        super(InitBlockEntity.SOUL_BASIN.blockEntityType(), pos, state);

        alphaStack = ItemStack.EMPTY;
        betaStack = ItemStack.EMPTY;
        outStack = ItemStack.EMPTY;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        alphaStack = getItemStack(nbt, NBT_ALPHA);
        betaStack = getItemStack(nbt, NBT_BETA);

        outStack = getItemStack(nbt, NBT_OUT);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        putItemStack(nbt, NBT_ALPHA, alphaStack);
        putItemStack(nbt, NBT_BETA, betaStack);

        putItemStack(nbt, NBT_OUT, outStack);
    }

    public void recipeTick(BlockState state) {
        CognitionResourceManager.forEachResource(InitResourceListener.DIALABS_RECIPES, InitDataResourceTypes.SOUL_FIRE_ENRICHING, recipe -> {
            Identifier alpha = recipe.getIdentifier(SoulFireEnrichmentRecipe.ALPHA_IN);
            Identifier beta = recipe.getIdentifier(SoulFireEnrichmentRecipe.BETA_IN);

            if (containsIdentifiedItem(alpha) && containsIdentifiedItem(beta)) {
                outStack = getStackFromIdentifier(recipe.getIdentifier(SoulFireEnrichmentRecipe.OUTPUT));
                outStack.setCount(calculateOutputCount(alphaStack, betaStack));

                alphaStack.setCount(alphaStack.getCount() - outStack.getCount());
                betaStack.setCount(betaStack.getCount() - outStack.getCount());

                if (alphaStack.getCount() <= 0) alphaStack = ItemStack.EMPTY;
                if (betaStack.getCount() <= 0) betaStack = ItemStack.EMPTY;
            }
        });
    }

    private boolean containsIdentifiedItem(Identifier id) {
        return getIdentifierFromStack(alphaStack) == id || getIdentifierFromStack(betaStack) == id;
    }


    public static int calculateOutputCount(ItemStack a, ItemStack b) {
        return Math.min(a.getCount(), b.getCount());
    }
    public static void tick(World world, BlockPos pos, BlockState state, SoulBasinBlockEntity blockEntity) {
        blockEntity.recipeTick(state);
    }
    private static ItemStack getStackFromIdentifier(Identifier id) {
        return new ItemStack(Registries.ITEM.get(id));
    }
    private static Identifier getIdentifierFromStack(ItemStack stack) {
        return Registries.ITEM.getId(stack.getItem());
    }

    public void addStack(ItemStack stack) {
        if (canAddStacks()) {
            if (alphaStack == ItemStack.EMPTY) {
                alphaStack = stack;
            } else {
                betaStack = stack;
            }
        }
    }

    public boolean canAddStacks() {
        return alphaStack.isEmpty() || betaStack.isEmpty();
    }
    public boolean hasStacks() {
        return !alphaStack.isEmpty() || !betaStack.isEmpty();
    }
    public boolean hasOutput() {
        return !this.outStack.isEmpty();
    }
    public ItemStack removeLastStack() {
        ItemStack ret;

        if (hasOutput()) {
            ret = outStack;
            outStack = ItemStack.EMPTY;
            return ret;
        }

        if (hasStacks()) {
            if (betaStack.isEmpty()) {
                ret = alphaStack;
                alphaStack = ItemStack.EMPTY;
                return ret;
            } else {
                ret = betaStack;
                betaStack = ItemStack.EMPTY;
                return ret;
            }
        } return null;
    }


    // nbtutil
    public static void putItemStack(NbtCompound nbt, String key, ItemStack stack) {
        NbtCompound compounded = new NbtCompound();
        compounded.putString("id", getIdentifierFromStack(stack).toString());
        compounded.putInt("count", stack.getCount());
        nbt.put(key, compounded);
    }
    public static ItemStack getItemStack(NbtCompound nbt, String key) {
        NbtCompound compounded = nbt.getCompound(key);
        Item item = Registries.ITEM.get(new Identifier(compounded.getString("id")));
        int count = compounded.getInt("count");


        return new ItemStack(item, count);
    }
}
