package net.diamonddev.dialabs.api;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public record ModIntegration(String modid) {

    public Identifier getIdentifier(String path) {
        return new Identifier(this.modid, path);
    }

    public boolean isModLoaded() {
        return FabricLoaderImpl.INSTANCE.isModLoaded(this.modid());
    }

    public Enchantment getEnchantment(String path) {
        return Registry.ENCHANTMENT.getOrEmpty(getIdentifier(path)).orElseThrow(
                () -> new IllegalArgumentException("Enchantment '" + getIdentifier(path) + "' was not found in the registry!"));
    }
}
