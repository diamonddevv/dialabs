package net.diamonddev.dialabs.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SnipingEnchantment extends Enchantment implements SyntheticEnchantment {
    public SnipingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean shouldMakeEnchantmentBook() {
        return true;
    }

    @Override
    public boolean shouldBookBeTradable() {
        return false; // Makes the book unable to be traded by villagers.
    }

    @Override
    public boolean shouldBookBeRandomlySelectable() {
        return false; // Makes the book a treasure Enchantment. This *book* can only be found in loot chests, making it pretty rare.
    }
}
