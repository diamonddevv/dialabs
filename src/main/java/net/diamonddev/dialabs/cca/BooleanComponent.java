package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class BooleanComponent implements Component {

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
