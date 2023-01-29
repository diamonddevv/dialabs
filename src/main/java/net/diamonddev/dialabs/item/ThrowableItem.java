package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.registry.InitEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public class ThrowableItem extends Item {
    private final ThrowableItemSettings throwableSettings;

    public ThrowableItem(Settings settings, ThrowableItemSettings throwableSettings) {
        super(settings);
        this.throwableSettings = throwableSettings;

        // register dispenser behavior for throwable items
        DispenserBlock.registerBehavior(this, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                ThrowableItem item = (ThrowableItem) stack.getItem();
                ThrowableItemEntity entity = InitEntity.THROWN_ITEM.create(world);

                entity.setOnCollideConsumer(item.throwableSettings.onCollideConsumer);
                entity.setItem(stack);

                return entity;
            }
        });
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (!playerEntity.isCreative()) {
            playerEntity.getItemCooldownManager().set(playerEntity.getStackInHand(hand).getItem(), this.throwableSettings.cooldownTicks);
        }

        ItemStack stackInHand = playerEntity.getStackInHand(hand);

        world.playSoundFromEntity(null, playerEntity, this.throwableSettings.sound, SoundCategory.NEUTRAL, 1.0f, 1.0f);

        if (!world.isClient) {
            ThrowableItemEntity entity = InitEntity.THROWN_ITEM.create(world);

            entity.setOnCollideConsumer(this.throwableSettings.onCollideConsumer);
            entity.setItem(stackInHand);

            entity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 1.5F, 1.0F);
            entity.setPos(playerEntity.getX(), playerEntity.getY() + (double) playerEntity.getStandingEyeHeight() - 0.10000000149011612D, playerEntity.getZ());
            entity.setOwner(playerEntity);
            world.spawnEntity(entity);
        }

        if (!playerEntity.getAbilities().creativeMode) {
            stackInHand.decrement(1);
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
    }

    public static class ThrowableItemSettings {
        private BiConsumer<ThrowableItemEntity, HitResult> onCollideConsumer;
        private SoundEvent sound;
        private int cooldownTicks;

        public ThrowableItemSettings() {
            this.onCollideConsumer = null;
            this.sound = SoundEvents.ENTITY_SNOWBALL_THROW;
            this.cooldownTicks = 0;
        }

        ///

        public ThrowableItemSettings setOnCollideConsumer(BiConsumer<ThrowableItemEntity, HitResult> consumer) {
            this.onCollideConsumer = consumer;
            return this;
        }

        public ThrowableItemSettings setSound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        public ThrowableItemSettings setCooldownTicks(int cooldownTicks) {
            this.cooldownTicks = cooldownTicks;
            return this;
        }
    }
}
