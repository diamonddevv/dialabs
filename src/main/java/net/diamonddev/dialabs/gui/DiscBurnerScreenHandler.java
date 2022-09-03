package net.diamonddev.dialabs.gui;

import net.diamonddev.dialabs.block.inventory.DiscBurnerInventory;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.registry.InitScreenHandler;
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
import net.minecraft.world.World;

import java.util.Map;

public class DiscBurnerScreenHandler extends ScreenHandler {

    private final DiscBurnerInventory inventory;
    private final ScreenHandlerContext context;
    private final World world;

    private boolean hasDisc;
    private Property xpRequirement;

    public DiscBurnerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public DiscBurnerScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(InitScreenHandler.DISC_BURNER, syncId);

        this.xpRequirement = Property.create();

        this.inventory = new DiscBurnerInventory(3) {
            public void markDirty() {
                super.markDirty();
                DiscBurnerScreenHandler.this.onContentChanged(this);
            }
        };

        this.addSlot(new Slot(this.inventory, 0, 27, 47) {
            // Input A - Item Slot
        });

        this.addSlot(new Slot(this.inventory, 1, 76, 47) {
            // Input B - Disc Slot

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof SyntheticEnchantmentDiscItem;
            }
        });

        this.addSlot(new Slot(this.inventory, 2, 134, 47) {
            // Output

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
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
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);

        int cost = this.xpRequirement.get();
        ItemStack a = inventory.getStack(0);
        ItemStack b = inventory.getStack(1);
        ItemStack out;

        if (b.getItem() instanceof SyntheticEnchantmentDiscItem) {

            if (a.getItem() instanceof SyntheticEnchantmentDiscItem) {

            } else {

                Map<Enchantment, Integer> mappedDiscEnchants = EnchantHelper.getMappedStoredEnchantments(b);
                boolean allAcceptable = true;
                boolean allCompatible = true;
                for (Enchantment e : mappedDiscEnchants.keySet()) {
                    allAcceptable = !e.isAcceptableItem(a);
                    if (!allAcceptable) {
                        break;
                    }
                }

                if (a.hasEnchantments()) {
                    Map<Enchantment, Integer> mappedAEnchants = EnchantmentHelper.get(a);
                    allCompatible = EnchantHelper.allCompatible(mappedAEnchants, mappedDiscEnchants);
                }

                if (allAcceptable && allCompatible) {
                    out = a.copy();
                    EnchantHelper.addAllEnchantments(out, mappedDiscEnchants);
                    cost = EnchantHelper.getXpCostForAddingEnchants(a, mappedDiscEnchants);
                }

            }
        }
    }
}
