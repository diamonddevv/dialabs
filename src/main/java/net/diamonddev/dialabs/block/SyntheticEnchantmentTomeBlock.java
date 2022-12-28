package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.item.TranslatedSynthesisTag;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.stream.Stream;

public class SyntheticEnchantmentTomeBlock extends HorizontalRotationalBlock implements TranslatedSynthesisTag, Wearable {


    private final String key;
    public static final FabricItemSettings TOME_ITEM_SETTINGS = new FabricItemSettings().equipmentSlot(new TomeEquipmentSlotProvider());

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
        return getNorthShape();
    }

    @Override
    public VoxelShape getWestShape() {
        return getEastShape();
    }


    public static class TomeEquipmentSlotProvider implements EquipmentSlotProvider {
        @Override
        public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.HEAD;
        }
    }
}
