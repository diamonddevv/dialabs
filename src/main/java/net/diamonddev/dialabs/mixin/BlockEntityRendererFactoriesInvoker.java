package net.diamonddev.dialabs.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockEntityRendererFactories.class)
public interface BlockEntityRendererFactoriesInvoker {

    @Invoker("register")
    static <T extends BlockEntity> void invokeRegister(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory) {
        throw new RuntimeException("I USED TO RULEEEEEEEE THE WORLD, BUT SOMEHOW THIS CODE IS BROKEN AS FU-");
    }
}
