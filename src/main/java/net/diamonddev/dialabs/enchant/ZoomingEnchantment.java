package net.diamonddev.dialabs.enchant;

import net.diamonddev.dialabs.lib.enchant.EnchantTargets;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class ZoomingEnchantment extends Enchantment implements SyntheticEnchantment {
    public ZoomingEnchantment() {
        super(Rarity.UNCOMMON, EnchantTargets.SPYGLASS, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMaxSyntheticLevel() {
        return 5;
    }


    @Override
    public boolean shouldMakeEnchantmentBook() {
        return true;
    }
}
