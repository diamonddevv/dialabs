package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.cca.DialabsCCA;
import net.diamonddev.dialabs.nbt.DialabsNBT;
import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Shadow
    public static void setCharged(ItemStack stack, boolean charged) {
    }

    @Shadow
    private static void clearProjectiles(ItemStack crossbow) {
    }

    @Shadow
    protected static void putProjectile(ItemStack crossbow, ItemStack projectile) {
    }

    @Shadow
    protected static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
        return null;
    }

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
            DialabsCCA.RetributiveArrowManager.setRetributive(persProj, true);

            // Return
            cir.setReturnValue(persProj);
        }
    }

    @Inject(method = "createArrow", at = @At("HEAD"), cancellable = true)
    private static void dialabs$createSnipingCrossbowArrow(World world, LivingEntity entity, ItemStack crossbow,
                                                               ItemStack arrow, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
        if (EnchantHelper.hasEnchantment(InitEnchants.SNIPING, crossbow)) {
            ArrowItem arrowItem = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
            PersistentProjectileEntity persProj = arrowItem.createArrow(world, arrow, entity);

            // Boilerplate
            persProj.setShotFromCrossbow(true);
            persProj.setSound(SoundEvents.ITEM_CROSSBOW_HIT);

            // Actual Sniping Info
            DialabsCCA.SnipingArrowManager.setIs(persProj, true);
            DialabsCCA.SnipingArrowManager.set(persProj, entity.getPos());
            DialabsCCA.SnipingArrowManager.setSpeedReference(persProj, (EnchantHelper.getEnchantmentLevel(crossbow, InitEnchants.SNIPING) * 0.5));

            // Return
            cir.setReturnValue(persProj);
        }
    }


    @Redirect(
            method = "shoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileEntity;setVelocity(DDDFF)V"
            )
    )
    private static void dialabs$setSnipingSpeed(ProjectileEntity projEntity, double x, double y, double z, float speed, float divergence) {
        if (!(projEntity instanceof FireworkRocketEntity)) {
            speed *= 1 + (DialabsCCA.SnipingArrowManager.getSpeed((PersistentProjectileEntity) projEntity));
        }
        projEntity.setVelocity(x, y, z, speed, divergence);
    }



    @Inject(
            method = "loadProjectile",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void dialabs$loadMulticlipProjectiles(
            LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir
    ) {
        if (EnchantHelper.hasEnchantment(InitEnchants.MULTICLIP, crossbow)) { // I cloned the method, its easier

            int loadable;

            if (projectile.isEmpty()) {
                cir.setReturnValue(false);
            } else {
                boolean bl = creative && projectile.getItem() instanceof ArrowItem;
                ItemStack itemStack;
                if (!bl && !creative && !simulated) {
                    itemStack = projectile.split(EnchantmentHelper.getLevel(InitEnchants.MULTICLIP, crossbow) + 1);
                    loadable = itemStack.getCount();
                    if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                        ((PlayerEntity)shooter).getInventory().removeOne(projectile);
                    }
                } else {
                    itemStack = projectile.copy();
                    loadable = EnchantmentHelper.getLevel(InitEnchants.MULTICLIP, crossbow) + 1;
                }

                DialabsNBT.MulticlipProjectileManager.setProjectiles(crossbow, loadable);
                putProjectile(crossbow, itemStack);
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getPullTime", at = @At("HEAD"), cancellable = true)
    private static void dialabs$getMulticlipPullTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (EnchantHelper.hasEnchantment(InitEnchants.MULTICLIP, stack)) {
            int multiclip = EnchantmentHelper.getLevel(InitEnchants.MULTICLIP, stack);
            int quickcharge = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);

            int modifier = multiclip - quickcharge;

            cir.setReturnValue(25 + (5 * modifier));
        }
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void dialabs$appendCrossbowTooltips(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (EnchantHelper.hasEnchantment(InitEnchants.MULTICLIP, stack)) {
            tooltip.add(Text.translatable("text.dialabs.multiclip_loaded_count", DialabsNBT.MulticlipProjectileManager.getProjectiles(stack)));
        }
    }

    @Inject(
            method = "postShoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/CrossbowItem;clearProjectiles(Lnet/minecraft/item/ItemStack;)V"
            ),
            cancellable = true)
    private static void dialabs$decrementMulticlipLoadedProjectileCountBeforeClearing(World world, LivingEntity entity, ItemStack stack, CallbackInfo ci) {
        if (EnchantHelper.hasEnchantment(InitEnchants.MULTICLIP, stack)) { // if stack has multiclip
            if (DialabsNBT.MulticlipProjectileManager.getProjectiles(stack) > 0) { // if arrow count is greater than 0
                DialabsNBT.MulticlipProjectileManager.decrementProjectileCount(stack); // decrement count
                ci.cancel(); // cancel
            }
        }
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/TypedActionResult;consume(Ljava/lang/Object;)Lnet/minecraft/util/TypedActionResult;",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void dialabs$preventMulticlipFromBecomingMultishot(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = user.getStackInHand(hand);
        if (EnchantHelper.hasEnchantment(InitEnchants.MULTICLIP, stack)) {
            if (DialabsNBT.MulticlipProjectileManager.getProjectiles(stack) > 0) {
                setCharged(stack, true);
                ((CrossbowItemAccessor) stack.getItem()).setAccessedLoadedState(true);
                cir.setReturnValue(TypedActionResult.consume(stack));
            } else {
                clearProjectiles(stack);
            }
        }
    }
}
