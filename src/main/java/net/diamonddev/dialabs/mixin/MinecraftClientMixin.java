package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {


    @Shadow @Final public InGameHud inGameHud;

    @Shadow public abstract Window getWindow();

    @Inject(method = "tick", at = @At("HEAD"))
    private void dialabs$clientTickSnipingSpyglassCheck(CallbackInfo ci) {
        ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
        if (clientPlayer != null) {
            if (EnchantHelper.hasEnchantment(InitEnchants.SNIPING, clientPlayer.getStackInHand(Hand.MAIN_HAND))) {
                if (clientPlayer.getStackInHand(Hand.OFF_HAND).getItem() == Items.SPYGLASS) {
                    ((InGameHudInvoker)MinecraftClient.getInstance().inGameHud).invokeRenderSpyglassOverlay(1f);
                    MinecraftClient.getInstance().gameRenderer.renderWithZoom(2f, getWindow().getWidth() / 2f, getWindow().getHeight() / 2f);
                }
            }
        }
    }
}
