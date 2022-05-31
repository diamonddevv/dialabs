package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.init.InitBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity {


    @Shadow private int ambientTick;

    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(at=@At("TAIL"), method="tick")
    public void tick(CallbackInfo ci) {
        if(ambientTick >= 0) {
            if(!world.isClient) {
                BlockPos struckBlock = getBlockPos().down();
                if(world.getBlockState(struckBlock).getBlock().equals(Blocks.IRON_BLOCK)) {
                    world.setBlockState(struckBlock, InitBlocks.SHOCKED_IRON_BLOCK.getDefaultState());
                }
            }
        }
    }
}
