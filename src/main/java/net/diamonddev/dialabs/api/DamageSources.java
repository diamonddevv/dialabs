package net.diamonddev.dialabs.api;

import net.minecraft.entity.damage.DamageSource;

public class DamageSources extends DamageSource {

    protected DamageSources(String name) {
        super(name);
    }


    public static final DamageSource CRYSTAL_SHARDS = (new DamageSources("crystal_shards")).setUsesMagic();
    public static final DamageSource CHARGE = (new DamageSources("charge").setBypassesArmor());



}
