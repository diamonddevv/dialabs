package net.diamonddev.dialabs;

import net.diamonddev.dialabs.api.DiaLabsGamerules;
import net.diamonddev.dialabs.init.InitBlocks;
import net.diamonddev.dialabs.init.InitEffects;
import net.diamonddev.dialabs.init.InitEnchants;
import net.diamonddev.dialabs.init.InitItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.MinecraftVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DiaLabs implements ModInitializer {

	// Mod ID and Version Variables
	public static final String MOD_ID = "dialabs";
	public static final String MOD_VER = "1.0.0b";
	public static String VERSION = getStringifiedModVer("v");
	public static String MC_VER = getStringifiedMcVer("v");

	// Get Metadata
	public static String getStringifiedModVer(String prefix) {
		VERSION = MOD_VER;
		if (prefix == null) {
			return VERSION;
		} else {
			return prefix + VERSION;
		}
	}

	public static String getStringifiedMcVer(String prefix) {
		String mcVer = MinecraftVersion.CURRENT.getName();

		if (prefix == null) {
			return mcVer;
		} else {
			return prefix + mcVer;
		}
	}

	// Logger
	public static final Logger LOGGER = LogManager.getLogger("DiaLabs");

	// Initializer
	@Override
	public void onInitialize() {

		InitItem.initializeItem();
        InitEffects.initializeEffects();
		InitBlocks.registerBlock();
		InitEnchants.register();
		DiaLabsGamerules.registerGamerules();




		LOGGER.info("Mod DiaLabs for Minecraft '" + MC_VER +"' has initialized with Mod ID '" + DiaLabs.MOD_ID + "' " +
				"and version " + DiaLabs.VERSION + ", Have fun messing about!");
	}
}
