package net.diamonddev.dialabs.mixin;

import net.minecraft.enchantment.ChannelingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChannelingEnchantment.class)
public class ChannelingEnchantmentMixin {
    @Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
    private void dialabs$increaseChannelingMaxLevel(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(2); // literally just increase it to two
    }
}
