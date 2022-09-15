package net.diamonddev.dialabs.gui;

import net.diamonddev.dialabs.block.inventory.DiscBurnerInventory;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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

import java.util.Map;

public class DiscBurnerScreenHandler extends ScreenHandler {

    private final DiscBurnerInventory inventory;
    private final ScreenHandlerContext context;
    private final World world;
    private final PlayerEntity playerEntity;

    private boolean hasDisc;
    private Property xpRequirement;
    private boolean possibleCombination = true;

    public DiscBurnerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public DiscBurnerScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(InitScreenHandler.DISC_BURNER, syncId);

        this.xpRequirement = Property.create();

        this.playerEntity = playerInventory.player;

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

    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
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
        ItemStack out;

        if (a.isEmpty() || b.isEmpty()) {
            out = ItemStack.EMPTY;
            this.inventory.setStack(getOutputSlotIndex(), out);
        }

        if (b.getItem() instanceof SyntheticEnchantmentDiscItem) {

            if (a.getItem() instanceof SyntheticEnchantmentDiscItem) {
                Map<Enchantment, Integer> mappedAdditiveEnchants = EnchantHelper.getMappedStoredEnchantments(b);
                out = a.copy();
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

                this.possibleCombination = allCompatible && allAcceptable;

                if (allAcceptable && allCompatible) {
                    out = a.copy();
                    EnchantHelper.addAllEnchantments(out, mappedDiscEnchants);
                    cost = EnchantHelper.getXpCostForAddingEnchants(a, mappedDiscEnchants);
                    this.inventory.setStack(getOutputSlotIndex(), out);
                    this.xpRequirement.set(cost);
                }

            }
        }
    }


    public void onOutputTaken() {
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

    public Property getXpRequirementProperty() {
        return xpRequirement;
    }
}
