package net.diamonddev.dialabs.nbt;

import net.minecraft.nbt.NbtCompound;

public class NbtIntComponent extends NbtComponent<Integer> {
    public NbtIntComponent(String key) {
        super(key);
    }
    public Integer read(NbtCompound nbt) {
      return nbt.getInt(key);
    }

    @Override
    public void write(NbtCompound nbt, Integer data) {
        nbt.putInt(key, data);
    }
}
