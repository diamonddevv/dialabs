package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.block.entity.SoulBasinBlockEntity;
import net.diamonddev.dialabs.registry.InitBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.stream.Stream;

public class SoulBasinBlock extends HorizontalRotationBlockWithEntity {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public SoulBasinBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, shouldPlaceOpposite() ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerFacing()).with(LIT, false);
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

            if (soulBasinBlockEntity.canAddStacks()) {
                soulBasinBlockEntity.addStack(itemStack);
                player.setStackInHand(hand, ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }

        }

        return ActionResult.PASS;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SoulBasinBlockEntity soulBasinBlockEntity) {
                ArrayList<ItemStack> stacks = new ArrayList<>();
                stacks.add(soulBasinBlockEntity.alphaStack);
                stacks.add(soulBasinBlockEntity.betaStack);
                stacks.add(soulBasinBlockEntity.outStack);
                scatterItems(world, pos, stacks);
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, InitBlockEntity.SOUL_BASIN.blockEntityType(), SoulBasinBlockEntity::tick) : null;
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

    // ROTATABLE

    @Override
    public VoxelShape getNorthShape() {
         return Stream.of(
                 VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 1, 0, 16, 2, 16), Stream.of(
                        Block.createCuboidShape(0, 2, 0, 16, 8, 1),
                        Block.createCuboidShape(0, 2, 15, 16, 8, 16),
                        Block.createCuboidShape(0, 2, 1, 1, 8, 15),
                        Block.createCuboidShape(15, 2, 1, 16, 8, 15)
                 ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(), BooleanBiFunction.OR), Stream.of(
                         Block.createCuboidShape(13, 0, 1, 15, 1, 3),
                         Block.createCuboidShape(13, 0, 13, 15, 1, 15),
                         Block.createCuboidShape(1, 0, 13, 3, 1, 15),
                         Block.createCuboidShape(1, 0, 1, 3, 1, 3)
                 ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
                 Block.createCuboidShape(1, 2, 1, 15, 6, 15)
         ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getEastShape() {
        return getNorthShape();
    }

    @Override
    public VoxelShape getSouthShape() {
        return getNorthShape();
    }

    @Override
    public VoxelShape getWestShape() {
        return getEastShape();
    }
}
