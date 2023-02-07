package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.block.*;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static net.diamonddev.dialabs.block.SyntheticEnchantmentTomeBlock.TOME_ITEM_SETTINGS;

public class InitBlocks implements RegistryInit {

    private static final HashMap<Block, BlockItem> hashedBlockItems = new HashMap<>();

    public static Block STATICITE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10F).requiresTool());
    public static Block SHOCKED_IRON_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(18F).requiresTool().luminance(2));
    public static EnchantmentSynthesizerBlock ENCHANTMENT_SYNTHESIZER = new EnchantmentSynthesizerBlock();
    public static DiscBurnerBlock DISC_BURNER = new DiscBurnerBlock();

    public static SoulBasinBlock SOUL_BASIN = new SoulBasinBlock(FabricBlockSettings.of(Material.METAL));

    public static final SyntheticEnchantmentTomeBlock ASPECTION_TOME = new SyntheticEnchantmentTomeBlock("aspection");
    public static final SyntheticEnchantmentTomeBlock DEFENSE_TOME = new SyntheticEnchantmentTomeBlock("defense");
    public static final SyntheticEnchantmentTomeBlock DESTRUCTIVE_TOME = new SyntheticEnchantmentTomeBlock("destructive");
    public static final SyntheticEnchantmentTomeBlock STRENGTH_TOME = new SyntheticEnchantmentTomeBlock("strength");

    @Override
    public void init() {
        registerBlockWithBlockItem(STATICITE_BLOCK, Dialabs.id.build("staticite_block"), new FabricItemSettings());
        registerBlockWithBlockItem(SHOCKED_IRON_BLOCK, Dialabs.id.build("shocked_iron_block"), new FabricItemSettings());
        registerBlockWithBlockItem(ENCHANTMENT_SYNTHESIZER, Dialabs.id.build("enchantment_synthesizer"), new FabricItemSettings());
        registerBlockWithBlockItem(DISC_BURNER, Dialabs.id.build("disc_burner"), new FabricItemSettings());

        registerBlockWithBlockItem(SOUL_BASIN, Dialabs.id.build("soul_basin"), new FabricItemSettings());

        registerBlockWithBlockItem(ASPECTION_TOME, Dialabs.id.build("aspection_tome"), TOME_ITEM_SETTINGS);
        registerBlockWithBlockItem(DEFENSE_TOME, Dialabs.id.build("defense_tome"), TOME_ITEM_SETTINGS);
        registerBlockWithBlockItem(DESTRUCTIVE_TOME, Dialabs.id.build("destructive_tome"), TOME_ITEM_SETTINGS);
        registerBlockWithBlockItem(STRENGTH_TOME, Dialabs.id.build("strength_tome"), TOME_ITEM_SETTINGS);
    }
    public static void registerBlockWithBlockItem(Block block, Identifier identifier, FabricItemSettings blockItemSettings) {
        BlockItem bi = new BlockItem(block, blockItemSettings);

        Registry.register(Registries.BLOCK, identifier, block);
        Registry.register(Registries.ITEM, identifier, bi);

        hashedBlockItems.put(block, bi);
    }

    public static BlockItem getBlockItem(Block block) {
        return hashedBlockItems.get(block);
    }
}
