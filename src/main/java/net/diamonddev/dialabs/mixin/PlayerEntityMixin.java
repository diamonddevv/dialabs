package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isUsingSpyglass", at = @At("HEAD"), cancellable = true)
    private void dialabs$forceSpyglassUsage(CallbackInfoReturnable<Boolean> cir) {
        if (EnchantHelper.hasEnchantment(InitEnchants.SNIPING, this.getStackInHand(Hand.MAIN_HAND))) {
            if (CrossbowItem.isCharged(this.getStackInHand(Hand.MAIN_HAND))) {
                if (this.getStackInHand(Hand.OFF_HAND).getItem() == Items.SPYGLASS) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
