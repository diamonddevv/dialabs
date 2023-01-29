package net.diamonddev.dialabs.world.explosion;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

public class BombExplosion extends Explosion {


    public BombExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, DestructionType destructionType) {
        super(world, entity, damageSource, behavior, x, y, z, power, createFire, destructionType);
    }


    public static class DynamicExplosionBehavior extends ExplosionBehavior {
        private final boolean destroysBlocks;
        private final boolean damagesEntities;

        public DynamicExplosionBehavior(boolean destroysBlocks, boolean damagesEntities) {
            super();
            this.destroysBlocks = destroysBlocks;
            this.damagesEntities = damagesEntities;
        }

        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            return this.destroysBlocks;
        }

        public boolean getDamagesEntities() {
            return damagesEntities;
        }


    }
}
