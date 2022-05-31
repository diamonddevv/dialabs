package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.api.DamageSources;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract Random getRandom();

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    @Nullable
    public abstract LivingEntity getAttacker();

    // called when 'this' takes damage
    @Inject(at = @At("HEAD"), method = "applyEnchantmentsToDamage", cancellable = true)
    private void injectCrystallisingMethods(DamageSource source, float amount,
                                            CallbackInfoReturnable<Float> cir) {

        if (this.hasStatusEffect(InitEffects.CRYSTALLISE)) {

            int amp = Objects.requireNonNull(this.getStatusEffect(InitEffects.CRYSTALLISE)).getAmplifier() + 1;
            LivingEntity target = this.getAttacker();
            Random rand = this.getRandom();

            if (target != null) {
                if (target.isAlive()) {
                    if (CrystalliseEffect.shouldDamageAttacker(amp, rand)) {
                        target.damage(DamageSources.CRYSTAL_SHARDS, (float) CrystalliseEffect.getDamageAmount(amp, rand));
                    }
                }
            }
        }
    }

    // Called when Entity takes damage
    @Inject(method = "applyEnchantmentsToDamage", at = @At(value = "TAIL"))
    public void injectChargedMethod(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {

            Entity sourceEntity = source.getAttacker();
            LivingEntityMixin entity = this;

            if (sourceEntity instanceof LivingEntity && entity.isAlive()) {

                try {
                    StatusEffectInstance statusEffectInstance = ((LivingEntity) sourceEntity).getStatusEffect(InitEffects.CHARGE);
                    if (statusEffectInstance != null) {
                        int duration = statusEffectInstance.getDuration();
                        int amplifier = statusEffectInstance.getAmplifier() + 1;
                        if (entity.hasStatusEffect(InitEffects.CHARGE)) {
                            float getAdditionalDamage = ChargeEffect.calculateAdditionalDamage(amplifier, duration, ChargeEffect.chargedStatusEffectAdditionalDamageBase, true);
                            if (entity.isAlive()) {
                                entity.damage(DamageSources.CHARGE, (float) getAdditionalDamage);
                            } else {
                                DiaLabs.LOGGER.warn("Couldn't inflict additional Charge Damage");
                            }

                        } else {
                            float getAdditionalDamage = ChargeEffect.calculateAdditionalDamage(amplifier, duration, ChargeEffect.chargedStatusEffectAdditionalDamageBase, false);
                            if (entity.isAlive()) {
                                entity.damage(DamageSources.CHARGE, (float) getAdditionalDamage);
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