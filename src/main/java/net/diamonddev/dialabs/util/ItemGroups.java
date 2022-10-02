package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.registry.InitBlocks;
import net.diamonddev.dialabs.registry.InitItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {

    public static final ItemGroup SYNTHETIC_ENCHANT_GROUP = FabricItemGroupBuilder.build(
            new Identifier("synthetic_enchantments"), () -> new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC));

}
