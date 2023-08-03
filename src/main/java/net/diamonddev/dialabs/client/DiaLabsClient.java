package net.diamonddev.dialabs.client;

import net.diamonddev.dialabs.client.registry.InitBlockEntityRenderer;
import net.diamonddev.dialabs.client.registry.InitEntityLayer;
import net.diamonddev.dialabs.client.registry.InitEntityRenderer;
import net.diamonddev.dialabs.client.registry.InitHandledScreen;
import net.diamonddev.dialabs.particle.InitParticle;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class DialabsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        new InitHandledScreen().register();
        new InitEntityLayer().register();
        new InitEntityRenderer().register();
        new InitBlockEntityRenderer().register();
        new InitParticle().register();
    }
}
