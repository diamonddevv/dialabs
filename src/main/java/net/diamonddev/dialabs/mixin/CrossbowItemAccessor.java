package net.diamonddev.dialabs.mixin;

import net.minecraft.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CrossbowItem.class)
public interface CrossbowItemAccessor {
    @Accessor("loaded")
    boolean accessLoadedState();

    @Accessor("loaded")
    void setAccessedLoadedState(boolean loaded);
}
