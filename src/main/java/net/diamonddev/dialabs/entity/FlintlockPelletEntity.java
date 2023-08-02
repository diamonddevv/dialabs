package net.diamonddev.dialabs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class FlintlockPelletEntity extends PersistentProjectileEntity {

    public FlintlockPelletEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            LivingEntity target = (LivingEntity) ((EntityHitResult) hitResult).getEntity();
            target.damage(this.getDamageSources().generic(), 5f);

            kill();
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            kill();
        }
    }



    @Override
    protected void initDataTracker() {

    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.IRON_NUGGET);
    }
}
