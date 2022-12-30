package net.diamonddev.dialabs.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InGameHud.class)
public interface InGameHudInvoker {
    @Invoker("renderSpyglassOverlay")
    void invokeRenderSpyglassOverlay(float scale);
}
