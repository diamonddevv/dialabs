package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.feature_flags.FeatureFlags;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class InitScreenHandler implements RegistryInitializer {

    public static ScreenHandlerType<EnchantmentSynthesisScreenHandler> ENCHANT_SYNTHESIS = new ScreenHandlerType<>(EnchantmentSynthesisScreenHandler::new, FeatureFlags.DEFAULT_SET);
    public static ScreenHandlerType<DiscBurnerScreenHandler> DISC_BURNER = new ScreenHandlerType<>(DiscBurnerScreenHandler::new, FeatureFlags.DEFAULT_SET);

    public void register() {
        Registry.register(Registries.SCREEN_HANDLER_TYPE, Dialabs.id("enchantment_synthesis_screen_handler"), ENCHANT_SYNTHESIS);
        Registry.register(Registries.SCREEN_HANDLER_TYPE, Dialabs.id("disc_burner_screen_handler"), DISC_BURNER);
    }

}