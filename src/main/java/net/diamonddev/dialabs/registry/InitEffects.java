package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.diamonddev.dialabs.effect.RetributionEffect;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInit {

    public static final StatusEffect CRYSTALLISE = new CrystalliseEffect();
    public static final StatusEffect CHARGE = new ChargeEffect();
    public static final StatusEffect RETRIBUTION = new RetributionEffect();

    @Override
    public void init() {
        Registry.register(Registries.STATUS_EFFECT, DiaLabs.id.build("crystallising"), CRYSTALLISE);
        Registry.register(Registries.STATUS_EFFECT, DiaLabs.id.build("charged"), CHARGE);
        Registry.register(Registries.STATUS_EFFECT, DiaLabs.id.build("retribution"), RETRIBUTION);
    }
}
