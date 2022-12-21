package net.diamonddev.dialabs.util;

import net.minecraft.entity.damage.DamageSource;

public class DialabsDamageSource extends DamageSource {

    public DialabsDamageSource(String name) {
        super(name);
    }

    public static final DamageSource CRYSTAL_SHARDS = new DialabsDamageSource("crystal_shards").setUsesMagic();
    public static final DamageSource RETRIBUTION = new DialabsDamageSource("retribution").setUnblockable();
}
