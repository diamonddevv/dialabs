package net.diamonddev.dialabs.mixin;

import com.mojang.authlib.GameProfile;
import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    float level = 0.018f;
    /*
        Here's where this number comes from, since you're looking through this code, and you clearly want to know what this mixin does.

        The FOV multiplier at Zooming 5 should be 0.01, so from 0.1 thats a reduction of 0.09.
        There are 5 levels, so to get our level-multiplier we divide 0.09 by 5.
        This gives us 0.018.
     */
    @Inject(method = "getFovMultiplier", at = @At("HEAD"), cancellable = true)
    private void dialabs$setFOVWhenUsingSpyglassEarly(CallbackInfoReturnable<Float> cir) {
        if (this.isUsingSpyglass() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {

            float zoom = 0.1f;

            ItemStack stack = this.getStackInHand(this.getActiveHand());
            if (EnchantHelper.hasEnchantment(InitEnchants.ZOOMING, stack)) { // Zooming Enchantment
                float inc = EnchantHelper.getEnchantmentLevel(stack, InitEnchants.ZOOMING) * level;
                zoom -= inc;
            }


            cir.setReturnValue(zoom);
        }
    }
}
