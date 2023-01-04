package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.enchant.*;
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
    public static final Enchantment MULTICLIP = new MulticlipEnchantment();
    public static final Enchantment SNIPING = new SnipingEnchantment();
    public static final Enchantment ZOOMING = new ZoomingEnchantment();
    public static final Enchantment PRISMARINE_SPIKES = new PrismarineThornsEnchantment();

    @Override
    public void init() {
        // Register new Enchantments
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("withered_aspect"), WITHERED_ASPECT);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("harvester"), HARVESTER);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("soul_aspect"), SOUL_ASPECT);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("retributive"), RETRIBUTIVE);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("multiclip"), MULTICLIP);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("sniping"), SNIPING);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("zooming"), ZOOMING);
        Registry.register(Registries.ENCHANTMENT, Dialabs.id.build("prismarine_spikes"), PRISMARINE_SPIKES);

        SyntheticEnchantment.makeSyntheticDiscItemFromEnchantment(Enchantments.RIPTIDE);
        SyntheticEnchantment.makeSyntheticDiscItemFromEnchantment(Enchantments.CHANNELING, 2);


        // Register List of External Synthetic Enchantments
        SyntheticEnchantment.validSyntheticEnchantments.addAll(SyntheticEnchantmentDiscItem.externalEntries);
    }
}
