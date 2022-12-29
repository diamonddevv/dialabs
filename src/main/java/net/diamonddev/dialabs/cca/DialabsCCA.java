package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.cca.entity.BooleanComponent;
import net.diamonddev.dialabs.cca.entity.DoubleComponent;
import net.diamonddev.dialabs.cca.item.ItemIntComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DialabsCCA implements EntityComponentInitializer, ItemComponentInitializer {

    // ENTITY COMPONENTS
    public static final ComponentKey<DoubleComponent> RETRIBUTIONAL_DAMAGE =
            ComponentRegistryV3.INSTANCE.getOrCreate(DiaLabs.id.build("retributional_damage"), DoubleComponent.class);

    public static final ComponentKey<BooleanComponent> RETRIBUTIVE_ARROW =
            ComponentRegistryV3.INSTANCE.getOrCreate(DiaLabs.id.build("retributive"), BooleanComponent.class);


    // ITEM COMPONENTS
    public static final ComponentKey<ItemIntComponent> MULTICLIP_PROJECTILES =
            ComponentRegistry.getOrCreate(DiaLabs.id.build("multiclip_projectiles"), ItemIntComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, RETRIBUTIONAL_DAMAGE, livingEntity -> new DoubleComponent("retributionalDamage"));
        registry.registerFor(PersistentProjectileEntity.class, RETRIBUTIVE_ARROW, persProj -> new BooleanComponent("isRetributive", false));
    }

    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(Items.CROSSBOW, MULTICLIP_PROJECTILES, stack -> new ItemIntComponent("projectileCount", stack));
    }

    public static class RetributionalDamageManager {
        public static double getDmg(LivingEntity target) {
            return RETRIBUTIONAL_DAMAGE.get(target).getValue();
        }

        public static void resetDmg(LivingEntity target) {
            RETRIBUTIONAL_DAMAGE.get(target).setValue(0d);
        }

        public static void addDmg(LivingEntity target, double additive) {
            RETRIBUTIONAL_DAMAGE.get(target).setValue(RETRIBUTIONAL_DAMAGE.get(target).getValue() + additive);
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

    public static class MulticlipProjectileManager {
        public static int getProjectiles(ItemStack stack) {
            return MULTICLIP_PROJECTILES.get(stack).getValue();
        }

        public static void setProjectiles(ItemStack stack, int count) {
            MULTICLIP_PROJECTILES.get(stack).setValue(count);
        }

        public static void decrementProjectileCount(ItemStack stack) {
            setProjectiles(stack, getProjectiles(stack) - 1);
        }
    }
}
