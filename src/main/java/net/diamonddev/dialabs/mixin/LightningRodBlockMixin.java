package net.diamonddev.dialabs.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {

    @Inject(method = "onProjectileHit", at = @At("HEAD"), cancellable = true)
    private void dialabs$channelingTwoOnLightningRod(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
        if (projectile instanceof TridentEntity &&
                EnchantmentHelper.getLevel(Enchantments.CHANNELING, ((TridentEntityAccessor)projectile).accessTridentStack()) > 1) {

            BlockPos blockPos = hit.getBlockPos();
            if (world.isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                if (lightningEntity != null) {
                    lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos.up()));
                    Entity entity = projectile.getOwner();
                    lightningEntity.setChanneler(entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null);
                    world.spawnEntity(lightningEntity);
                }

                world.playSound(null, blockPos, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0F, 1.0F);
                ci.cancel(); // cancel inject
            }

        }
    }
}
