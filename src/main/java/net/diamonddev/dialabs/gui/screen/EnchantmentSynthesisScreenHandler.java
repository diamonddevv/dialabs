package net.diamonddev.dialabs.gui.screen;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class EnchantmentSynthesisScreenHandler extends ScreenHandler {

    private final Property selectedEnchantment;
    private final ArrayList<Enchantment> availableEnchants;
    private final ItemStack inputStack;
    private final SimpleInventory input;
    private final CraftingResultInventory output;
    private final World world;
    private final Slot inputSlot;
    private final Slot outputSlot;
    private final ScreenHandlerContext context;

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public EnchantmentSynthesisScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(InitScreenHandler.ENCHANT_SYNTHESIS, syncId);
        this.selectedEnchantment = Property.create();
        this.availableEnchants = this.getPossibleOutputs();
        this.inputStack = ItemStack.EMPTY;
        this.input = new SimpleInventory(1) {
            public void markDirty() {
                super.markDirty();
                EnchantmentSynthesisScreenHandler.this.onContentChanged(this);
            }
        };
        this.output = new CraftingResultInventory();
        this.context = context;
        this.world = playerInventory.player.world;
        this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
        this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.world, player, stack.getCount());
                EnchantmentSynthesisScreenHandler.this.output.unlockLastRecipe(player);
                ItemStack itemStack = EnchantmentSynthesisScreenHandler.this.inputSlot.takeStack(1);
                if (!itemStack.isEmpty()) {
                    output.setStack(outputSlot.id, getRandomlyEnchantedOutput(1));
                }
                super.onTakeItem(player, stack);
            }
        });

    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }

    public ArrayList<Enchantment> getPossibleOutputs() {
        ArrayList<Enchantment> enchants = new ArrayList<>();
        for (Enchantment e : Registry.ENCHANTMENT) {
            if (e instanceof SyntheticEnchantment synthesis) {
                if (synthesis.canBeSynthesized()) {
                    enchants.add(e);
                }
            }
        }
        return enchants;
    }

    public ItemStack getRandomlyEnchantedOutput(int level) {
        ItemStack stack = new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC);
        Random r = new Random();
        int limit = getPossibleOutputs().size();
        Enchantment toEnchant = getPossibleOutputs().get(r.nextInt(limit));
        stack.addEnchantment(toEnchant, level);
        return stack;
    }
}
