package net.diamonddev.dialabs.util;

import net.minecraft.util.math.Direction;

public class DDVMathHelper {

    public static float degToRad(float deg) {
        return (float) (deg * (Math.PI / 180));
    }

    public static float directionToRad(Direction direction) {
        return degToRad(switch (direction) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 360;
        });
    }

}
