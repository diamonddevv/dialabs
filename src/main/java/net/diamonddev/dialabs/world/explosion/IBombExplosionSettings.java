package net.diamonddev.dialabs.world.explosion;

import net.diamonddev.dialabs.util.DialabsDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBombExplosionSettings {
    float getPower();

    default boolean createsFire() {
        return false;
    }

    default DamageSource getDamageSource(Entity source) {
        return DialabsDamageSource.bomb(source);
    }

    default boolean shouldDestroyBlocks() {
        return false;
    }

    default boolean shouldExplosionDamageEntities() {
        return true;
    }

    default void forEachBlockAffected(Entity source, World world, BlockPos blockPos) {}
    default void forEachEntityAffected(Entity source, Entity entity) {}

    default World.ExplosionSourceType getExplosionSourceType() {
        return World.ExplosionSourceType.BLOCK;
    }

    default boolean hasParticles() {
        return true;
    }
}
