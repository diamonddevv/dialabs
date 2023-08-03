package net.diamonddev.dialabs.util;

import com.google.common.collect.Sets;
import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.mixin.ExplosionAccessor;
import net.diamonddev.dialabs.world.explosion.BombExplosion;
import net.diamonddev.dialabs.world.explosion.IBombExplosionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;
import java.util.Set;

public class ExplosionHelper {
    public static void createDynamicBombExplosion(World world, ThrowableItemEntity entity, IBombExplosionSettings settings) {
        Explosion explosion = world.createExplosion(
                null,
                settings.getDamageSource(entity, entity),
                new BombExplosion.DynamicExplosionBehavior(settings.shouldDestroyBlocks(), settings.shouldExplosionDamageEntities()),
                entity.getX(), entity.getY(), entity.getZ(),
                settings.getPower(), settings.createsFire(),
                settings.getExplosionSourceType()
        );


        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(settings.hasParticles());

        collectEntities(entity, explosion, entity.getOwner()).forEach((e) -> settings.forEachEntityAffected(entity.getOwner(), e, explosion, entity));
        collectBlocks(explosion).forEach((b) -> settings.forEachBlockAffected(entity.getOwner(), world, b, explosion, entity));
    }

    public static double getCustomCalculatedExposure(Entity entity, Vec3d origin, Explosion explosion) {
        double distance = Math.sqrt(entity.squaredDistanceTo(origin));

        double d = 1.0;
        double falloffRate = 1 / (((ExplosionAccessor) explosion).getPower() * 2);

        d -= distance * falloffRate;

        return d <= 0 ? 0 : d;
    }

    private static Collection<Entity> collectEntities(ThrowableItemEntity tie, Explosion explosion, Entity owner) { // copy-pasted from Explosion.class and modified
        ExplosionAccessor accessedExplosion = (ExplosionAccessor) explosion;

        double q = accessedExplosion.getPower() * 2.0F;
        int k = MathHelper.floor(accessedExplosion.getX() - q - 1.0);
        int l = MathHelper.floor(accessedExplosion.getX() + q + 1.0);
        int r = MathHelper.floor(accessedExplosion.getY() - q - 1.0);
        int s = MathHelper.floor(accessedExplosion.getY() + q + 1.0);
        int t = MathHelper.floor(accessedExplosion.getZ() - q - 1.0);
        int u = MathHelper.floor(accessedExplosion.getZ() + q + 1.0);

       Collection<Entity> entity = accessedExplosion.getWorld().getOtherEntities(owner, new Box(k, r, t, l, s, u));
       entity.add(owner);
       entity.remove(tie);

       entity.removeIf(e -> e instanceof PlayerEntity pe && pe.isCreative());

       return entity;
    }

    private static Collection<BlockPos> collectBlocks(Explosion explosion) { // copy-pasted from Explosion.class and modified
        ExplosionAccessor accessedExplosion = (ExplosionAccessor) explosion;

        Set<BlockPos> set = Sets.newHashSet();

        int k;
        int l;
        for(int j = 0; j < 16; ++j) {
            for(k = 0; k < 16; ++k) {
                for(l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {


                        double d = (j / 15.0F * 2.0F - 1.0F);
                        double e = (k / 15.0F * 2.0F - 1.0F);
                        double f = (l / 15.0F * 2.0F - 1.0F);
                        double g = Math.sqrt(d * d + e * e + f * f);
                        d /= g;
                        e /= g;
                        f /= g;

                        float h = accessedExplosion.getPower() * (0.7F + accessedExplosion.getWorld().random.nextFloat() * 0.6F);
                        double m = accessedExplosion.getX();
                        double n = accessedExplosion.getY();
                        double o = accessedExplosion.getZ();

                        for(; h > 0.0F; h -= 0.22500001F) {
                            BlockPos blockPos = BlockPos.create(m, n, o);
                            if (!accessedExplosion.getWorld().isInBuildLimit(blockPos)) {
                                break;
                            }

                            set.add(blockPos);

                            m += d * 0.30000001192092896;
                            n += e * 0.30000001192092896;
                            o += f * 0.30000001192092896;
                        }
                    }
                }
            }
        }

        return set;
    }

}
