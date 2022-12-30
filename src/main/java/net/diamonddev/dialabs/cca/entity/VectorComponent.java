package net.diamonddev.dialabs.cca.entity;

import net.diamonddev.dialabs.cca.Vec3dComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class VectorComponent implements Vec3dComponent {

    private final String key;
    private Vec3d value = Vec3d.ZERO;

    public VectorComponent(String key) {
        this.key = key;
    }


    @Override
    public Vec3d get() {
        return value;
    }

    @Override
    public void set(Vec3d vec) {
        value = vec;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        set(unpackVec(tag.getString(key)));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString(key, packVec(get()));
    }

    private static String packVec(Vec3d vec3d) {
        StringBuilder builder = new StringBuilder();

        builder.append(vec3d.x).append(",");
        builder.append(vec3d.y).append(",");
        builder.append(vec3d.z);

        return builder.toString();
    }

    private static Vec3d unpackVec(String string) {
        String[] vecs = string.split(",");
        return new Vec3d(Double.parseDouble(vecs[0]), Double.parseDouble(vecs[1]), Double.parseDouble(vecs[2]));
    }
}
