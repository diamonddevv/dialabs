package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DamageTracker.class)
public abstract class DamageTrackerMixin {

    @Shadow @Final private List<DamageRecord> recentDamage;

    @Shadow @Nullable public abstract DamageRecord getMostRecentDamage();

    @Shadow @Final private LivingEntity entity;

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void dialabs$checkIsStatic(CallbackInfoReturnable<Text> cir) {

        List<DamageRecord> list = this.recentDamage;

        if (!list.isEmpty()) {
            if (this.getMostRecentDamage().getAttacker() instanceof LivingEntity attacker) {
                if (attacker.hasStatusEffect(InitEffects.CHARGE) && !this.entity.hasStatusEffect(InitEffects.CHARGE)) {
                    cir.setReturnValue(Text.translatable("death.attack.charge.player", this.entity.getName(), attacker.getName()));
                }
            }
        }
    }
}
