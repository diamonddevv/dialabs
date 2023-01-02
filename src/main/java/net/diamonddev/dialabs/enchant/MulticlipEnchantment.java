package net.diamonddev.dialabs.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class MulticlipEnchantment extends Enchantment implements SyntheticEnchantment {
    public MulticlipEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 4; // Each level should add one extra loaded arrow, but also a longer charge time. hopefully Quick Charge will balance this
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) &&
                other != Enchantments.MULTISHOT;
    }
}
