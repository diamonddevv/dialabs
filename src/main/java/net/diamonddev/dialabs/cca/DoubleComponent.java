package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class DoubleComponent implements Component {

    private final String key;

    public DoubleComponent(String key) {
        this.key = key;
    }

    private double num = 0d;

    @Override
    public void readFromNbt(NbtCompound tag) {
        setNum(tag.getDouble(key));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble(key, getNum());
    }


    public double getNum() {
        return num;
    }

    public void setNum(double retributionalDamage) {
        this.num = retributionalDamage;
    }
}
