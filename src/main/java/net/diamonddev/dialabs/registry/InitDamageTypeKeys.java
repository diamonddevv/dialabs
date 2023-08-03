package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class InitDamageTypeKeys implements RegistryInitializer {
    public static RegistryKey<DamageType>
            ATTRILLITE_POISON, BOMB, CRYSTAL_SHARDS, RETRIBUTION, SPARK_BOMB, THWACK, FLINTLOCK;


    @Override
    public void register() {
        ATTRILLITE_POISON = create(Dialabs.id("attrillite_poison"));
        BOMB = create(Dialabs.id("bomb"));
        CRYSTAL_SHARDS = create(Dialabs.id("crystal_shards"));
        RETRIBUTION = create(Dialabs.id("retribution"));
        SPARK_BOMB = create(Dialabs.id("spark_bomb"));
        THWACK = create(Dialabs.id("thwack"));
        FLINTLOCK = create(Dialabs.id("flintlock"));
    }

    private static RegistryKey<DamageType> create(Identifier id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id);
    }

    public static DamageSource get(Entity entity, RegistryKey<DamageType> type, @Nullable Entity sourceOrProjectile, @Nullable Entity attacker) {
        if (attacker != null) return entity.getDamageSources().create(type, sourceOrProjectile, attacker);
        if (sourceOrProjectile != null) return entity.getDamageSources().create(type, sourceOrProjectile);
        return entity.getDamageSources().create(type);
    }
}
