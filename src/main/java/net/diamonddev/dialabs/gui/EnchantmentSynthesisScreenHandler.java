package net.diamonddev.dialabs.gui;

import net.diamonddev.dialabs.block.inventory.SynthesisInventory;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.diamonddev.dialabs.util.DataDrivenTagKeys;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EnchantmentSynthesisScreenHandler extends ScreenHandler {

    private final Property selectedEnchantment;
    private final ArrayList<Enchantment> availableEnchants;
    private final ItemStack inputStack;
    private final Inventory inventory;
    private final World world;
    private final ScreenHandlerContext context;
    private final PlayerInventory playerInventory;

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(InitScreenHandler.ENCHANT_SYNTHESIS, syncId);
        this.selectedEnchantment = Property.create();
        this.availableEnchants = SyntheticEnchantmentDiscItem.getAllSyntheticEnchantments();
        this.inputStack = ItemStack.EMPTY;

        this.inventory = new SynthesisInventory(5) {
            public void markDirty() {
                super.markDirty();
                EnchantmentSynthesisScreenHandler.this.onContentChanged(this);
            }
        };

        this.addSlot(new Slot(this.inventory, 0, 15, 47) {
            // Empty Disc Input Slot

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                if (!EnchantHelper.hasAnySyntheticEnchantmentStored(stack)) {
                    EnchantHelper.storeEnchantment(stack, new EnchantmentLevelEntry(SyntheticEnchantmentDiscItem.getRandomSyntheticEnchantment(), 1));
                }
            }

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof SyntheticEnchantmentDiscItem && !EnchantHelper.hasAnySyntheticEnchantmentStored(stack);
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 35, 47) {
            // Payment Tag Input Slot

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(DataDrivenTagKeys.SYNTHETIC_ENCHANTMENT_PAYMENT_ITEMS);
            }
        });

        this.context = context;
        this.world = playerInventory.player.world;
        this.playerInventory = playerInventory;

        int i;
        int j;

        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        // Recipe Slots
        for (i = 0; i < 3; i++) {
            this.addSlot(new Slot(this.inventory, i + 2, 60, 15 + i * 19));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public int getLapisCount() {
        ItemStack itemStack = this.inventory.getStack(1);
        return itemStack.isEmpty() ? 0 : itemStack.getCount();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.isOf(Items.LAPIS_LAZULI)) {
                if (!this.insertItem(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if ((this.slots.get(0)).hasStack() || !(this.slots.get(0)).canInsert(itemStack2)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemStack3 = itemStack2.copy();
                itemStack3.setCount(1);
                itemStack2.decrement(1);
                (this.slots.get(0)).setStack(itemStack3);
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.inventory));
    }
}