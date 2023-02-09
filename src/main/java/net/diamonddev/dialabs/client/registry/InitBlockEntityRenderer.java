package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.client.render.block.SoulBasinBlockEntityRenderer;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.mixin.BlockEntityRendererFactoriesInvoker;
import net.diamonddev.dialabs.registry.InitBlockEntity;

public class InitBlockEntityRenderer implements RegistryInit {
    @Override
    public void init() {
        BlockEntityRendererFactoriesInvoker.invokeRegister(InitBlockEntity.SOUL_BASIN.blockEntityType(), SoulBasinBlockEntityRenderer::new);
    }
}
