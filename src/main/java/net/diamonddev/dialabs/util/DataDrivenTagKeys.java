package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.Dialabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class DataDrivenTagKeys {

    public static TagKey<Item> SYNTHETIC_ENCHANTMENT_PAYMENT_ITEMS = TagKey.of(Registries.ITEM.getKey(), Dialabs.id("synthetic_enchantment_payments"));
    public static TagKey<Enchantment> SYNTHETIC_ENCHANTMENTS_FROM_TAG = TagKey.of(Registries.ENCHANTMENT.getKey(), Dialabs.id("synthetic"));

}
