package net.diamonddev.dialabs.init;

import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.screen.EnchantmentSynthesisScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class InitScreenHandler {

    public static ScreenHandlerType<EnchantmentSynthesisScreenHandler> ENCHANT_SYNTHESIS;

    public static void register() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier("enchant_synthesis_screen_handler"), new ScreenHandlerType<ScreenHandler>(EnchantmentScreenHandler::new));
    }
}
