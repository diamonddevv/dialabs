package net.diamonddev.dialabs.cca.entity;

import net.diamonddev.dialabs.cca.DecimalComponent;
import net.minecraft.nbt.NbtCompound;

public class DoubleComponent implements DecimalComponent {

    private final String key;

    public DoubleComponent(String key) {
        this.key = key;
    }

    private double num = 0d;

    @Override
    public void readFromNbt(NbtCompound tag) {
        setValue(tag.getDouble(key));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble(key, getValue());
    }


    public double getValue() {
        return num;
    }

    public void setValue(double d) {
        this.num = d;
    }
}
