package net.diamonddev.dialabs.client;

import net.diamonddev.dialabs.client.registry.InitBlockEntityRenderer;
import net.diamonddev.dialabs.client.registry.InitEntityLayer;
import net.diamonddev.dialabs.client.registry.InitEntityRenderer;
import net.diamonddev.dialabs.client.registry.InitHandledScreen;
import net.diamonddev.dialabs.particle.InitParticle;
import net.fabricmc.api.ClientModInitializer;
public class DialabsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new InitHandledScreen().register();
        new InitEntityLayer().register();
        new InitEntityRenderer().register();
        new InitBlockEntityRenderer().register();
        new InitParticle().register();
    }
}
