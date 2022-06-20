package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.api.DamageSources;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {

    @Final @Shadow private LivingEntity entity;

    // Called when Entity takes damage
    @Inject(method = "onDamage", at = @At(value = "HEAD"))
    public void injectChargedMethod(DamageSource damageSource, float originalHealth, float f, CallbackInfo ci) {

        Entity sourceEntity = damageSource.getAttacker();

        if (entity != null && sourceEntity instanceof LivingEntity && entity.isAlive()) {
            StatusEffectInstance statusEffectInstance = ((LivingEntity) sourceEntity).getStatusEffect(InitEffects.CHARGE);
            if (statusEffectInstance != null) {
                int duration = statusEffectInstance.getDuration();
                int amplifier = statusEffectInstance.getAmplifier() + 1;

                if (!entity.hasStatusEffect(InitEffects.CHARGE)) {
                    float getAdditionalDamage = ChargeEffect.calculateDamage(amplifier, duration, ChargeEffect.chargedStatusEffectAdditionalDamageBase, false);
                    if (entity != null) {
                        if (entity.isAlive()) {
                            entity.damage(DamageSources.chargeDamage((LivingEntity) sourceEntity), getAdditionalDamage);
                        }
                    }
                }
            }
        }
    }
}