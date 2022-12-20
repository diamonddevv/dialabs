package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.registry.InitItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {
    public static final ItemGroup SYNTHETIC_ENCHANT_GROUP = FabricItemGroup.builder(DiaLabs.id.build("synthetic_enchantments"))
            .icon(() -> new ItemStack(InitItem.SYNTHETIC_ENCHANTMENT_DISC))
            .build();

}
