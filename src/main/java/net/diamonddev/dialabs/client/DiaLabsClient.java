package net.diamonddev.dialabs.client;

import net.diamonddev.dialabs.client.registry.InitHandledScreen;
import net.fabricmc.api.ClientModInitializer;
public class DiaLabsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new InitHandledScreen().init();
    }
}
