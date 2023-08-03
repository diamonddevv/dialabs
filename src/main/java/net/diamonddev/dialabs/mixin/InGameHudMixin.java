package net.diamonddev.dialabs.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.registry.InitEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(InGameHud.class)
public class InGameHudMixin { // i stole this idea from pick your poison ok (https://github.com/Ladysnake/Pick-Your-Poison)


    @Shadow @Final private MinecraftClient client;
    private static final Identifier RETRIBUTION_HEARTS = Dialabs.id("textures/gui/retributional_hearts.png");
    private static final Identifier CHARGED_HEARTS = Dialabs.id("textures/gui/charged_hearts.png");
    private static final Identifier CRYSTAL_HEARTS = Dialabs.id("textures/gui/crystal_hearts.png");
    private static final Identifier ATTRILLITE_POISON_HEARTS = Dialabs.id("textures/gui/attrillite_poison_hearts.png");


    @WrapOperation(
            method = "drawHeart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
            )
    )
    private void dialabs$replaceHeartTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        Identifier tex = texture;

        if (this.client.cameraEntity instanceof PlayerEntity player) {

            if (player.hasStatusEffect(InitEffects.RETRIBUTION)) tex = RETRIBUTION_HEARTS;
            if (player.hasStatusEffect(InitEffects.CHARGE)) tex = CHARGED_HEARTS;
            if (player.hasStatusEffect(InitEffects.CRYSTALLISE)) tex = CRYSTAL_HEARTS;
            if (player.hasStatusEffect(InitEffects.ATTRILLITE_POISON)) tex = ATTRILLITE_POISON_HEARTS;

        }

        original.call(graphics, tex, x, y, u, v, width, height);
    }
}
