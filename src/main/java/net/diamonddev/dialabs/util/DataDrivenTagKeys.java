package net.diamonddev.dialabs.util;


import net.diamonddev.dialabs.api.Identifier;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class DataDrivenTagKeys {

    public static TagKey<Item> SYNTHETIC_ENCHANTMENT_PAYMENT_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("synthetic_enchantment_payments"));
    public static TagKey<Enchantment> SYNTHETIC_ENCHANTMENTS_FROM_TAG = TagKey.of(Registry.ENCHANTMENT_KEY, new Identifier("synthetic"));

}
