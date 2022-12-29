package net.diamonddev.dialabs.cca.entity;

import net.diamonddev.dialabs.cca.BoolComponent;
import net.minecraft.nbt.NbtCompound;

public class BooleanComponent implements BoolComponent {

    private final String key;

    public BooleanComponent(String key, boolean def) {
        this.key = key;

        this.bool = def;
    }

    private boolean bool;
    @Override
    public void readFromNbt(NbtCompound tag) {
        setComponent(tag.getBoolean(key));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean(key, isComponentTrue());
    }


    public boolean isComponentTrue() {
        return bool;
    }

    public void setComponent(boolean bool) {
        this.bool = bool;
    }
}
