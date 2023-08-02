package net.diamonddev.dialabs.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

import java.util.Random;

public class CrystalliseEffect extends StatusEffect {
    public CrystalliseEffect() {
        super(StatusEffectType.BENEFICIAL, 0x2f71a1);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    }

    public static boolean shouldDamageAttacker(int amplifier, Random random) {
        if (amplifier <= 0) {
            return false;
        } else {
            return random.nextFloat() < 0.30F * (float)amplifier;
        }
    }

    public static int getDamageAmount(int amplifier, Random random) {
        return amplifier > 10 ? amplifier - 10 : 1 + random.nextInt(4);
    }

}
