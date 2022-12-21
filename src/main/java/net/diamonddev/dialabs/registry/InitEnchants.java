package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.enchant.*;
import net.diamonddev.dialabs.integration.ModIntegrations;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEnchants implements RegistryInit {

    public static final Enchantment WITHERED_ASPECT = new WitheredAspectEnchantment();
    public static final Enchantment HARVESTER = new HarvesterEnchantment();
    public static final Enchantment SOUL_ASPECT = new SoulAspectEnchantment();
    public static final Enchantment RETRIBUTIVE = new RetributiveEnchantment();


    public static void createValidSyntheticEnchantmentsList() {
        for (Enchantment e : Registries.ENCHANTMENT) {
            if (e instanceof SyntheticEnchantment) {
                SyntheticEnchantment.validSyntheticEnchantments.add(e);
            }
        }
        SyntheticEnchantment.validSyntheticEnchantments.addAll(SyntheticEnchantmentDiscItem.externalEntries);
    }

    @Override
    public void init() {
        // Register new Enchantments
        Registry.register(Registries.ENCHANTMENT, DiaLabs.id.build("withered_aspect"), WITHERED_ASPECT);
        Registry.register(Registries.ENCHANTMENT, DiaLabs.id.build("harvester"), HARVESTER);
        Registry.register(Registries.ENCHANTMENT, DiaLabs.id.build("soul_aspect"), SOUL_ASPECT);
        Registry.register(Registries.ENCHANTMENT, DiaLabs.id.build("retributive"), RETRIBUTIVE);


        SyntheticEnchantment.makeSyntheticDiscItemFromEnchantment(Enchantments.RIPTIDE);

        // Create new Synthetic Discs for ModIntegration enchantments, these have a catch for unloaded mods already
        SyntheticEnchantment.makeSyntheticDiscItemFromModIntegration(ModIntegrations.INCOMBUSTIUM, "spectral");

        // Register List of Synthetics
        createValidSyntheticEnchantmentsList();
    }
}
