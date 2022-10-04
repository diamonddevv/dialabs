package net.diamonddev.dialabs.block.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class OutputSlotInventory implements ImplementedInventory {

    private final int size;
    private final DefaultedList<ItemStack> inventory;

    public OutputSlotInventory() {
        this.size = 1;
        this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public int size() {
        return ImplementedInventory.super.size();
    }

    public void set(ItemStack stack) {
        this.inventory.set(0, stack);
    }

    public ItemStack get() {
        return this.inventory.get(0);
    }
}
