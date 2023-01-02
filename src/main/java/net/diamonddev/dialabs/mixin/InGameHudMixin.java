package net.diamonddev.dialabs.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.diamonddev.dialabs.DiaLabs;
import net.diamonddev.dialabs.registry.InitEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper { // i stole this idea from pick your poison ok (https://github.com/Ladysnake/Pick-Your-Poison)


    private static final Identifier RETRIBUTION_HEARTS = DiaLabs.id.build("textures/gui/retributional_hearts.png");

    private static final Identifier CHARGED_HEARTS = DiaLabs.id.build("textures/gui/charged_hearts.png");


    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void dialabs$drawHearts(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (!blinking && type == InGameHud.HeartType.NORMAL && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player &&
                (player.hasStatusEffect(InitEffects.RETRIBUTION) ||
                player.hasStatusEffect(InitEffects.CHARGE))
        ) {

            if (player.hasStatusEffect(InitEffects.RETRIBUTION)) {
                RenderSystem.setShaderTexture(0, RETRIBUTION_HEARTS);
            }

            if (player.hasStatusEffect(InitEffects.CHARGE)) {
                RenderSystem.setShaderTexture(0, CHARGED_HEARTS);
            }

            drawTexture(matrices, x, y, halfHeart ? 9 : 0, v, 9, 9);
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
            ci.cancel();
        }
    }
}
