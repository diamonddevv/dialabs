package net.diamonddev.dialabs.impl;

import com.chocohead.mm.api.ClassTinkerers;
import org.quiltmc.loader.api.MappingResolver;
import org.quiltmc.loader.api.QuiltLoader;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = QuiltLoader.getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");

        ClassTinkerers.enumBuilder(enchantmentTarget)
                .addEnumSubclass("SPYGLASS", "net.diamonddev.dialabs.enchant.targets.SpyglassEnchantTarget").build();
    }
}
