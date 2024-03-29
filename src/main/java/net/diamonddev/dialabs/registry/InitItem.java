package net.diamonddev.dialabs.registry;


import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.item.*;
import net.diamonddev.dialabs.particle.InitParticle;
import net.diamonddev.dialabs.util.ExplosionHelper;
import net.diamonddev.dialabs.util.Helpers;
import net.diamonddev.dialabs.world.explosion.IBombExplosionSettings;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
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
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.function.BiConsumer;

public class InitItem implements RegistryInitializer {

    public static final Item STATICITE_INGOT = new Item(new QuiltItemSettings());
    public static final Item STATICITE_SCRAP = new Item(new QuiltItemSettings());
    public static final Item STATICITE_SCRAP_HEAP = new Item(new QuiltItemSettings());

    public static final Item ATTRILLITE_INGOT = new Item(new QuiltItemSettings());
    public static final Item ATTRILLITE_SCRAP = new Item(new QuiltItemSettings());


    public static final StaticCoreItem STATIC_CORE = new StaticCoreItem(new QuiltItemSettings().maxCount(4).rarity(Rarity.RARE));
    public static final CrystalShardItem CRYSTAL_SHARD = new CrystalShardItem(new QuiltItemSettings());


    public static final LightningBottleItem LIGHTNING_BOTTLE = new LightningBottleItem(new QuiltItemSettings().maxCount(8));


    public static final Item DEEPSLATE_PLATE = new Item(new QuiltItemSettings());
    public static final SyntheticEnchantmentDiscItem SYNTHETIC_ENCHANTMENT_DISC = new SyntheticEnchantmentDiscItem();

    public static final FlintlockItem FLINTLOCK = new FlintlockItem(new QuiltItemSettings());


    public static final ThrowableItem BOMB = createBomb(new QuiltItemSettings(), () -> 1.8f, null);
    public static final ThrowableItem SPARK_BOMB = createBomb(new QuiltItemSettings(), new IBombExplosionSettings() {
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
            entity.damage(InitDamageTypeKeys.get(entity, InitDamageTypeKeys.SPARK_BOMB, throwableItemEntity, source), (float) (12f * ExplosionHelper.getCustomCalculatedExposure(entity, throwableItemEntity.getPos(), explosion)));
            Helpers.spawnParticles(
                    entity.getWorld(),
                    InitParticle.SPARK.getParticleType(),
                    entity.getPos().x, entity.getPos().y, entity.getPos().z,
                    50, 0.5,
                    0.5, 0.5, 0.5
            );
        }

        @Override
        public void forEachBlockAffected(Entity source, World world, BlockPos blockPos, Explosion explosion, ThrowableItemEntity throwableItemEntity) {
            if (world.getBlockState(blockPos).getBlock() == Blocks.LIGHTNING_ROD) {

                LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
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
    public static final ThrowableItem ATTRILLITE_ARC = createBomb(new QuiltItemSettings(), new IBombExplosionSettings() {
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
    public static final ThrowableItem ROCK = new ThrowableItem(new QuiltItemSettings(), new ThrowableItem.ThrowableItemSettings().setOnCollideConsumer((entity, collision) -> {
        if (collision.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) collision).getEntity();
            target.damage(InitDamageTypeKeys.get(target, InitDamageTypeKeys.THWACK, entity, entity.getOwner()), 1f);
        }
        entity.kill();
    }));

    @Override
    public void register() {

        Registry.register(Registries.ITEM, Dialabs.id("staticite_ingot"), STATICITE_INGOT);
        Registry.register(Registries.ITEM, Dialabs.id("staticite_scrap"), STATICITE_SCRAP);
        Registry.register(Registries.ITEM, Dialabs.id("staticite_scrap_heap"), STATICITE_SCRAP_HEAP);

        Registry.register(Registries.ITEM, Dialabs.id("attrillite_ingot"), ATTRILLITE_INGOT);
        Registry.register(Registries.ITEM, Dialabs.id("attrillite_scrap"), ATTRILLITE_SCRAP);

        Registry.register(Registries.ITEM, Dialabs.id("static_core"), STATIC_CORE);
        Registry.register(Registries.ITEM, Dialabs.id("crystal_shard"), CRYSTAL_SHARD);
        Registry.register(Registries.ITEM, Dialabs.id("lightning_bottle"), LIGHTNING_BOTTLE);

        Registry.register(Registries.ITEM, Dialabs.id("deepslate_plate"), DEEPSLATE_PLATE);
        Registry.register(Registries.ITEM, Dialabs.id("synthetic_enchantment_disc"), SYNTHETIC_ENCHANTMENT_DISC);

        Registry.register(Registries.ITEM, Dialabs.id("flintlock"), FLINTLOCK);

        Registry.register(Registries.ITEM, Dialabs.id("bomb"), BOMB);
        Registry.register(Registries.ITEM, Dialabs.id("spark_bomb"), SPARK_BOMB);
        Registry.register(Registries.ITEM, Dialabs.id("attrillite_arc"), ATTRILLITE_ARC);

        Registry.register(Registries.ITEM, Dialabs.id("rock"), ROCK);

    }

    private static ThrowableItem createBomb(Item.Settings settings, IBombExplosionSettings bombSettings, BiConsumer<ThrowableItemEntity, HitResult> onCollideExtra) {
        return new ThrowableItem(settings,
                new ThrowableItem.ThrowableItemSettings().setOnCollideConsumer(
                        Helpers.and(
                                (entity, result) -> {
                                    ExplosionHelper.createDynamicBombExplosion(entity.getWorld(), entity, bombSettings);
                                    entity.kill();
                                },
                                onCollideExtra
                        )
                ).setCooldownTicks(20)
        );
    }
}
