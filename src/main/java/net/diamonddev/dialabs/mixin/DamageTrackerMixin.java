package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.api.DamageSources;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {

    @Shadow LivingEntity entity;

    // Called when Entity takes damage
    @Inject(method = "onDamage", at = @At(value = "TAIL"))
    public void injectChargedMethod(DamageSource damageSource, float originalHealth, float f, CallbackInfo ci) {

        Entity sourceEntity = damageSource.getAttacker();

        if (entity != null && sourceEntity instanceof LivingEntity && entity.isAlive()) {

            try {
                StatusEffectInstance statusEffectInstance = ((LivingEntity) sourceEntity).getStatusEffect(InitEffects.CHARGE);
                if (statusEffectInstance != null) {
                    int duration = statusEffectInstance.getDuration();
                    int amplifier = statusEffectInstance.getAmplifier() + 1;
                    if (entity.hasStatusEffect(InitEffects.CHARGE)) {
                        float getAdditionalDamage = ChargeEffect.calculateAdditionalDamage(amplifier, duration, ChargeEffect.chargedStatusEffectAdditionalDamageBase, true);
                        if (entity != null) {
                            if (entity.isAlive()) {
                                entity.damage(DamageSources.CHARGE, (float) getAdditionalDamage);
                            } else {
                                DiaLabs.LOGGER.warn("Couldn't inflict additional Charge Damage");
                            }
                        } else {
                            DiaLabs.LOGGER.warn("Couldn't inflict additional Charge Damage");
                        }

                    } else {
                        float getAdditionalDamage = ChargeEffect.calculateAdditionalDamage(amplifier, duration, ChargeEffect.chargedStatusEffectAdditionalDamageBase, false);
                        if (entity != null) {
                            if (entity.isAlive()) {
                                entity.damage(DamageSources.CHARGE, (float) getAdditionalDamage);
                            } else {
                                DiaLabs.LOGGER.warn("Couldn't inflict additional Charge Damage");
                            }
                        } else {
                            DiaLabs.LOGGER.warn("Couldn't inflict additional Charge Damage");
                        }
                    }
                }
            } catch (NullPointerException ignored) {
                DiaLabs.LOGGER.warn("Couldn't inflict additional Charge Damage");
            }
        }
    }
}
