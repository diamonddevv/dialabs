package net.diamonddev.dialabs.integration;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

public class ModIntegration {

    private final String modid;

    public ModIntegration(String modid) {
       this.modid = modid;
   }
    public Identifier getIdentifier(String path) {
        return new Identifier(this.modid, path);
    }

    public boolean isModLoaded() {
        return FabricLoaderImpl.INSTANCE.isModLoaded(this.modid);
    }

    public <T> T getRegistryValue(Registry<T> registry, String path) {
        return registry.get(getIdentifier(path));
    }


}
