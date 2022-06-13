package net.diamonddev.dialabs.init;


import net.diamonddev.dialabs.enchant.WitheredAspect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InitEnchants {

    public static final Enchantment WITHERED_ASPECT = new WitheredAspect();



    public static void register() {

        Registry.register(Registry.ENCHANTMENT, new Identifier("withered_aspect"), WITHERED_ASPECT);

    }
}
