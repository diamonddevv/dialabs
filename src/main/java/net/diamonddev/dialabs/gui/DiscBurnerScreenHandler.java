package net.diamonddev.dialabs.gui;

import net.diamonddev.dialabs.block.inventory.DiscBurnerInventory;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.diamonddev.dialabs.registry.InitSoundEvent;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Optional;

public class DiscBurnerScreenHandler extends ScreenHandler {

    private final DiscBurnerInventory inventory;
    private final ScreenHandlerContext context;
    private final World world;
    private final PlayerEntity playerEntity;
    private final Optional<BlockPos> blockPos;

    private Property xpRequirement;
    private boolean possibleCombination = true;
    public boolean forceFail = false;

    public DiscBurnerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public DiscBurnerScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(InitScreenHandler.DISC_BURNER, syncId);

        this.xpRequirement = Property.create();

        this.playerEntity = playerInventory.player;

        this.blockPos = context.get((w, blockPos) -> blockPos);

        this.inventory = new DiscBurnerInventory(3) {


            public void markDirty() {
                super.markDirty();
                DiscBurnerScreenHandler.this.onContentChanged(this);
            }
        };

        this.addSlot(new Slot(this.inventory, getInputASlotIndex(), 27, 47) {
            // Input A - Item Slot
        });

        this.addSlot(new Slot(this.inventory, getInputBSlotIndex(), 76, 47) {
            // Input B - Disc Slot

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof SyntheticEnchantmentDiscItem;
            }
        });

        this.addSlot(new Slot(this.inventory, getOutputSlotIndex(), 134, 47) {
            // Output

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                onOutputTaken();
                super.onTakeItem(player, stack);
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return canTake();
            }
        });

        // Player Inventory
        this.context = context;
        this.world = playerInventory.player.getWorld();

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

    }

    public boolean transferItem(ItemStack stack, int slotIndex) {
        if (this.slots.get(slotIndex).getStack() != stack)
            return !super.insertItem(stack, slotIndex, slotIndex + 1, true);
        return false;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int slot) {
        Slot s = this.slots.get(slot);
        if (s.hasStack()) {
            ItemStack stack = s.getStack();

            if (stack.getItem() instanceof SyntheticEnchantmentDiscItem && !slots.get(getInputBSlotIndex()).hasStack()) {
                if (transferItem(stack, getInputBSlotIndex())) {
                    return ItemStack.EMPTY;
                }
            } else if (transferItem(stack, getInputASlotIndex())) {
                return ItemStack.EMPTY;
            } else {
                player.getInventory().insertStack(stack);
                this.inventory.markDirty();
            }
        }
        this.inventory.markDirty();
        return ItemStack.EMPTY;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> {
            this.inventory.removeStack(getOutputSlotIndex());
            this.dropInventory(player, this.inventory);
        });
    }

    public static int getInputASlotIndex() {
        return 0;
    }
    public static int getInputBSlotIndex() {
        return 1;
    }
    public static int getOutputSlotIndex() {
        return 2;
    }
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);

        int cost;
        ItemStack a = inventory.getStack(getInputASlotIndex());
        ItemStack b = inventory.getStack(getInputBSlotIndex());
        ItemStack out = ItemStack.EMPTY;

        this.inventory.setStack(getOutputSlotIndex(), out);
        forceFail = false;
        boolean isCreative = this.playerEntity.getAbilities().creativeMode;

        if (a.isEmpty() || b.isEmpty()) {
            this.inventory.setStack(getOutputSlotIndex(), out);
        }

        if ((!a.isEmpty() && b.getItem() instanceof SyntheticEnchantmentDiscItem && EnchantHelper.hasAnySyntheticEnchantmentStored(b)) || (isCreative && isAllSlotsRequiredFilled())) {
            forceFail = false;

            if (a.getItem() instanceof SyntheticEnchantmentDiscItem) {
                Map<Enchantment, Integer> mappedAdditiveEnchants = EnchantHelper.getMappedStoredEnchantments(b);
                out = a.copy();
                if (!isCreative) out.setCount(1);
                EnchantHelper.storeAllEnchantments(out, mappedAdditiveEnchants);
                this.inventory.setStack(getOutputSlotIndex(), out);
                cost = EnchantHelper.getXpCostForAddingEnchants(a, mappedAdditiveEnchants);
                this.xpRequirement.set(cost);

            } else {

                Map<Enchantment, Integer> mappedDiscEnchants = EnchantHelper.getMappedStoredEnchantments(b);
                boolean allAcceptable = EnchantHelper.allAcceptable(mappedDiscEnchants, a);
                boolean allCompatible = true;

                if (a.hasEnchantments()) {
                    Map<Enchantment, Integer> mappedAEnchants = EnchantmentHelper.get(a);
                    allCompatible = EnchantHelper.allCompatible(mappedAEnchants, mappedDiscEnchants);
                }

                this.possibleCombination = (allCompatible && allAcceptable) || (isCreative && isAllSlotsRequiredFilled());

                if (possibleCombination) {
                    forceFail = false;
                    out = a.copy();
                    if (!isCreative) out.setCount(1);
                    EnchantHelper.addAllEnchantments(out, mappedDiscEnchants);
                    cost = EnchantHelper.getXpCostForAddingEnchants(a, mappedDiscEnchants);
                    this.inventory.setStack(getOutputSlotIndex(), out);
                    this.xpRequirement.set(cost);
                } else {
                    forceFail = true;
                }

            }
        } else {
            this.inventory.setStack(getOutputSlotIndex(), out);
            forceFail = !b.isEmpty() && b.getItem() instanceof SyntheticEnchantmentDiscItem && !EnchantHelper.hasAnySyntheticEnchantmentStored(b);
        }
    }


    public void onOutputTaken() {
        blockPos.ifPresent(pos -> world.playSound(null, pos, InitSoundEvent.BURN_DISC, SoundCategory.BLOCKS));

        inventory.decrementStackSize(getInputASlotIndex(), 1);
        inventory.decrementStackSize(getInputBSlotIndex(), 1);

        playerEntity.experienceLevel -= this.xpRequirement.get();
    }

    public boolean isAllSlotsRequiredFilled() {
        return !this.inventory.getStack(getInputASlotIndex()).isEmpty() && !this.inventory.getStack(getInputBSlotIndex()).isEmpty();
    }

    public boolean canTake() {
        return this.playerEntity.experienceLevel >= xpRequirement.get() || playerEntity.getAbilities().creativeMode;
    }
    public boolean isPossibleCombination() {
        return possibleCombination;
    }

    public boolean hasOutput() {
        return !this.inventory.getStack(getOutputSlotIndex()).isEmpty();
    }
    public Property getXpRequirementProperty() {
        return xpRequirement;
    }
}
