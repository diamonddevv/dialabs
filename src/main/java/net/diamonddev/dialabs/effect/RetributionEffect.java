package net.diamonddev.dialabs.effect;

import net.diamonddev.dialabs.cca.DialabsCCA;
import net.diamonddev.dialabs.registry.InitDamageTypeKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class RetributionEffect extends StatusEffect {
    public RetributionEffect() {
        super(StatusEffectType.HARMFUL, 0x520e05);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);

        // Apply Retibutional Damage
        double attributedDamage = DialabsCCA.RetributionalDamageManager.getDmg(entity);
        entity.damage(InitDamageTypeKeys.get(entity, InitDamageTypeKeys.RETRIBUTION, null, null), (float) attributedDamage);
        DialabsCCA.RetributionalDamageManager.resetDmg(entity); // Reset Retributional Damage
    }
}