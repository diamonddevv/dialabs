package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.world.explosion.BombExplosion;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExplosionMixin {

    @Shadow @Final private ExplosionBehavior behavior;

    @Inject(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"), cancellable = true)
    private void dialabs$preventDamageWithDynamicExplosionBehavior(CallbackInfo ci) {
        if (this.behavior instanceof BombExplosion.DynamicExplosionBehavior dynamic) {
            if (!dynamic.getDamagesEntities()) {
                ci.cancel();
            }
        }
    }
}
