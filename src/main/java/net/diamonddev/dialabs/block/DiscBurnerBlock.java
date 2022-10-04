package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DiscBurnerBlock extends HorizontalRotationalBlock implements HorizontalRotationModelShape { //todo: voxelshape

    public DiscBurnerBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(5f, 8f));
    }

    private final Text TITLE = Text.translatable("block.dialabs.disc_burner.ui.title");

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                new DiscBurnerScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getNorthShape() {
        return null;
    }

    @Override
    public VoxelShape getEastShape() {
        return null;
    }

    @Override
    public VoxelShape getSouthShape() {
        return null;
    }

    @Override
    public VoxelShape getWestShape() {
        return null;
    }
}
