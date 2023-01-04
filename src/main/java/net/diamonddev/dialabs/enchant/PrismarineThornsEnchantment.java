package net.diamonddev.dialabs.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class PrismarineThornsEnchantment extends Enchantment implements SyntheticEnchantment {
    public PrismarineThornsEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean shouldMakeEnchantmentBook() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
