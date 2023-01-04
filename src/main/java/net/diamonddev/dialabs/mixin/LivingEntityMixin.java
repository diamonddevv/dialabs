package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.cca.DialabsCCA;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.registry.InitEffects;
import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.DialabsDamageSource;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    Random r = new Random();

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    @Nullable
    public abstract LivingEntity getAttacker();

    @Shadow public abstract boolean shouldDropXp();

    @Shadow public abstract int getXpToDrop();

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
                        target.damage(DialabsDamageSource.CRYSTAL_SHARDS, (float) CrystalliseEffect.getDamageAmount(amp, rand));
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

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void dialabs$allowXpHarvesting(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.getSource() instanceof LivingEntity livingEntity) {
            if (EnchantHelper.hasEnchantment(InitEnchants.HARVESTER, livingEntity.getStackInHand(Hand.MAIN_HAND)) ||
                    EnchantHelper.hasEnchantment(InitEnchants.HARVESTER, livingEntity.getStackInHand(Hand.OFF_HAND))) {
                if (this.shouldDropXp()) {
                    int amount = this.getXpToDrop();
                    new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), amount);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void dialabs$healSoulAspect(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.getSource() instanceof LivingEntity user) {
            LivingEntity target = (LivingEntity) (Object) this;
            ItemStack stack = user.getStackInHand(Hand.MAIN_HAND);
            if (EnchantHelper.hasEnchantment(InitEnchants.SOUL_ASPECT, stack)) {
                int lvl = EnchantHelper.getEnchantmentLevel(stack, InitEnchants.SOUL_ASPECT);
                int targetMaxHealth = (int) target.getMaxHealth();
                int gain = (targetMaxHealth / 100) * (lvl * 10);
                user.setHealth(user.getHealth() + gain);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "modifyAppliedDamage")
    private void dialabs$retributionalDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (source.getSource() instanceof LivingEntity target) {
            storeRetributionDmgIfPossible(target, amount);
        }

        if (source.isProjectile()) {
            if (((ProjectileEntity)source.getSource()).getOwner() instanceof LivingEntity target) {
                storeRetributionDmgIfPossible(target, amount);
            }
        }
    }

    private static void storeRetributionDmgIfPossible(LivingEntity target, float damageDealt) {
        if (target.hasStatusEffect(InitEffects.RETRIBUTION)) {
            int lvl = target.getStatusEffect(InitEffects.RETRIBUTION).getAmplifier() + 1;
            DialabsCCA.RetributionalDamageManager.addDmg(target, damageDealt * (0.5 + (lvl / 10d)));
        }
    }

}