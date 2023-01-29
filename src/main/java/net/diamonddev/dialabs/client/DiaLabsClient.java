package net.diamonddev.dialabs.client;

import net.diamonddev.dialabs.client.registry.InitEntityRenderer;
import net.diamonddev.dialabs.client.registry.InitHandledScreen;
import net.diamonddev.dialabs.particle.InitParticle;
import net.fabricmc.api.ClientModInitializer;
public class DialabsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new InitHandledScreen().init();
        new InitEntityRenderer().init();
        new InitParticle().init();
    }
}
