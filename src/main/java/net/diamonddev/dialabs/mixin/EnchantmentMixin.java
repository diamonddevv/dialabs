package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = "isAvailableForEnchantedBookOffer", at = @At("HEAD"), cancellable = true)
    private void dialabs$preventSyntheticSale(CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof SyntheticEnchantment s) {
            if (!s.shouldBookBeTradable()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "isTreasure", at = @At("HEAD"), cancellable = true)
    private void dialabs$preventEnchantmentTableSynthetic(CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof SyntheticEnchantment s) {
            if (!s.shouldBookBeLootable()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "isAvailableForRandomSelection", at = @At("HEAD"), cancellable = true)
    private void dialabs$preventSyntheticSelection(CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof SyntheticEnchantment s) {
            if (!s.shouldBookBeRandomlySelectable()) {
                cir.setReturnValue(true);
            }
        }
    }
}
