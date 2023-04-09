package net.diamonddev.dialabs.mixin;


import net.diamonddev.dialabs.registry.InitResourceListener;
import net.diamonddev.dialabs.resource.InitDataResourceTypes;
import net.diamonddev.dialabs.resource.recipe.StrikingRecipe;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
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
    public void dialabs$tickLightningRecipes(CallbackInfo ci) {
        if(ambientTick >= 0) {
            if(!world.isClient) {
                BlockPos struckBlock = getBlockPos().down();
                if (world.getBlockState(struckBlock).getBlock().equals(Blocks.LIGHTNING_ROD)) {
                    struckBlock = struckBlock.down();
                }

                BlockPos finalStruckBlock = struckBlock;
                CognitionResourceManager.forEachResource(InitResourceListener.DIALABS_RECIPES, InitDataResourceTypes.STRIKING, recipe -> {
                    Identifier ogBlock = recipe.getIdentifier(StrikingRecipe.ORIGINAL_BLOCK_KEY);
                    Identifier newBlock = recipe.getIdentifier(StrikingRecipe.NEW_BLOCK_KEY);

                    if (Registries.BLOCK.getId(world.getBlockState(finalStruckBlock).getBlock()).equals(ogBlock)) {
                        Block block = Registries.BLOCK.get(newBlock);
                        world.setBlockState(finalStruckBlock, block.getDefaultState());
                    }
                });
            }
        }
    }
}
