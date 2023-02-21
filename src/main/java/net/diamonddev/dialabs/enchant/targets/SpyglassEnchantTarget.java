package net.diamonddev.dialabs.enchant.targets;


import net.diamonddev.libgenetics.core.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.SpyglassItem;

public class SpyglassEnchantTarget extends EnchantmentTargetMixin {

    @Override
    public boolean method_8177(Item item) {
        return item instanceof SpyglassItem;
    }
}
