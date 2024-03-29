package net.diamonddev.dialabs.gui;

import net.diamonddev.dialabs.block.inventory.OutputSlotInventory;
import net.diamonddev.dialabs.block.inventory.SynthesisInventory;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.diamonddev.dialabs.registry.InitSoundEvent;
import net.diamonddev.dialabs.util.DataDrivenTagKeys;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Optional;

public class EnchantmentSynthesisScreenHandler extends ScreenHandler {

    private final SynthesisInventory inventory;
    private final OutputSlotInventory out;
    private final World world;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private final Optional<BlockPos> blockPos;

    public ArrayList<EnchantmentLevelEntry> recipeEles;

    public boolean allRecipeSlotsFilled;

    private OutSlot OUT_SLOT;
    private boolean canTake;

    public boolean getCanTakeFromOutSlot() {
        return canTake;
    }
    private static class OutSlot extends Slot {
        public OutSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }
        public boolean canTake = false;
    }
    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(InitScreenHandler.ENCHANT_SYNTHESIS, syncId);

        this.blockPos = context.get((w, blockPos) -> blockPos);
        this.inventory = new SynthesisInventory(6) {
            public void markDirty() {
                super.markDirty();
                EnchantmentSynthesisScreenHandler.this.onContentChanged(this);
            }
        };


        this.out = new OutputSlotInventory();

        OUT_SLOT = new OutSlot(this.out, 0, 24, 17) {
            // Output Slot

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return canTake;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                blockPos.ifPresent(pos -> world.playSound(null, pos, InitSoundEvent.SYNTHESIZE_DISC, SoundCategory.BLOCKS));

                decrementSlots();

                EnchantHelper.storeAllEnchantments(
                        stack,
                        recipeEles
                );

                super.onTakeItem(player, stack);
            }
        };

        this.addSlot(new Slot(this.inventory, 0, 15, 47) {
            // Empty Disc Input Slot
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof SyntheticEnchantmentDiscItem && !EnchantHelper.hasAnySyntheticEnchantmentStored(stack);
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 34, 47) {
            // Payment Tag Input Slot
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(DataDrivenTagKeys.SYNTHETIC_ENCHANTMENT_PAYMENT_ITEMS);
            }
        });

        this.addSlot(OUT_SLOT); // Add out slot, handled seperately

        this.context = context;
        this.world = playerInventory.player.getWorld();
        this.player = playerInventory.player;

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

    @Override
    public void onContentChanged(Inventory inventory) {
        validate();
        super.onContentChanged(inventory);
    }

    public boolean transferItem(ItemStack stack, int slotIndex) {
        if (stack.getItem() instanceof SyntheticEnchantmentDiscItem) {
            if (!EnchantHelper.hasAnySyntheticEnchantmentStored(stack)) {
                if (slotIndex == getDiscSlotIndex()) {
                    return true;
                }
            }
        }

        if (stack.isIn(DataDrivenTagKeys.SYNTHETIC_ENCHANTMENT_PAYMENT_ITEMS)) {
            if (slotIndex == getLapisSlotIndex()) {
                return true;
            }
        }

        if (isSlotEmpty(getInputASlotIndex())) {
            return true;
        }
        if (isSlotEmpty(getInputBSlotIndex())) {
            return true;
        }

        return isSlotEmpty(getInputCSlotIndex());
    }

    public boolean isSlotEmpty(int index) {
        return getInventory().getStack(index).isEmpty();
    }
    public SynthesisInventory getInventory() {
        return inventory;
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int slot) {
        Slot s = this.slots.get(slot);
        if (s.hasStack()) {
            ItemStack stack = s.getStack();

            if (transferItem(stack, slot)) {
                return ItemStack.EMPTY;
            } else {
                player.getInventory().insertStack(stack);
                this.inventory.markDirty();
            }

            return stack;
        }
        return ItemStack.EMPTY;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> {
            this.out.clear();
            this.dropInventory(player, this.inventory);
        });
    }

    public void validate() {
        // If a matching recipe is found, perform actions
        Optional<SynthesisRecipe> match = world.getRecipeManager().getFirstMatch(SynthesisRecipe.Type.INSTANCE, getInventory(), world);

        this.allRecipeSlotsFilled = match.isPresent();

        // Copy result stack to output.
        if (match.isPresent()) {
            this.recipeEles = match.get().getOutputRolledEnchants();
            this.out.set(match.get().getResult(this.world.getRegistryManager()).copy());

            this.OUT_SLOT.canTake = match.get().lapisReqMet(this.inventory);
            this.canTake = this.OUT_SLOT.canTake;
        } else {
            this.out.clear();
        }
    }

    public void decrementSlots() {
        // Get Match
        Optional<SynthesisRecipe> match = world.getRecipeManager().getFirstMatch(SynthesisRecipe.Type.INSTANCE, getInventory(), world);
        if (match.isPresent()) {

            inventory.decrementStackSize(getDiscSlotIndex(), 1);

            inventory.decrementStackSize(getInputASlotIndex(), match.get().getInputA().getCountComponent());
            inventory.decrementStackSize(getInputBSlotIndex(), match.get().getInputB().getCountComponent());
            inventory.decrementStackSize(getInputCSlotIndex(), match.get().getInputC().getCountComponent());

            inventory.decrementStackSize(getLapisSlotIndex(), match.get().getLapisRequirement());
        }
    }
}