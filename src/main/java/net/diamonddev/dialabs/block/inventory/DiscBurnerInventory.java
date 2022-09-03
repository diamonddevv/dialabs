package net.diamonddev.dialabs.block.inventory;

import net.diamonddev.dialabs.interfaces.ImplementedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class DiscBurnerInventory implements ImplementedInventory {

    private final int size;
    private final DefaultedList<ItemStack> inventory;

    public DiscBurnerInventory(int size) {
        this.size = size;
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
}
