package net.diamonddev.dialabs.enchant;

import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.Collection;

public interface SyntheticEnchantment {
    Collection<Enchantment> validSyntheticEnchantments = new ArrayList<>();
    default boolean canBeSynthesized() {
        return true;
    }
    default boolean shouldMakeEnchantmentBook() {
        return false;
    }
    default boolean shouldBookBeTradable() {
        return this.shouldMakeEnchantmentBook();
    }
    default boolean shouldBookBeLootable() {
        return this.shouldMakeEnchantmentBook();
    }
    default boolean shouldBookBeRandomlySelectable() {
        return this.shouldMakeEnchantmentBook();
    }


    static void makeSyntheticDiscItemForEnchantment(Enchantment enchantment) {
        SyntheticEnchantmentDiscItem.externalEntries.add(enchantment);
    }
}
