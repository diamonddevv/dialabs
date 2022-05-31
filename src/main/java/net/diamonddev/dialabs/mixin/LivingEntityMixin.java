package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.api.DamageSources;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


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

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow @Nullable public abstract LivingEntity getAttacking();

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract void setAttacker(@Nullable LivingEntity attacker);

    @Shadow public abstract void onAttacking(Entity target);

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
}