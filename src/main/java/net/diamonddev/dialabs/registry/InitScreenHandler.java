package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.gui.DiscBurnerScreenHandler;
import net.diamonddev.dialabs.gui.EnchantmentSynthesisScreenHandler;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class InitScreenHandler implements RegistryInit {

    public static ScreenHandlerType<EnchantmentSynthesisScreenHandler> ENCHANT_SYNTHESIS = new ScreenHandlerType<>(EnchantmentSynthesisScreenHandler::new);
    public static ScreenHandlerType<DiscBurnerScreenHandler> DISC_BURNER = new ScreenHandlerType<>(DiscBurnerScreenHandler::new);
    public void init() {
        Registry.register(Registries.SCREEN_HANDLER, DiaLabs.id.build("enchantment_synthesis_screen_handler"), ENCHANT_SYNTHESIS);
        Registry.register(Registries.SCREEN_HANDLER, DiaLabs.id.build("disc_burner_screen_handler"), DISC_BURNER);
    }

}