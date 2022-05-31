package net.diamonddev.dialabs.effect;

import net.diamonddev.dialabs.api.DamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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
    }

    public static final float chargedStatusEffectAdditionalDamageBase = 0.1F;

    public static float calculateAdditionalDamage(float remainingHealth, float amplifier, float durationInTicks, float base, boolean hasChargedEffect) {

        if (hasChargedEffect) {
            return 0;
        } else {
            float value = base * ((durationInTicks / 20) * amplifier);
            if (value <= 0) {
                return 0;
            } else {
                if (value > remainingHealth) {
                    return value - remainingHealth;
                } else {
                    return value;
                }
            }
        }

        // 5s remain, amp 1, attacked w/out charged = 0.5dmg attack bonus
        // 60s remain, amp 1, attacked w/out charged = 6.0dmg attack bonus
        // 60s remain, amp 1, attacked w/ charged = 0 dmg attack bonus
    }

    public static boolean inflictDamageWithChargeBonus(LivingEntity target, DamageSource source, float chargeBonus) {

        if (target != null) {
            target.damage(source, chargeBonus);
            return true;
        } else {
            return false;
        }
    }

}
