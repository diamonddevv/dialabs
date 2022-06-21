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
	public static final String MOD_VER = "1.0.0";

	public enum ModReleaseType {
		RELEASE,
		BETA,
		ALPHA
	}
	public static String VERSION = getStringifiedModVer("v", ModReleaseType.BETA);
	public static String MC_VER = getStringifiedMcVer("");

	// Get Metadata
	public static String getStringifiedModVer(String prefix, ModReleaseType modReleaseType) {
		VERSION = MOD_VER;
		String suffix = null;

		if (modReleaseType == ModReleaseType.RELEASE) {
			suffix = "";
		} else if (modReleaseType == ModReleaseType.ALPHA) {
			suffix = "a";
		} else if (modReleaseType == ModReleaseType.BETA) {
			suffix = "b";
		}

		if (prefix == null) {
			return VERSION + suffix;
		} else {
			return prefix + VERSION + suffix;
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
