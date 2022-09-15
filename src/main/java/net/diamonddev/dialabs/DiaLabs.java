package net.diamonddev.dialabs;

import net.diamonddev.dialabs.util.DiaLabsGamerules;
import net.diamonddev.dialabs.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.SharedConstants;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;


public class DiaLabs implements ModInitializer {

	// Mod ID and Version Variables
	public static final String MOD_ID = "dialabs";
	public static String VERSION = FabricLoaderImpl.INSTANCE.getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static String MC_VER = SharedConstants.getGameVersion().getName();

	// Logger
	public static final Logger LOGGER = LogManager.getLogger("DiaLabs");

	// Initializer
	@Override
	public void onInitialize() {
		long startInitTime = System.currentTimeMillis();


		InitItem.initializeItem();
        InitEffects.initializeEffects();
		InitBlocks.registerBlock();
		InitEnchants.register();
		DiaLabsGamerules.registerGamerules();
		InitPotion.register();
		InitScreenHandler.register();
		InitRecipe.register();


		long initializationTime = System.currentTimeMillis() - startInitTime;
		LOGGER.info("DiaLabs {" + MOD_ID + " - " + VERSION + "} for Minecraft " + MC_VER + " has initialized (" + initializationTime + " milliseconds elapsed)");
	}

}
