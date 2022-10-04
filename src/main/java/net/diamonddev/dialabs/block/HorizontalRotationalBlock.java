package net.diamonddev.dialabs.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;

public abstract class HorizontalRotationalBlock extends HorizontalFacingBlock {
    protected HorizontalRotationalBlock(Settings settings) {
        super(settings);
    }

    public boolean shouldPlaceOpposite() {
        return false;
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, shouldPlaceOpposite() ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerFacing());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
