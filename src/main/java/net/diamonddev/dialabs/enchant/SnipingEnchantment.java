package net.diamonddev.dialabs.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SnipingEnchantment extends Enchantment implements SyntheticEnchantment {
    public SnipingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
}
