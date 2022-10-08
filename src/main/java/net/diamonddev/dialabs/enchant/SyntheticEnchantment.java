package net.diamonddev.dialabs.enchant;


import net.diamonddev.dialabs.integration.ModIntegration;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;

public interface SyntheticEnchantment {
    Collection<Enchantment> validSyntheticEnchantments = new ArrayList<>();
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


    static void makeSyntheticDiscItemFromEnchantment(Enchantment enchantment) {
        SyntheticEnchantmentDiscItem.externalEntries.add(enchantment);
    }

    static void makeSyntheticDiscItemFromModIntegration(ModIntegration modIntegration, String enchantmentPath) {
        if (modIntegration.isModLoaded()) {
            SyntheticEnchantmentDiscItem.externalEntries.add(modIntegration.getRegistryValue(Registry.ENCHANTMENT, enchantmentPath));
        }
    }
}
