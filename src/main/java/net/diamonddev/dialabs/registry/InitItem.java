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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.function.BiConsumer;

public class InitItem implements RegistryInit {

    public static final Item STATICITE_INGOT = new Item(new FabricItemSettings());
    public static final Item STATICITE_SCRAP = new Item(new FabricItemSettings());
    public static final Item STATICITE_SCRAP_HEAP = new Item(new FabricItemSettings());

    public static final Item ATTRILLITE_INGOT = new Item(new FabricItemSettings());
    public static final Item ATTRILLITE_SCRAP = new Item(new FabricItemSettings());


    public static final StaticCoreItem STATIC_CORE = new StaticCoreItem(new FabricItemSettings().maxCount(4).rarity(Rarity.RARE));
    public static final CrystalShardItem CRYSTAL_SHARD = new CrystalShardItem(new FabricItemSettings());


    public static final LightningBottleItem LIGHTNING_BOTTLE = new LightningBottleItem(new FabricItemSettings().maxCount(8));


    public static final Item DEEPSLATE_PLATE = new Item(new FabricItemSettings());
    public static final SyntheticEnchantmentDiscItem SYNTHETIC_ENCHANTMENT_DISC = new SyntheticEnchantmentDiscItem();

    public static final FlintlockItem FLINTLOCK = new FlintlockItem(new FabricItemSettings());


    public static final ThrowableItem BOMB = createBomb(new FabricItemSettings(), () -> 1.8f, null);
    public static final ThrowableItem SPARK_BOMB = createBomb(new FabricItemSettings(), new IBombExplosionSettings() {
        @Override
        public float getPower() {
            return 3.5f;
        }

        @Override
        public boolean shouldExplosionDamageEntities() {
            return false;
        }

        @Override
        public void forEachEntityAffected(Entity source, Entity entity, Explosion explosion, ThrowableItemEntity throwableItemEntity) {
            entity.damage(DialabsDamageSource.sparkBomb(source), (float) (12f * ExplosionHelper.getCustomCalculatedExposure(entity, throwableItemEntity.getPos(), explosion)));
            Helpers.spawnParticles(
                    entity.world,
                    InitParticle.SPARK.getParticleType(),
                    entity.getPos().x, entity.getPos().y, entity.getPos().z,
                    50, 0.5,
                    0.5, 0.5, 0.5
            );
        }

        @Override
        public void forEachBlockAffected(Entity source, World world, BlockPos blockPos, Explosion explosion, ThrowableItemEntity throwableItemEntity) {
            if (world.getBlockState(blockPos).getBlock() == Blocks.LIGHTNING_ROD) {

                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos.up()));
                if (source instanceof ServerPlayerEntity s) lightningEntity.setChanneler(s);

                world.spawnEntity(lightningEntity);
            }

        }

        @Override
        public boolean hasParticles() {
            return false;
        }
    }, null);
    public static final ThrowableItem ATTRILLITE_ARC = createBomb(new FabricItemSettings(), new IBombExplosionSettings() {
        @Override
        public float getPower() {
            return 5f;
        }

        @Override
        public boolean shouldExplosionDamageEntities() {
            return false;
        }

        @Override
        public void forEachEntityAffected(Entity source, Entity entity, Explosion explosion, ThrowableItemEntity throwableItemEntity) {
            if (ExplosionHelper.getCustomCalculatedExposure(entity, throwableItemEntity.getPos(), explosion) > 0f) {
                if (entity instanceof LivingEntity living) {
                    living.addStatusEffect(new StatusEffectInstance(InitEffects.ATTRILLITE_POISON, 300), source);
                }
            }
        }
    }, null);

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

        Registry.register(Registries.ITEM, Dialabs.id.build("attrillite_ingot"), ATTRILLITE_INGOT);
        Registry.register(Registries.ITEM, Dialabs.id.build("attrillite_scrap"), ATTRILLITE_SCRAP);

        Registry.register(Registries.ITEM, Dialabs.id.build("static_core"), STATIC_CORE);
        Registry.register(Registries.ITEM, Dialabs.id.build("crystal_shard"), CRYSTAL_SHARD);
        Registry.register(Registries.ITEM, Dialabs.id.build("lightning_bottle"), LIGHTNING_BOTTLE);

        Registry.register(Registries.ITEM, Dialabs.id.build("deepslate_plate"), DEEPSLATE_PLATE);
        Registry.register(Registries.ITEM, Dialabs.id.build("synthetic_enchantment_disc"), SYNTHETIC_ENCHANTMENT_DISC);

        Registry.register(Registries.ITEM, Dialabs.id.build("flintlock"), FLINTLOCK);

        Registry.register(Registries.ITEM, Dialabs.id.build("bomb"), BOMB);
        Registry.register(Registries.ITEM, Dialabs.id.build("spark_bomb"), SPARK_BOMB);
        Registry.register(Registries.ITEM, Dialabs.id.build("attrillite_arc"), ATTRILLITE_ARC);

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
