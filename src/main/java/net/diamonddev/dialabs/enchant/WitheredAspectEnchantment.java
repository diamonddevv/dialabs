package net.diamonddev.dialabs.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

import static net.diamonddev.dialabs.util.DiaLabsGamerules.WITHERED_ASPECT_APL;
import static net.diamonddev.dialabs.util.DiaLabsGamerules.WITHERED_ASPECT_SPL;

public class WitheredAspectEnchantment extends Enchantment implements SyntheticEnchantment {

    public WitheredAspectEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinPower(int level) {return 19;}

    @Override
    public boolean isTreasure() {return true;}

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

        World world = user.getWorld();

        int witheredAspectGameruleValue = world.getGameRules().getInt(WITHERED_ASPECT_SPL);
        int witheredAspectSpl = witheredAspectGameruleValue * 20;

        int witheredAspectApl = world.getGameRules().getInt(WITHERED_ASPECT_APL);

        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, level * witheredAspectSpl, level * witheredAspectApl));
        }
    }

    @Override
    public boolean canBeSynthesized() {
        return true;
    }
}
