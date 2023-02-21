package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.client.render.block.SoulBasinBlockEntityRenderer;
import net.diamonddev.dialabs.mixin.BlockEntityRendererFactoriesInvoker;
import net.diamonddev.dialabs.registry.InitBlockEntity;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitBlockEntityRenderer implements RegistryInitializer {
    @Override
    public void register() {
        BlockEntityRendererFactoriesInvoker.invokeRegister(InitBlockEntity.SOUL_BASIN.blockEntityType(), SoulBasinBlockEntityRenderer::new);
    }
}
