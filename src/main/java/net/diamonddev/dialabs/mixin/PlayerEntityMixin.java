package net.diamonddev.dialabs.mixin;

import com.mojang.authlib.GameProfile;
import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.manager.ItemContinuityManager;
import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public ItemContinuityManager itemContinuityManager; // Continutity Manager for all Players!!!!
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

    @Inject(method = "<init>", at = @At("TAIL"))
    public void dialabs$injectConstructor(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
        this.itemContinuityManager = new ItemContinuityManager((PlayerEntity)(Object) this);
    }

    int maxContiniuityTicks = 200;

    @Inject(method = "damageShield", at = @At("HEAD"))
    private void dialabs$shieldContinuity(float amount, CallbackInfo ci) {
        if (Dialabs.isFunkyDevFeaturesOn()) {
            ItemContinuityManager.ItemContinuityEntryManager entry =
                    itemContinuityManager.hasEntry(Items.SHIELD) ? itemContinuityManager.getEntry(Items.SHIELD) : itemContinuityManager.createEntry(Items.SHIELD, maxContiniuityTicks);

            entry.setContinuity((int) (entry.getContinuity() + (amount * 10)));

            if (entry.getContinuity() >= maxContiniuityTicks) {
                entry.startCooldown();
            }
        }
    }

}
