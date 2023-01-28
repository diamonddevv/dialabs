package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.registry.InitEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class InitEntityRenderer implements RegistryInit {
    @Override
    public void init() {
        EntityRendererRegistry.register(InitEntity.THROWN_ITEM, FlyingItemEntityRenderer::new);
    }
}
