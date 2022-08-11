package net.diamonddev.dialabs.init;


import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.enchant.WitheredAspectEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class InitEnchants {

    public static final Enchantment WITHERED_ASPECT = new WitheredAspectEnchantment();



    public static void register() {

        Registry.register(Registry.ENCHANTMENT, new Identifier("withered_aspect"), WITHERED_ASPECT);

    }
}
