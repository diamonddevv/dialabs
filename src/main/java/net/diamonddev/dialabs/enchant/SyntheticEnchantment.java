package net.diamonddev.dialabs.enchant;


import net.diamonddev.dialabs.integration.ModIntegration;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.Collection;

public interface SyntheticEnchantment {
    Collection<Enchantment> validSyntheticEnchantments = new ArrayList<>();


    /**
     * @return Whether an Enchantment Book should also be made for this synthetic enchantment.
     */
    default boolean shouldMakeEnchantmentBook() {
        return false;
    }


    /**
     * @return Whether an Enchantment Book made for this synthetic enchantment should be tradable by villagers.
     */
    default boolean shouldBookBeTradable() {
        return this.shouldMakeEnchantmentBook();
    }


    /**
     * @return Whether an Enchantment Book made for this synthetic enchantment should be found in loot tables
     */
    default boolean shouldBookBeLootable() {
        return this.shouldMakeEnchantmentBook();
    }


    /**
     * @return Whether an Enchantment Book made for this synthetic enchantment should be obtainable through enchantment tables.
     */
    default boolean shouldBookBeRandomlySelectable() {
        return this.shouldMakeEnchantmentBook();
    }


    static void makeSyntheticDiscItemFromEnchantment(Enchantment enchantment) {
        SyntheticEnchantmentDiscItem.externalEntries.add(enchantment);
    }

    static void makeSyntheticDiscItemFromModIntegration(ModIntegration modIntegration, String enchantmentPath) {
        if (modIntegration.isModLoaded()) {
            SyntheticEnchantmentDiscItem.externalEntries.add(modIntegration.getRegistryValue(Registries.ENCHANTMENT, enchantmentPath));
        }
    }
}
