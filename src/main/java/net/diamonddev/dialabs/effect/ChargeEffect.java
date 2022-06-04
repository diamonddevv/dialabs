package net.diamonddev.dialabs.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChargeEffect extends StatusEffect {
    public ChargeEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe6dc27);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (hasBeenStruck) {
            if (getStrikeType() == LightningStrikeType.NATURAL)



        }

    }

    public static float chargedStatusEffectAdditionalDamageBase = 0.1F;
    public static float sentDamage;
    public static int previousPotencyChanges;
    public static boolean hasBeenStruck;
    public static LightningStrikeType strikeType = LightningStrikeType.UNSTRUCK;

    public static float calculateDamage(float previousmeleeDamage, float amplifier, float durationInTicks, float base, boolean hasChargedEffect) {

        if (hasChargedEffect) {
            return 0;
        } else {
            return (base * ((durationInTicks / 20) * amplifier)) + previousmeleeDamage;
        }

        // 5s remain, amp 1, attacked w/out charged = 0.5dmg attack bonus
        // 60s remain, amp 1, attacked w/out charged = 6.0dmg attack bonus
        // 60s remain, amp 1, attacked w/ charged = 0 dmg attack bonus
    }

    public static float getSentMeleeDamage() {
        return sentDamage;
    }

    public static void sendMeleeDamage(float damage) {
        sentDamage = damage;
    }

    public static void changeEffectPotency(int changeBy, int untruePotency) {
        if (previousPotencyChanges < 5) {
            int tp = (untruePotency + 1);

            int newPotency = tp + changeBy;
        }
    }

    public static void sendEntityStruck(LightningStrikeType lightningStrikeType) {
        hasBeenStruck = true;
        strikeType = lightningStrikeType;
    }

    public static boolean getHasBeenStruck() {
        return hasBeenStruck;
    }

    public static LightningStrikeType getStrikeType() {
        return strikeType;
    }

    public enum LightningStrikeType {
        UNSTRUCK,
        NATURAL,
        TRIDENT,
        LIGHTNING_BOTTLE
    }
}
