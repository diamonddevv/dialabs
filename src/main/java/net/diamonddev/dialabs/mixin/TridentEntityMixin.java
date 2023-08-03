package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntityMixin {

    @Shadow private ItemStack tridentStack;

    public TridentEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/TridentEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"
            ),
            cancellable = true
    )
    private void dialabs$makeChannelingTwoWork(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = entityHitResult.getEntity();


        if (this.getWorld() instanceof ServerWorld && EnchantmentHelper.getLevel(Enchantments.CHANNELING, this.tridentStack) > 1) { // if this is serverworld and the trident has channeling greater than 1
            BlockPos blockPos = entity.getBlockPos();
            if (this.getWorld().isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.getWorld());
                if (lightningEntity != null) {
                    lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                    lightningEntity.setChanneler(this.getOwner() instanceof ServerPlayerEntity ? (ServerPlayerEntity)this.getOwner() : null);
                    this.getWorld().spawnEntity(lightningEntity);
                    SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
                    float g = 5.0F;

                    this.playSound(soundEvent, g, 1.0F);
                    ci.cancel(); // End invoke
                }
            }
        }
    }

    @Redirect(
            method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    private boolean dialabs$increasePrismarineThornsDamage(Entity instance, DamageSource source, float amount) {
        if (instance instanceof TridentEntity trident) {
            ItemStack stack = ((TridentEntityAccessor)trident).accessTridentStack();
            if (EnchantHelper.hasEnchantment(InitEnchants.PRISMARINE_SPIKES, stack)) {
                amount += EnchantHelper.getEnchantmentLevel(stack, InitEnchants.PRISMARINE_SPIKES) * 3f;
            }
        }
        return instance.damage(source, amount);
    }
}
