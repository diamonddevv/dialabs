package net.diamonddev.dialabs.block;

import net.diamonddev.dialabs.item.TranslatedSynthesisTag;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equippable;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.stream.Stream;

public class SyntheticEnchantmentTomeBlock extends HorizontalRotationalBlock implements TranslatedSynthesisTag, Equippable {


    private final String key;
    public static final QuiltItemSettings TOME_ITEM_SETTINGS = new QuiltItemSettings();

    public SyntheticEnchantmentTomeBlock(String typeKey) {
        super(QuiltBlockSettings.create().sounds(BlockSoundGroup.WOOL));
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
                Block.createCuboidShape(4, 0.05, 5.025, 12, 1, 11.025),
                Block.createCuboidShape(3, 0.025, 5, 13, 0.025, 12),
                Block.createCuboidShape(3, 0.025, 5, 13, 1.025, 5),
                Block.createCuboidShape(3, 1.025, 5, 13, 1.025, 12),
                Block.createCuboidShape(7, 1.026, 8, 9, 1.026, 10)
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

    @Override
    public EquipmentSlot getPreferredSlot() {
        return EquipmentSlot.HEAD;
    }
}
