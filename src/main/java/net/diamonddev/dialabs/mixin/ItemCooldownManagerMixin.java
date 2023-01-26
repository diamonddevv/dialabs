package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.Dialabs;
import net.minecraft.entity.player.ItemCooldownManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemCooldownManager.class)
public class ItemCooldownManagerMixin {


    @Inject(method = "update", at = @At("HEAD"))
    public void dialabs$experimentalShieldTweak(CallbackInfo ci) {
        if (Dialabs.isDevelopment()) {



        }
    }

}
