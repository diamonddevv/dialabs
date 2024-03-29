package net.diamonddev.dialabs.enchant.targets;


import net.diamonddev.dialabs.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.SpyglassItem;

public class SpyglassEnchantTarget extends EnchantmentTargetMixin {

    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof SpyglassItem;
    }
}
