package net.diamonddev.dialabs.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(
            method = "updateFovMultiplier",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;fovMultiplier:F",
                    ordinal = 6
            ),
            cancellable = true
    )
    private void dialabs$allowFovMultiplierToBeLower(CallbackInfo ci) {
        ci.cancel();
    }
}
