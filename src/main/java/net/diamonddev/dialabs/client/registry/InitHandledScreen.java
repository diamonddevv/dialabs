package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.client.gui.DiscBurnerScreen;
import net.diamonddev.dialabs.client.gui.EnchantmentSynthesisScreen;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class InitHandledScreen implements RegistryInit {
    public void init() {
        HandledScreens.register(InitScreenHandler.ENCHANT_SYNTHESIS, EnchantmentSynthesisScreen::new);
        HandledScreens.register(InitScreenHandler.DISC_BURNER, DiscBurnerScreen::new);
    }

}
