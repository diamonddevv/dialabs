package net.diamonddev.dialabs.nbt;

import net.minecraft.nbt.NbtCompound;

public class NBTIntComponent {

    private final String key;

    public NBTIntComponent(String key) {
        this.key = key;
    }


    public int read(NbtCompound nbt) {
      return nbt.getInt(key);
    }


    public void write(NbtCompound nbt, int i) {
        nbt.putInt(key, i);
    }
}
