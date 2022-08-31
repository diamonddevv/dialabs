package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.enchant.HarvesterEnchantment;
import net.diamonddev.dialabs.enchant.WitheredAspectEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class InitEnchants {

    public static final Enchantment WITHERED_ASPECT = new WitheredAspectEnchantment();
    public static final Enchantment HARVESTER = new HarvesterEnchantment();



    public static void register() {

        Registry.register(Registry.ENCHANTMENT, new Identifier("withered_aspect"), WITHERED_ASPECT);
        Registry.register(Registry.ENCHANTMENT, new Identifier("harvester"), HARVESTER);

    }
}
