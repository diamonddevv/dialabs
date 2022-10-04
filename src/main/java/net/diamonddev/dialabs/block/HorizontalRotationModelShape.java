package net.diamonddev.dialabs.block;

import net.minecraft.util.shape.VoxelShape;

public interface HorizontalRotationModelShape {
    VoxelShape getNorthShape();
    VoxelShape getEastShape();
    VoxelShape getSouthShape();
    VoxelShape getWestShape();
}
