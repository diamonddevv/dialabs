package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.client.render.entity.FlintlockPelletEntityRenderer;
import net.diamonddev.dialabs.registry.InitEntity;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class InitEntityRenderer implements RegistryInitializer {
    @Override
    public void register() {
        EntityRendererRegistry.register(InitEntity.THROWN_ITEM, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(InitEntity.FLINTLOCK_PELLET, FlintlockPelletEntityRenderer::new);
    }
}
