package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.enchant.*;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEnchants implements RegistryInitializer {

    public static final Enchantment WITHERED_ASPECT = new WitheredAspectEnchantment();
    public static final Enchantment HARVESTER = new HarvesterEnchantment();
    public static final Enchantment SOUL_ASPECT = new SoulAspectEnchantment();
    public static final Enchantment RETRIBUTIVE = new RetributiveEnchantment();
    public static final Enchantment MULTICLIP = new MulticlipEnchantment();
    public static final Enchantment SNIPING = new SnipingEnchantment();
    public static final Enchantment ZOOMING = new ZoomingEnchantment();
    public static final Enchantment PRISMARINE_SPIKES = new PrismarineThornsEnchantment();

    @Override
    public void register() {
        // Register new Enchantments
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("withered_aspect"), WITHERED_ASPECT);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("harvester"), HARVESTER);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("soul_aspect"), SOUL_ASPECT);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("retributive"), RETRIBUTIVE);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("multiclip"), MULTICLIP);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("sniping"), SNIPING);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("zooming"), ZOOMING);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id("prismarine_spikes"), PRISMARINE_SPIKES);

        SyntheticEnchantment.makeSyntheticDiscItemFromEnchantment(Enchantments.RIPTIDE);
        SyntheticEnchantment.makeSyntheticDiscItemFromEnchantment(Enchantments.CHANNELING, 2);


        // Register List of External Synthetic Enchantments
        SyntheticEnchantment.validSyntheticEnchantments.addAll(SyntheticEnchantmentDiscItem.externalEntries);
    }
}
