package net.diamonddev.dialabs;

import net.diamonddev.dialabs.api.DiaLabsGamerules;
import net.diamonddev.dialabs.init.InitBlocks;
import net.diamonddev.dialabs.init.InitEffects;
import net.diamonddev.dialabs.init.InitEnchants;
import net.diamonddev.dialabs.init.InitItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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


		long initializationTime = System.currentTimeMillis() - startInitTime;
		LOGGER.info("DiaLabs {" + MOD_ID + " - " + VERSION + "} for Minecraft " + MC_VER + " has initialized (" + initializationTime + " milliseconds elapsed)");
	}
}
