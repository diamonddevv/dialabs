package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.item.CrystalShardItem;
import net.diamonddev.dialabs.item.LightningBottleItem;
import net.diamonddev.dialabs.item.StaticCoreItem;
import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class InitItem implements RegistryInit {

    public static final Item STATICITE_INGOT = new Item(new FabricItemSettings());
    public static final Item STATICITE_SCRAP = new Item(new FabricItemSettings());
    public static final Item STATICITE_SCRAP_HEAP = new Item(new FabricItemSettings());
    public static final Item DEEPSLATE_PLATE = new Item(new FabricItemSettings());
    public static final StaticCoreItem STATIC_CORE = new StaticCoreItem(new FabricItemSettings().maxCount(4).rarity(Rarity.RARE));
    public static final CrystalShardItem CRYSTAL_SHARD = new CrystalShardItem(new FabricItemSettings());
    public static final LightningBottleItem LIGHTNING_BOTTLE = new LightningBottleItem(new FabricItemSettings().maxCount(8));

    public static final SyntheticEnchantmentDiscItem SYNTHETIC_ENCHANTMENT_DISC = new SyntheticEnchantmentDiscItem();


    @Override
    public void init() {

        Registry.register(Registries.ITEM, DiaLabs.id.build("staticite_ingot"), STATICITE_INGOT);
        Registry.register(Registries.ITEM, DiaLabs.id.build("staticite_scrap"), STATICITE_SCRAP);
        Registry.register(Registries.ITEM, DiaLabs.id.build("staticite_scrap_heap"), STATICITE_SCRAP_HEAP);
        Registry.register(Registries.ITEM, DiaLabs.id.build("static_core"), STATIC_CORE);
        Registry.register(Registries.ITEM, DiaLabs.id.build("crystal_shard"), CRYSTAL_SHARD);
        Registry.register(Registries.ITEM, DiaLabs.id.build("lightning_bottle"), LIGHTNING_BOTTLE);

        Registry.register(Registries.ITEM, DiaLabs.id.build("synthetic_enchantment_disc"), SYNTHETIC_ENCHANTMENT_DISC);

    }
}
