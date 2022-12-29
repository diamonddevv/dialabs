package net.diamonddev.dialabs.cca.item;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.diamonddev.dialabs.cca.IntComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;

public class ItemIntComponent extends ItemComponent implements IntComponent {
    private final String key;

    public ItemIntComponent(String key, ItemStack stack) {
        super(stack);
        this.key = key;
    }

    @Override
    public int getValue() {
        if (!this.hasTag(key, NbtType.INT)) this.putInt(key, 0);
        return this.getInt(key);
    }

    @Override
    public void setValue(int i) {
        this.putInt(key, i);
    }
}
