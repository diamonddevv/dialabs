package net.diamonddev.dialabs.gui;

import net.diamonddev.dialabs.block.inventory.SynthesisInventory;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.diamonddev.dialabs.util.DataDrivenTagKeys;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.Optional;

public class EnchantmentSynthesisScreenHandler extends ScreenHandler {

    private final SynthesisInventory inventory;
    private final World world;
    private final ScreenHandlerContext context;

    private final int lapisCount = 0;
    private final boolean hasDisc = false;

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(InitScreenHandler.ENCHANT_SYNTHESIS, syncId);

        this.inventory = new SynthesisInventory(6) {
            public void markDirty() {
                super.markDirty();
                EnchantmentSynthesisScreenHandler.this.onContentChanged(this);
            }
        };

        this.addSlot(new Slot(this.inventory, 0, 15, 47) {
            // Empty Disc Input Slot
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

        this.addSlot(new Slot(this.inventory, 5, 24, 17) {
           // Output Slot

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                decrementSlots();
                super.onTakeItem(player, stack);
            }
        });

        this.context = context;
        this.world = playerInventory.player.world;

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
        ItemStack itemStack = this.inventory.getStack(getLapisSlotIndex());
        return itemStack.isEmpty() ? 0 : itemStack.getCount();
    }


    public int getDiscSlotIndex() {
        return 0;
    }
    public int getLapisSlotIndex() {
        return 1;
    }
    public int getInputASlotIndex() {
        return 2;
    }
    public int getInputBSlotIndex() {
        return 3;
    }
    public int getInputCSlotIndex() {
        return 4;
    }

    public int getOutputSlotIndex() {
        return 5;
    }


    @Override
    public void onContentChanged(Inventory inventory) {
        validate(inventory.getStack(getDiscSlotIndex()));
        super.onContentChanged(inventory);
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

    public SynthesisInventory getInventory() {
        return inventory;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> {
            this.inventory.removeStack(getOutputSlotIndex());
            this.dropInventory(player, this.inventory);
        });
    }

    public void validate(ItemStack discInputStack) {
        if (!EnchantHelper.hasAnySyntheticEnchantmentStored(discInputStack)) {
            // Get match status
            Optional<SynthesisRecipe> match = world.getRecipeManager().getFirstMatch(SynthesisRecipe.Type.INSTANCE, getInventory(), world);
            // If a matching recipe is found, perform actions
            // Copy result stack to output.
            if (match.isPresent()) {
                inventory.setStack(getOutputSlotIndex(), match.get().getOutput().copy());
            } else {
                inventory.setStack(getOutputSlotIndex(), ItemStack.EMPTY);
            }
        }
    }

    public void decrementSlots() {
        Optional<SynthesisRecipe> match = world.getRecipeManager().getFirstMatch(SynthesisRecipe.Type.INSTANCE, getInventory(), world); // Get Matched recipe
        if (match.isPresent()) {

            inventory.decrementStackSize(getDiscSlotIndex(), 1); // DECREMENT ALL THE SLOTS

            inventory.decrementStackSize(getInputASlotIndex(), 1);
            inventory.decrementStackSize(getInputBSlotIndex(), 1);
            inventory.decrementStackSize(getInputCSlotIndex(), 1);

            inventory.decrementStackSize(getLapisSlotIndex(), match.get().getLapisRequirement());
        }
    }

}