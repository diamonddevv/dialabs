package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.diamonddev.dialabs.DiaLabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

public class DialabsCCA implements EntityComponentInitializer {

    public static final ComponentKey<DoubleComponent> RETRIBUTIONAL_DAMAGE =
            ComponentRegistryV3.INSTANCE.getOrCreate(DiaLabs.id.build("retributional_damage"), DoubleComponent.class);

    public static final ComponentKey<BooleanComponent> RETRIBUTIVE_ARROW =
            ComponentRegistryV3.INSTANCE.getOrCreate(DiaLabs.id.build("retributive"), BooleanComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, RETRIBUTIONAL_DAMAGE, livingEntity -> new DoubleComponent("retributionalDamage"));
        registry.registerFor(PersistentProjectileEntity.class, RETRIBUTIVE_ARROW, persProj -> new BooleanComponent("isRetributive", false));
    }

    public static class RetributionalDamageManager {
        public static double getDmg(LivingEntity target) {
            return RETRIBUTIONAL_DAMAGE.get(target).getNum();
        }

        public static void resetDmg(LivingEntity target) {
            RETRIBUTIONAL_DAMAGE.get(target).setNum(0d);
        }

        public static void addDmg(LivingEntity target, double additive) {
            RETRIBUTIONAL_DAMAGE.get(target).setNum(RETRIBUTIONAL_DAMAGE.get(target).getNum() + additive);
        }

    }
    public static class RetributiveArrowManager {
        public static boolean isRetributive(PersistentProjectileEntity target) {
            return RETRIBUTIVE_ARROW.get(target).isComponentTrue();
        }

        public static void setRetributive(PersistentProjectileEntity target, boolean set) {
            RETRIBUTIVE_ARROW.get(target).setComponent(set);
        }
    }
}
