package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.mixin.ExplosionAccessor;
import net.diamonddev.dialabs.world.explosion.BombExplosion;
import net.diamonddev.dialabs.world.explosion.IBombExplosionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;

public class ExplosionHelper {
    public static void createDynamicBombExplosion(World world, ThrowableItemEntity entity, IBombExplosionSettings settings) {
        Explosion explosion = world.createExplosion(
                entity.getOwner(),
                settings.getDamageSource(entity),
                new BombExplosion.DynamicExplosionBehavior(settings.shouldDestroyBlocks(), settings.shouldExplosionDamageEntities()),
                entity.getX(), entity.getY(), entity.getZ(),
                settings.getPower(), settings.createsFire(),
                settings.getExplosionSourceType()
        );


        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(settings.hasParticles());


        collectEntities(explosion).forEach((e) -> settings.forEachEntityAffected(entity.getOwner(), e));
        explosion.getAffectedBlocks().forEach((b) -> settings.forEachBlockAffected(entity.getOwner(), world, b));

    }


    private static Collection<Entity> collectEntities(Explosion explosion) { // copy-pasted from Explosion.class
        ExplosionAccessor accessedExplosion = (ExplosionAccessor) explosion;

        double q = accessedExplosion.getPower() * 2.0F;
        int k = MathHelper.floor(accessedExplosion.getX() - q - 1.0);
        int l = MathHelper.floor(accessedExplosion.getX() + q + 1.0);
        int r = MathHelper.floor(accessedExplosion.getY() - q - 1.0);
        int s = MathHelper.floor(accessedExplosion.getY() + q + 1.0);
        int t = MathHelper.floor(accessedExplosion.getZ() - q - 1.0);
        int u = MathHelper.floor(accessedExplosion.getZ() + q + 1.0);

        return accessedExplosion.getWorld().getOtherEntities(accessedExplosion.getEntity(), new Box(k, r, t, l, s, u));
    }

}
