package net.diamonddev.dialabs.enchant;


import net.diamonddev.dialabs.integration.ModIntegration;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public interface SyntheticEnchantment {
    Collection<Enchantment> validSyntheticEnchantments = new ArrayList<>();
    HashMap<Enchantment, Integer> hashSyntheticEnchantMaxLevel = new HashMap<>();


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

    /**
     * @return The max level for a synthetic disc of this enchantment. Can exceed the normal max level. Anything less than 1 defaults to the normal max level.
     */
    default int getMaxSyntheticLevel() {
        return 0;
    }

    static void makeSyntheticDiscItemFromEnchantment(Enchantment enchantment) {
        SyntheticEnchantmentDiscItem.externalEntries.add(enchantment);
        SyntheticEnchantment.hashSyntheticEnchantMaxLevel.put(enchantment, enchantment.getMaxLevel());
    }

    static void makeSyntheticDiscItemFromEnchantment(Enchantment enchantment, int syntheticMaxLevel) {
        SyntheticEnchantmentDiscItem.externalEntries.add(enchantment);
        SyntheticEnchantment.hashSyntheticEnchantMaxLevel.put(enchantment, syntheticMaxLevel >= 1 ? syntheticMaxLevel : enchantment.getMaxLevel());
    }

    static void makeSyntheticDiscItemFromModIntegration(ModIntegration modIntegration, String enchantmentPath) {
        if (modIntegration.isModLoaded()) {
            Enchantment e = modIntegration.getRegistryValue(Registries.ENCHANTMENT, enchantmentPath);
            SyntheticEnchantmentDiscItem.externalEntries.add(e);
            SyntheticEnchantment.hashSyntheticEnchantMaxLevel.put(e, e.getMaxLevel());
        }
    }

    static void makeSyntheticDiscItemFromModIntegration(ModIntegration modIntegration, String enchantmentPath, int syntheticMaxLevel) {
        if (modIntegration.isModLoaded()) {
            Enchantment e = modIntegration.getRegistryValue(Registries.ENCHANTMENT, enchantmentPath);
            SyntheticEnchantmentDiscItem.externalEntries.add(e);
            SyntheticEnchantment.hashSyntheticEnchantMaxLevel.put(e, syntheticMaxLevel >= 1 ? syntheticMaxLevel : e.getMaxLevel());
        }
    }
}
