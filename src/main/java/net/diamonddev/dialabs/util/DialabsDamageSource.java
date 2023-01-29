package net.diamonddev.dialabs.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class DialabsDamageSource {

    public static final DamageSource CRYSTAL_SHARDS = new DamageSource("crystal_shards").setUsesMagic();
    public static final DamageSource RETRIBUTION = new DamageSource("retribution").setUnblockable();


    public static DamageSource thwack(Entity source) {
        return new EntityDamageSource("thwack", source);
    }
    public static DamageSource sparkBomb(Entity source) {
        return new EntityDamageSource("sparkBomb", source).setBypassesArmor().setBypassesProtection();
    }
    public static DamageSource bomb(Entity source) {
        return new EntityDamageSource("bomb", source).setExplosive();
    }
}
