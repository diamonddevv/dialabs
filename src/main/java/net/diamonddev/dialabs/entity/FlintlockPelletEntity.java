package net.diamonddev.dialabs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class FlintlockPelletEntity extends ProjectileEntity {

    public FlintlockPelletEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            LivingEntity target = (LivingEntity) ((EntityHitResult) hitResult).getEntity();
            target.damage(DamageSource.GENERIC, 5f);

            kill();
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            kill();
        }
    }



    @Override
    protected void initDataTracker() {

    }
}
