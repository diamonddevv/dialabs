package net.diamonddev.dialabs.init;

import net.diamonddev.dialabs.api.Identifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class InitBlocks {

    public static Block STATICITE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(10F).requiresTool());
    public static Block SHOCKED_IRON_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(18F).requiresTool().luminance(2));

    public static void registerBlock() {

        registerBlockWithBlockItem(STATICITE_BLOCK, new Identifier("staticite_block"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
        registerBlockWithBlockItem(SHOCKED_IRON_BLOCK, new Identifier("shocked_iron_block"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));

    }


    public static void registerBlockWithBlockItem(Block block, Identifier identifier, FabricItemSettings blockItemSettings) {
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, blockItemSettings));
    }
}
