package net.diamonddev.dialabs;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.registry.*;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.SharedConstants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.impl.QuiltLoaderImpl;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import static net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper.getBlockItem;
import static net.minecraft.item.ItemGroups.*;


public class Dialabs implements ModInitializer {

	// Mod ID and Version Variables
	public static final String MOD_ID = "dialabs";
	public static String VERSION = QuiltLoader.getModContainer(MOD_ID).orElseThrow().metadata().version().raw();
	public static String MC_VER = SharedConstants.getGameVersion().getName();

	// ID
	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	// Logger
	public static final Logger LOGGER = LogManager.getLogger("Dialabs");

	// Initializer
	@Override
	public void onInitialize(ModContainer mod) {
		long startInitTime = System.currentTimeMillis();

		addCallbackReferences(); // Keep BEFORE Registry Initialization

		new InitEntity().register();
		new InitItem().register();
        new InitEffects().register();
		new InitBlocks().register();
		new InitBlockEntity().register();
		new InitEnchants().register();
		new InitGamerules().register();
		new InitPotion().register();
		new InitScreenHandler().register();
		new InitRecipe().register();
		new InitSoundEvent().register();
		new InitResourceListener().register();
		new InitDamageTypeKeys().register();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

		});

		new ItemGroupEditor().register(); // Edit Item Groups - keep last!

		long initializationTime = System.currentTimeMillis() - startInitTime;
		LOGGER.info("DiaLabs {" + MOD_ID + " - " + VERSION + "} for Minecraft " + MC_VER + " has initialized (" + initializationTime + " milliseconds elapsed)");
	}


	private void addCallbackReferences() {
		// Synthetic Enchantment Registration
		RegistryEntryAddedCallback.event(Registries.ENCHANTMENT).register((rawId, identifier, enchantment) -> {
			if (enchantment instanceof SyntheticEnchantment synth) {
				SyntheticEnchantment.validSyntheticEnchantments.add(enchantment);

				int i = enchantment.getMaxLevel();
				if (synth.getMaxSyntheticLevel() >= 1) {
					i = synth.getMaxSyntheticLevel();
				}
				SyntheticEnchantment.hashSyntheticEnchantMaxLevel.put(enchantment, i);
			}
		});
	}
	private static class ItemGroupEditor implements RegistryInitializer {

		@Override
		public void register() {
			placeItemsInGroups();
		}

		public static void placeItemsInGroups() {

			ItemGroupEvents.modifyEntriesEvent(BUILDING_BLOCKS).register(content -> {
				content.addAfter(Items.NETHERITE_BLOCK, getBlockItem(InitBlocks.STATICITE_BLOCK));
				content.addAfter(Items.DEEPSLATE_EMERALD_ORE, getBlockItem(InitBlocks.SHOCKED_IRON_BLOCK));

				placeTomes(content);
			});

			ItemGroupEvents.modifyEntriesEvent(FUNCTIONAL_BLOCKS).register(content -> {
				content.addAfter(Items.ENCHANTING_TABLE, getBlockItem(InitBlocks.ENCHANTMENT_SYNTHESIZER));
				content.addAfter(getBlockItem(InitBlocks.ENCHANTMENT_SYNTHESIZER), getBlockItem(InitBlocks.DISC_BURNER));
				content.addAfter(getBlockItem(InitBlocks.DISC_BURNER), getBlockItem(InitBlocks.SOUL_BASIN));
			});

			ItemGroupEvents.modifyEntriesEvent(COMBAT).register(content -> {
				content.addItem(InitItem.BOMB);
				content.addItem(InitItem.SPARK_BOMB);
				content.addItem(InitItem.ATTRILLITE_ARC);
				content.addItem(InitItem.CRYSTAL_SHARD);
				content.addItem(InitItem.STATIC_CORE);
				content.addItem(InitItem.LIGHTNING_BOTTLE);
				content.addItem(InitItem.ROCK);
			});

			ItemGroupEvents.modifyEntriesEvent(INGREDIENTS).register(content -> {
				content.addAfter(Items.DISC_FRAGMENT_5, InitItem.DEEPSLATE_PLATE);

				content.addAfter(Items.NETHERITE_INGOT, InitItem.STATICITE_SCRAP);
				content.addAfter(InitItem.STATICITE_SCRAP, InitItem.STATICITE_SCRAP_HEAP);
				content.addAfter(InitItem.STATICITE_SCRAP_HEAP, InitItem.STATICITE_INGOT);

				content.addAfter(InitItem.STATICITE_INGOT, InitItem.ATTRILLITE_SCRAP);
				content.addAfter(InitItem.ATTRILLITE_SCRAP, InitItem.ATTRILLITE_INGOT);

				placeTomes(content);
			});


			ItemGroupEvents.modifyEntriesEvent(INGREDIENTS).register(content -> {
				content.addItem(getBlockItem(InitBlocks.ENCHANTMENT_SYNTHESIZER)); // first item
				content.addItem(getBlockItem(InitBlocks.DISC_BURNER));
				placeTomes(content);
				content.addItem(InitItem.SYNTHETIC_ENCHANTMENT_DISC);

				InitItem.SYNTHETIC_ENCHANTMENT_DISC.putSyntheticDiscStacks(content, ItemGroup.Visibility.PARENT_TAB_ONLY);
				InitItem.SYNTHETIC_ENCHANTMENT_DISC.putAllSyntheticDiscStacks(content, ItemGroup.Visibility.SEARCH_TAB_ONLY);
			});
		}

		private static void placeTomes(FabricItemGroupEntries content) {
			content.addItem(getBlockItem(InitBlocks.ASPECTION_TOME));
			content.addAfter(getBlockItem(InitBlocks.ASPECTION_TOME), getBlockItem(InitBlocks.DEFENSE_TOME));
			content.addAfter(getBlockItem(InitBlocks.DEFENSE_TOME), getBlockItem(InitBlocks.DESTRUCTIVE_TOME));
			content.addAfter(getBlockItem(InitBlocks.DESTRUCTIVE_TOME), getBlockItem(InitBlocks.STRENGTH_TOME));
		}
	}

	public static boolean isFunkyDevFeaturesOn() {
		return QuiltLoaderImpl.INSTANCE.isDevelopmentEnvironment();
	}

}
