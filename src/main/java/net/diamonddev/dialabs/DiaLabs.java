package net.diamonddev.dialabs;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.diamonddev.dialabs.lib.IdentifierBuilder;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.SharedConstants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static net.diamonddev.dialabs.registry.InitBlocks.getBlockItem;
import static net.diamonddev.dialabs.util.ItemGroups.SYNTHETIC_ENCHANT_GROUP;
import static net.minecraft.item.EnchantedBookItem.getEnchantmentNbt;
import static net.minecraft.item.ItemGroups.*;


public class DiaLabs implements ModInitializer {

	// Mod ID and Version Variables
	public static final String MOD_ID = "dialabs";
	public static String VERSION = FabricLoaderImpl.INSTANCE.getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static String MC_VER = SharedConstants.getGameVersion().getName();

	// ID BUILDER
	public static final IdentifierBuilder id = new IdentifierBuilder(MOD_ID);

	// Logger
	public static final Logger LOGGER = LogManager.getLogger("DiaLabs");

	// Initializer
	@Override
	public void onInitialize() {
		long startInitTime = System.currentTimeMillis();

		addCallbackReferences(); // Keep BEFORE Registry Initialization

		new InitItem().init();
        new InitEffects().init();
		new InitBlocks().init();
		new InitEnchants().init();
		new InitGamerules().init();
		new InitPotion().init();
		new InitScreenHandler().init();
		new InitRecipe().init();
		new InitSoundEvent().init();

		new ItemGroupEditor().init(); // Edit Item Groups - keep last!

		long initializationTime = System.currentTimeMillis() - startInitTime;
		LOGGER.info("DiaLabs {" + MOD_ID + " - " + VERSION + "} for Minecraft " + MC_VER + " has initialized (" + initializationTime + " milliseconds elapsed)");
	}


	private void addCallbackReferences() {
		// Synthetic Enchantment Registration
		RegistryEntryAddedCallback.event(Registries.ENCHANTMENT).register((rawId, identifier, enchantment) -> {
			if (enchantment instanceof SyntheticEnchantment) {
				SyntheticEnchantment.validSyntheticEnchantments.add(enchantment);
			}
		});
	}
	private static class ItemGroupEditor implements RegistryInit {

		@Override
		public void init() {
			placeItemsInGroups();
			removeItemsInGroups();
		}

		public static void placeItemsInGroups() {

			ItemGroupEvents.modifyEntriesEvent(BUILDING_BLOCKS).register(content -> {
				content.addAfter(Items.NETHERITE_BLOCK, getBlockItem(InitBlocks.STATICITE_BLOCK));
				content.addAfter(Items.DEEPSLATE_EMERALD_ORE, getBlockItem(InitBlocks.SHOCKED_IRON_BLOCK));

				placeTomes(content);
			});

			ItemGroupEvents.modifyEntriesEvent(FUNCTIONAL).register(content -> {
				content.addAfter(Items.ENCHANTING_TABLE, getBlockItem(InitBlocks.ENCHANTMENT_SYNTHESIZER));
				content.addAfter(getBlockItem(InitBlocks.ENCHANTMENT_SYNTHESIZER), getBlockItem(InitBlocks.DISC_BURNER));
			});

			ItemGroupEvents.modifyEntriesEvent(COMBAT).register(content -> {
				content.add(InitItem.CRYSTAL_SHARD);
				content.add(InitItem.STATIC_CORE);
				content.add(InitItem.LIGHTNING_BOTTLE);
			});

			ItemGroupEvents.modifyEntriesEvent(INGREDIENTS).register(content -> {
				content.addAfter(Items.DISC_FRAGMENT_5, InitItem.DEEPSLATE_PLATE);
				content.addAfter(Items.NETHERITE_INGOT, InitItem.STATICITE_SCRAP);
				content.addAfter(InitItem.STATICITE_SCRAP, InitItem.STATICITE_SCRAP_HEAP);
				content.addAfter(InitItem.STATICITE_SCRAP_HEAP, InitItem.STATICITE_INGOT);
				placeTomes(content);
			});


			ItemGroupEvents.modifyEntriesEvent(SYNTHETIC_ENCHANT_GROUP).register(content -> {
				content.add(getBlockItem(InitBlocks.ENCHANTMENT_SYNTHESIZER)); // first item
				content.add(getBlockItem(InitBlocks.DISC_BURNER));
				placeTomes(content);
				content.add(InitItem.SYNTHETIC_ENCHANTMENT_DISC);
				InitItem.SYNTHETIC_ENCHANTMENT_DISC.putSyntheticDiscStacks(content);
			});

		}

		public static void removeItemsInGroups() {
			ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
				if (group != SYNTHETIC_ENCHANT_GROUP) {
					entries.getDisplayStacks().removeIf(stack -> {
						boolean bl = false;
						if (stack.getItem() instanceof EnchantedBookItem) {
							Map<Enchantment, Integer> enchantMapData = EnchantmentHelper.fromNbt(getEnchantmentNbt(stack));
							for (Enchantment e : enchantMapData.keySet()) {
								if (e instanceof SyntheticEnchantment synth) {
									if (!synth.shouldMakeEnchantmentBook()) {
										bl = true;
										break;
									}
								}
							}
						}
						return bl;
					});
				}
			});
		}

		private static void placeTomes(FabricItemGroupEntries content) {
			content.add(getBlockItem(InitBlocks.ASPECTION_TOME));
			content.addAfter(getBlockItem(InitBlocks.ASPECTION_TOME), getBlockItem(InitBlocks.DEFENSE_TOME));
			content.addAfter(getBlockItem(InitBlocks.DEFENSE_TOME), getBlockItem(InitBlocks.DESTRUCTIVE_TOME));
			content.addAfter(getBlockItem(InitBlocks.DESTRUCTIVE_TOME), getBlockItem(InitBlocks.STRENGTH_TOME));
		}
	}

}
