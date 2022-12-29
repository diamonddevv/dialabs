package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.cca.DialabsCCA;
import net.diamonddev.dialabs.registry.InitEnchants;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
            persProj.setDamage(persProj.getDamage() * (5 * EnchantmentHelper.getLevel(InitEnchants.SNIPING, crossbow)));

            // Return
            cir.setReturnValue(persProj);
        }
    }

    @Inject(method = "shoot", at = @At("HEAD"), cancellable = true)
    private static void dialabs$setSnipingSpeed(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
        if (EnchantHelper.hasEnchantment(InitEnchants.SNIPING, crossbow)) { // cloned edited method
            if (!world.isClient) {
                boolean bl = projectile.isOf(Items.FIREWORK_ROCKET);
                ProjectileEntity projectileEntity;
                if (bl) {
                    projectileEntity = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448, shooter.getZ(), true);
                } else {
                    projectileEntity = createArrow(world, shooter, crossbow, projectile);
                    if (creative || simulated != 0.0F) {
                        ((PersistentProjectileEntity)projectileEntity).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    }
                }

                if (shooter instanceof CrossbowUser) {
                    CrossbowUser crossbowUser = (CrossbowUser)shooter;
                    crossbowUser.shoot(crossbowUser.getTarget(), crossbow, projectileEntity, simulated);
                } else {
                    Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
                    Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(simulated * 0.017453292F), vec3d.x, vec3d.y, vec3d.z);
                    Vec3d vec3d2 = shooter.getRotationVec(1.0F);
                    Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);
                    projectileEntity.setVelocity(vector3f.x(), vector3f.y(), vector3f.z(), speed * (EnchantmentHelper.getLevel(InitEnchants.SNIPING, crossbow) * 2), divergence);
                }

                crossbow.damage(bl ? 3 : 1, shooter, (e) -> {
                    e.sendToolBreakStatus(hand);
                });
                world.spawnEntity(projectileEntity);
                world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);

                ci.cancel();
            }
        }
    }



    @Inject(
            method = "loadProjectile",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
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

                DialabsCCA.MulticlipProjectileManager.setProjectiles(crossbow, loadable);
                putProjectile(crossbow, itemStack);
                cir.setReturnValue(true);
            }
        }
    }



    @Inject(method = "loadProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private static void dialabs$loadWhatYouCanGiveNothingBack(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir) {

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
            tooltip.add(Text.translatable("text.dialabs.multiclip_loaded_count", DialabsCCA.MulticlipProjectileManager.getProjectiles(stack)));
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
            if (DialabsCCA.MulticlipProjectileManager.getProjectiles(stack) > 0) { // if arrow count is greater than 0
                DialabsCCA.MulticlipProjectileManager.decrementProjectileCount(stack); // decrement count
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
            if (DialabsCCA.MulticlipProjectileManager.getProjectiles(stack) > 0) {
                setCharged(stack, true);
                ((CrossbowItemAccessor) stack.getItem()).setAccessedLoadedState(true);
                cir.setReturnValue(TypedActionResult.consume(stack));
            } else {
                clearProjectiles(stack);
            }
        }
    }
}
