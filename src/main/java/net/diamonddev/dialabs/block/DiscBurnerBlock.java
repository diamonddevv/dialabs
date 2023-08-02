package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.stream.Stream;

public class DiscBurnerBlock extends HorizontalRotationalBlock {

    public DiscBurnerBlock() {
        super(QuiltBlockSettings.create().sounds(BlockSoundGroup.METAL).strength(5f, 8f));
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
        return Stream.of(
                Block.createCuboidShape(7, 2, 9, 9, 9, 10),
                Block.createCuboidShape(7, 2, 6, 9, 11, 7),
                Block.createCuboidShape(3, 9, 3, 13, 11, 13),
                Block.createCuboidShape(2, 0, 3, 14, 2, 13),
                Block.createCuboidShape(6, 2, 7, 10, 10, 9)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getEastShape() {
        return Stream.of(
                Block.createCuboidShape(6, 2, 7, 7, 9, 9),
                Block.createCuboidShape(9, 2, 7, 10, 11, 9),
                Block.createCuboidShape(4.530733729460355, 8.695518130045151, 3.000000000000001, 14.530733729460358, 10.695518130045151, 13),
                Block.createCuboidShape(3, 0, 2, 13, 2, 14),
                Block.createCuboidShape(7, 2, 6, 9, 10, 10)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getSouthShape() {
        return Stream.of(
                Block.createCuboidShape(7, 2, 6, 9, 9, 7),
                Block.createCuboidShape(7, 2, 9, 9, 11, 10),
                Block.createCuboidShape(3, 8.695518130045151, 4.530733729460355, 13, 10.695518130045151, 14.530733729460358),
                Block.createCuboidShape(2, 0, 3, 14, 2, 13),
                Block.createCuboidShape(6, 2, 7, 10, 10, 9)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getWestShape() {
        return Stream.of(
                Block.createCuboidShape(9, 2, 7, 10, 9, 9),
                Block.createCuboidShape(6, 2, 7, 7, 11, 9),
                Block.createCuboidShape(1.4692662705396415, 8.695518130045151, 3, 11.469266270539645, 10.695518130045151, 13),
                Block.createCuboidShape(3, 0, 2, 13, 2, 14),
                Block.createCuboidShape(7, 2, 6, 9, 10, 10)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}
