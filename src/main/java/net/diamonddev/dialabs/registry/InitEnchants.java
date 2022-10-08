package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.enchant.HarvesterEnchantment;
import net.diamonddev.dialabs.enchant.SoulAspectEnchantment;
import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.enchant.WitheredAspectEnchantment;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.integration.ModIntegrations;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.registry.Registry;

public class InitEnchants {

    public static final Enchantment WITHERED_ASPECT = new WitheredAspectEnchantment();
    public static final Enchantment HARVESTER = new HarvesterEnchantment();
    public static final Enchantment SOUL_ASPECT = new SoulAspectEnchantment();



    public static void register() {

        // Register new Enchantments
        Registry.register(Registry.ENCHANTMENT, new Identifier("withered_aspect"), WITHERED_ASPECT);
        Registry.register(Registry.ENCHANTMENT, new Identifier("harvester"), HARVESTER);
        Registry.register(Registry.ENCHANTMENT, new Identifier("soul_aspect"), SOUL_ASPECT);


        SyntheticEnchantment.makeSyntheticDiscItemFromEnchantment(Enchantments.RIPTIDE);

        // Create new Synthetic Discs for ModIntegration enchantments, these have a catch for unloaded mods already todo: fix
//        SyntheticEnchantment.makeSyntheticDiscItemFromModIntegration(ModIntegrations.INCOMBUSTIUM, "spectral");

        // Register List of Synthetics
        createValidSyntheticEnchantmentsList();
    }

    public static void createValidSyntheticEnchantmentsList() {
        for (Enchantment e : Registry.ENCHANTMENT) {
            if (e instanceof SyntheticEnchantment) {
                SyntheticEnchantment.validSyntheticEnchantments.add(e);
            }
        }
        SyntheticEnchantment.validSyntheticEnchantments.addAll(SyntheticEnchantmentDiscItem.externalEntries);
    }
}
