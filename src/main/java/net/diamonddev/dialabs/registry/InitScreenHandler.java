package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class InitScreenHandler {

    public static ScreenHandlerType<EnchantmentSynthesisScreenHandler> ENCHANT_SYNTHESIS = new ScreenHandlerType<>(EnchantmentSynthesisScreenHandler::new);
    public static ScreenHandlerType<DiscBurnerScreenHandler> DISC_BURNER = new ScreenHandlerType<>(DiscBurnerScreenHandler::new);
    public static void register() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier("enchantment_synthesis_screen_handler"), ENCHANT_SYNTHESIS);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier("disc_burner_screen_handler"), DISC_BURNER);
    }

}