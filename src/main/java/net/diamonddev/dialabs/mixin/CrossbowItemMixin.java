package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.cca.DialabsCCA;
import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void dialabs$preventFireworksLoadingInRetributiveCrossbows(
            World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (EnchantHelper.hasEnchantment(InitEnchants.RETRIBUTIVE, user.getStackInHand(hand))) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (user.getArrowType(itemStack).getItem() instanceof FireworkRocketItem) {
                cir.setReturnValue(TypedActionResult.fail(itemStack));
            }
        }
    }

    @Inject(method = "createArrow", at = @At("HEAD"), cancellable = true)
    private static void dialabs$createRetributiveCrossbowArrow(World world, LivingEntity entity, ItemStack crossbow,
                                                               ItemStack arrow, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
        if (EnchantHelper.hasEnchantment(InitEnchants.RETRIBUTIVE, crossbow)) {
            ArrowItem arrowItem = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
            PersistentProjectileEntity persProj = arrowItem.createArrow(world, arrow, entity);

            // Boilerplate
            persProj.setShotFromCrossbow(true);
            persProj.setSound(SoundEvents.ITEM_CROSSBOW_HIT);

            // Actual Retribution Info
            persProj.setDamage(persProj.getDamage() * 0.5);
            persProj.setVelocity(persProj.getVelocity().multiply(0.25));
            DialabsCCA.RetributiveArrowManager.setRetributive(persProj, true);

            // Return
            cir.setReturnValue(persProj);
        }
    }
}
