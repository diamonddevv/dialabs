package net.diamonddev.dialabs.world.explosion;

import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.registry.InitDamageTypeKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public interface IBombExplosionSettings {
    float getPower();

    default boolean createsFire() {
        return false;
    }

    default DamageSource getDamageSource(Entity target, Entity source) {
        return InitDamageTypeKeys.get(target, InitDamageTypeKeys.BOMB, source, null);
    }

    default boolean shouldDestroyBlocks() {
        return false;
    }

    default boolean shouldExplosionDamageEntities() {
        return true;
    }

    default void forEachBlockAffected(Entity source, World world, BlockPos blockPos, Explosion explosion, ThrowableItemEntity entity) {}
    default void forEachEntityAffected(Entity source, Entity entity, Explosion explosion, ThrowableItemEntity throwableItemEntity) {}

    default World.ExplosionSourceType getExplosionSourceType() {
        return World.ExplosionSourceType.BLOCK;
    }

    default boolean hasParticles() {
        return true;
    }
}
