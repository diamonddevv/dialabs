package net.diamonddev.dialabs.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");

        ClassTinkerers.enumBuilder(enchantmentTarget)
                .addEnumSubclass("SPYGLASS", "net.diamonddev.dialabs.enchant.targets.SpyglassEnchantTarget").build();
    }
}
