package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.item.*;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.particle.InitParticle;
import net.diamonddev.dialabs.util.DialabsDamageSource;
import net.diamonddev.dialabs.util.ExplosionHelper;
import net.diamonddev.dialabs.util.Helpers;
import net.diamonddev.dialabs.world.explosion.IBombExplosionSettings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public class InitItem implements RegistryInit {

    public static final Item STATICITE_INGOT = new Item(new FabricItemSettings());
    public static final Item STATICITE_SCRAP = new Item(new FabricItemSettings());
    public static final Item STATICITE_SCRAP_HEAP = new Item(new FabricItemSettings());


    public static final StaticCoreItem STATIC_CORE = new StaticCoreItem(new FabricItemSettings().maxCount(4).rarity(Rarity.RARE));
    public static final CrystalShardItem CRYSTAL_SHARD = new CrystalShardItem(new FabricItemSettings());


    public static final LightningBottleItem LIGHTNING_BOTTLE = new LightningBottleItem(new FabricItemSettings().maxCount(8));


    public static final Item DEEPSLATE_PLATE = new Item(new FabricItemSettings());
    public static final SyntheticEnchantmentDiscItem SYNTHETIC_ENCHANTMENT_DISC = new SyntheticEnchantmentDiscItem();


    public static final ThrowableItem BOMB = createBomb(new FabricItemSettings(), () -> 1.25f, null);
    public static final ThrowableItem SPARK_BOMB = createBomb(new FabricItemSettings(), new IBombExplosionSettings() {
        @Override
        public float getPower() {
            return 2.5f;
        }

        @Override
        public boolean shouldExplosionDamageEntities() {
            return false;
        }

        @Override
        public void forEachEntityAffected(Entity source, Entity entity) {
            entity.damage(DialabsDamageSource.sparkBomb(source), 6f);
        }

        @Override
        public void forEachBlockAffected(Entity source, World world, BlockPos blockPos) {
            if (world.getBlockState(blockPos).getBlock() == Blocks.IRON_BLOCK)
        }

        @Override
        public boolean hasParticles() {
            return false;
        }
    }, (entity, collision) -> Helpers.spawnParticles(
            entity.world,
            InitParticle.SPARK.getParticleType(),
            collision.getPos().x, collision.getPos().y, collision.getPos().z,
            100, 0,
            5, 5, 5));


    public static final ThrowableItem ROCK = new ThrowableItem(new FabricItemSettings(), new ThrowableItem.ThrowableItemSettings().setOnCollideConsumer((entity, collision) -> {
        if (collision.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) collision).getEntity();
            target.damage(DialabsDamageSource.thwack(entity.getOwner()), 1f);
        }
        entity.kill();
    }));
    @Override
    public void init() {

        Registry.register(Registries.ITEM, Dialabs.id.build("staticite_ingot"), STATICITE_INGOT);
        Registry.register(Registries.ITEM, Dialabs.id.build("staticite_scrap"), STATICITE_SCRAP);
        Registry.register(Registries.ITEM, Dialabs.id.build("staticite_scrap_heap"), STATICITE_SCRAP_HEAP);

        Registry.register(Registries.ITEM, Dialabs.id.build("static_core"), STATIC_CORE);
        Registry.register(Registries.ITEM, Dialabs.id.build("crystal_shard"), CRYSTAL_SHARD);
        Registry.register(Registries.ITEM, Dialabs.id.build("lightning_bottle"), LIGHTNING_BOTTLE);

        Registry.register(Registries.ITEM, Dialabs.id.build("deepslate_plate"), DEEPSLATE_PLATE);
        Registry.register(Registries.ITEM, Dialabs.id.build("synthetic_enchantment_disc"), SYNTHETIC_ENCHANTMENT_DISC);

        Registry.register(Registries.ITEM, Dialabs.id.build("bomb"), BOMB);
        Registry.register(Registries.ITEM, Dialabs.id.build("spark_bomb"), SPARK_BOMB);

        Registry.register(Registries.ITEM, Dialabs.id.build("rock"), ROCK);

    }

    private static ThrowableItem createBomb(Item.Settings settings, IBombExplosionSettings bombSettings, BiConsumer<ThrowableItemEntity, HitResult> onCollideExtra) {
        return new ThrowableItem(settings,
                new ThrowableItem.ThrowableItemSettings().setOnCollideConsumer(
                        Helpers.and(
                                (entity, result) -> {
                                    ExplosionHelper.createDynamicBombExplosion(entity.world, entity, bombSettings);
                                    entity.kill();
                                },
                                onCollideExtra
                        )
                ).setCooldownTicks(20)
        );
    }
}
