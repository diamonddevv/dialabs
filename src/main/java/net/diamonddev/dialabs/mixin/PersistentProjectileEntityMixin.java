package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.cca.DialabsCCA;
import net.diamonddev.dialabs.registry.InitEffects;
import net.diamonddev.dialabs.registry.InitGamerules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void dialabs$onRetributiveHit(LivingEntity target, CallbackInfo ci) {
        if (DialabsCCA.RetributiveArrowManager.isRetributive((PersistentProjectileEntity)(Object)this)) {
            target.addStatusEffect(new StatusEffectInstance(
                    InitEffects.RETRIBUTION,
                    target.world.getGameRules().getInt(InitGamerules.RETRIBUTION_LENGTH),
                    target.world.getGameRules().getInt(InitGamerules.RETRIBUTION_STRENGTH),
                    false, true, true
            ));
        }
    }
}