package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.client.gui.DiscBurnerScreen;
import net.diamonddev.dialabs.client.gui.EnchantmentSynthesisScreen;
import net.diamonddev.dialabs.registry.InitScreenHandler;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class InitHandledScreen implements RegistryInitializer {
    public void register() {
        HandledScreens.register(InitScreenHandler.ENCHANT_SYNTHESIS, EnchantmentSynthesisScreen::new);
        HandledScreens.register(InitScreenHandler.DISC_BURNER, DiscBurnerScreen::new);
    }

}
