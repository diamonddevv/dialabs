package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.effect.AttrillitePoisonEffect;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.effect.RetributionEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInitializer {

    public static final StatusEffect CRYSTALLISE = new CrystalliseEffect();
    public static final StatusEffect CHARGE = new ChargeEffect();
    public static final StatusEffect RETRIBUTION = new RetributionEffect();
    public static final StatusEffect ATTRILLITE_POISON = new AttrillitePoisonEffect();

    @Override
    public void register() {
        Registry.register(Registries.STATUS_EFFECT, Dialabs.id("crystallising"), CRYSTALLISE);
        Registry.register(Registries.STATUS_EFFECT, Dialabs.id("charged"), CHARGE);
        Registry.register(Registries.STATUS_EFFECT, Dialabs.id("retribution"), RETRIBUTION);
        Registry.register(Registries.STATUS_EFFECT, Dialabs.id("attrillite_poison"), ATTRILLITE_POISON);
    }
}
