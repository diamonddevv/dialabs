package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.api.DamageSources;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
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
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    @Nullable
    public abstract LivingEntity getAttacker();

    // called when 'this' takes damage
    @Inject(at = @At("HEAD"), method = "modifyAppliedDamage")
    private void dialabs$injectCrystallisingMethods(DamageSource source, float amount,
                                            CallbackInfoReturnable<Float> cir) {

        if (this.hasStatusEffect(InitEffects.CRYSTALLISE)) {

            int amp = Objects.requireNonNull(this.getStatusEffect(InitEffects.CRYSTALLISE)).getAmplifier() + 1;
            LivingEntity target = this.getAttacker();
            Random rand = new Random();

            if (target != null) {
                if (target.isAlive()) {
                    if (CrystalliseEffect.shouldDamageAttacker(amp, rand)) {
                        target.damage(DamageSources.CRYSTAL_SHARDS, (float) CrystalliseEffect.getDamageAmount(amp, rand));
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "modifyAppliedDamage", cancellable = true)
    private void dialabs$injectStaticDamage(DamageSource source, float amount,
                                            CallbackInfoReturnable<Float> cir) {
        if (source.getSource() instanceof LivingEntity attacker) {
            if (attacker.hasStatusEffect(InitEffects.CHARGE)) {
                int dur = Objects.requireNonNull(attacker.getStatusEffect(InitEffects.CHARGE)).getDuration();
                int amp = Objects.requireNonNull(attacker.getStatusEffect(InitEffects.CHARGE)).getAmplifier() + 1;

                cir.setReturnValue(ChargeEffect.calculateDamage(amount, amp, dur, 0.1F, this.hasStatusEffect(InitEffects.CHARGE)));
            }
        }
    }

}