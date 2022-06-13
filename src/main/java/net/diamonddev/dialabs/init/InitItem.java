package net.diamonddev.dialabs.init;


import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.item.CrystalShardItem;
import net.diamonddev.dialabs.item.LightningBottleItem;
import net.diamonddev.dialabs.item.StaticCoreItem;
import net.diamonddev.dialabs.item.material.ChargePlatedMaterial;
import net.diamonddev.dialabs.item.tool.Pickaxe;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class InitItem {

    public static final Item STATICITE_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item STATICITE_SCRAP = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item STATICITE_SCRAP_HEAP = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item STATIC_PLATING = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item STATIC_PICK = new Pickaxe(ChargePlatedMaterial.INSTANCE, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS));
    public static final StaticCoreItem STATIC_CORE = new StaticCoreItem(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(4).rarity(Rarity.RARE));
    public static final CrystalShardItem CRYSTAL_SHARD = new CrystalShardItem(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final LightningBottleItem LIGHTNING_BOTTLE = new LightningBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(8));

    public static void initializeItem() {

        Registry.register(Registry.ITEM, new Identifier("staticite_ingot"), STATICITE_INGOT);
        Registry.register(Registry.ITEM, new Identifier("staticite_scrap"), STATICITE_SCRAP);
        Registry.register(Registry.ITEM, new Identifier("staticite_scrap_heap"), STATICITE_SCRAP_HEAP);
        Registry.register(Registry.ITEM, new Identifier("static_plating"), STATIC_PLATING);
        Registry.register(Registry.ITEM, new Identifier("staticite_plated_pickaxe"), STATIC_PICK);
        Registry.register(Registry.ITEM, new Identifier("static_core"), STATIC_CORE);
        Registry.register(Registry.ITEM, new Identifier("crystal_shard"), CRYSTAL_SHARD);
        Registry.register(Registry.ITEM, new Identifier("lightning_bottle"), LIGHTNING_BOTTLE);

    }
}
