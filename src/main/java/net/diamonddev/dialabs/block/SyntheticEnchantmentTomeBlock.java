package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.item.TranslatedSynthesisTag;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;

public class SyntheticEnchantmentTomeBlock extends HorizontalRotationalBlock implements TranslatedSynthesisTag, Wearable, HorizontalRotationModelShape {


    private final String key;
    public static final FabricItemSettings TOME_ITEM_SETTINGS = new FabricItemSettings()
            .group(ItemGroup.MISC).equipmentSlot(new TomeEquipmentSlotProvider());


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Generated VoxelShape with Blockbench and Modding Utils Plugin
        // Its Imperfect on rotations, so i might fix it or just make it a square that fits each rotation. I don't know, i doubt it matters much
        return switch (state.get(FACING)) {
            case SOUTH -> getSouthShape();
            case WEST -> getWestShape();
            case EAST -> getEastShape();
            default -> getNorthShape();
        };
    }

    public SyntheticEnchantmentTomeBlock(String typeKey) {
        super(FabricBlockSettings.of(Material.DECORATION));
        this.key = typeKey;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public String getSynthesisUiTranslationKey() {
        return "synthesis.dialabs." + key + "_tome";
    }

    @Override
    public VoxelShape getNorthShape() {
        return Stream.of(
                Block.createCuboidShape(5.025, 0.05, 4, 11.025, 1, 12),
                Block.createCuboidShape(5, 0.025, 3, 12, 0.025, 13),
                Block.createCuboidShape(5, 0.025, 3, 5, 1.025, 13),
                Block.createCuboidShape(5, 1.025, 3, 12, 1.025, 13),
                Block.createCuboidShape(8, 1.026, 7, 10, 1.026, 9),
                Block.createCuboidShape(8, 1.026, 7, 10, 1.026, 9)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getEastShape() {
        return Stream.of(
                Block.createCuboidShape(3, 0.05, 6.025, 11, 1, 12.025),
                Block.createCuboidShape(2, 0.025, 6, 12, 0.025, 13),
                Block.createCuboidShape(2, 0.025, 6, 12, 1.025, 6),
                Block.createCuboidShape(2, 1.025, 6, 12, 1.025, 13),
                Block.createCuboidShape(6, 1.026, 9, 8, 1.026, 11)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getSouthShape() {
        return Stream.of(
                Block.createCuboidShape(2.9749999999999996, 0.05, 4, 8.975, 1, 12),
                Block.createCuboidShape(2, 0.025, 3, 9, 0.025, 13),
                Block.createCuboidShape(9, 0.025, 3, 9, 1.025, 13),
                Block.createCuboidShape(2, 1.025, 3, 9, 1.025, 13),
                Block.createCuboidShape(4, 1.026, 7, 6, 1.026, 9)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getWestShape() {
        return Stream.of(
                Block.createCuboidShape(3, 0.05, 3.9749999999999996, 11, 1, 9.975),
                Block.createCuboidShape(2, 0.025, 3, 12, 0.025, 10),
                Block.createCuboidShape(2, 0.025, 10, 12, 1.025, 10),
                Block.createCuboidShape(2, 1.025, 3, 12, 1.025, 10),
                Block.createCuboidShape(6, 1.026, 5, 8, 1.026, 7)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }


    public static class TomeEquipmentSlotProvider implements EquipmentSlotProvider {
        @Override
        public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.HEAD;
        }
    }
}
