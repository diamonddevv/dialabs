package net.diamonddev.dialabs.mixin;

import net.minecraft.entity.player.ItemCooldownManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemCooldownManager.Entry.class)
public interface ItemCooldownManagerEntryInvoker {

    @Invoker("<init>")
    static ItemCooldownManager.Entry invokeInstantiate(int startTick, int endTick) {
        throw new RuntimeException("bonk! something went wrong.");
    }
}
