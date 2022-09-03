package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.item.*;
import net.diamonddev.dialabs.item.synthesis.AspectionTomeItem;
import net.diamonddev.dialabs.util.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class InitItem {

    public static final Item STATICITE_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item STATICITE_SCRAP = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item STATICITE_SCRAP_HEAP = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item DEEPSLATE_PLATE = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final StaticCoreItem STATIC_CORE = new StaticCoreItem(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(4).rarity(Rarity.RARE));
    public static final CrystalShardItem CRYSTAL_SHARD = new CrystalShardItem(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final LightningBottleItem LIGHTNING_BOTTLE = new LightningBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(8));

    public static final SyntheticEnchantmentDiscItem SYNTHETIC_ENCHANTMENT_DISC = new SyntheticEnchantmentDiscItem();
    public static final AspectionTomeItem ASPECTION_TOME = new AspectionTomeItem(new FabricItemSettings().group(ItemGroups.SYNTHESIS_INGREDIENTS));

    public static void initializeItem() {

        Registry.register(Registry.ITEM, new Identifier("staticite_ingot"), STATICITE_INGOT);
        Registry.register(Registry.ITEM, new Identifier("staticite_scrap"), STATICITE_SCRAP);
        Registry.register(Registry.ITEM, new Identifier("staticite_scrap_heap"), STATICITE_SCRAP_HEAP);
        Registry.register(Registry.ITEM, new Identifier("deepslate_plate"), DEEPSLATE_PLATE);
        Registry.register(Registry.ITEM, new Identifier("static_core"), STATIC_CORE);
        Registry.register(Registry.ITEM, new Identifier("crystal_shard"), CRYSTAL_SHARD);
        Registry.register(Registry.ITEM, new Identifier("lightning_bottle"), LIGHTNING_BOTTLE);

        Registry.register(Registry.ITEM, new Identifier("synthetic_enchantment_disc"), SYNTHETIC_ENCHANTMENT_DISC);
        Registry.register(Registry.ITEM, new Identifier("aspection_tome"), ASPECTION_TOME);

    }
}