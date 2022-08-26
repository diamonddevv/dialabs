package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.effect.ChargeEffect;
import net.diamonddev.dialabs.effect.CrystalliseEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class InitEffects {

    public static final StatusEffect CRYSTALLISE = new CrystalliseEffect();
    public static final StatusEffect CHARGE = new ChargeEffect();



    public static void initializeEffects() {

        Registry.register(Registry.STATUS_EFFECT, new Identifier("crystallising"), CRYSTALLISE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("charged"), CHARGE);




    }
}
