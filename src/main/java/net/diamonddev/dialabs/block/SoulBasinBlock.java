package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.block.entity.SoulBasinBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SoulBasinBlock extends BlockWithEntity {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public SoulBasinBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SoulBasinBlockEntity soulBasinBlockEntity) {
            ItemStack itemStack = player.getStackInHand(hand);

            if (itemStack.isEmpty()) {

                if (soulBasinBlockEntity.hasOutput()) {
                    player.setStackInHand(hand, soulBasinBlockEntity.removeLastStack());
                    return ActionResult.SUCCESS;
                }

                if (soulBasinBlockEntity.hasStacks()) player.setStackInHand(hand, soulBasinBlockEntity.removeLastStack());
                return ActionResult.SUCCESS;
            }

            soulBasinBlockEntity.addStack(itemStack);
            player.setStackInHand(hand, ItemStack.EMPTY);
            soulBasinBlockEntity.recipe();
            return ActionResult.SUCCESS;

        }

        return ActionResult.PASS;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SoulBasinBlockEntity soulBasinBlockEntity) {
                scatterItems(world, pos, soulBasinBlockEntity.stacks);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    // BLOCK ENTITY STUFF

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoulBasinBlockEntity(pos, state);
    }



    // STATE STUFF

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(LIT);
    }

    // MISC UTIL
    public static void scatterItems(World world, BlockPos pos, ArrayList<ItemStack> stacks) {
        stacks.forEach((stack) -> ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }
}
