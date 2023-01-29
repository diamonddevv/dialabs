package net.diamonddev.dialabs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.function.BiConsumer;


public class ThrowableItemEntity extends ThrownItemEntity {

    private BiConsumer<ThrowableItemEntity, HitResult> onCollideConsumer;
    public ThrowableItemEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setOnCollideConsumer(BiConsumer<ThrowableItemEntity, HitResult> consumer) {
        this.onCollideConsumer = consumer;
    }
    @Override
    protected Item getDefaultItem() {
        return Items.APPLE; // default to apples because i can
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (onCollideConsumer != null) {
            onCollideConsumer.accept(this, hitResult);
        } else {
            if (this.age > 1) {
                this.kill();
            }
        }
    }
}