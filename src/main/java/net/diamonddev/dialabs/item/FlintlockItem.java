package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.util.Helpers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class FlintlockItem extends RangedWeaponItem {
    public FlintlockItem(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return (stack -> stack.getItem() == Items.IRON_NUGGET);
    }

    @Override
    public int getRange() {
        return 40;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isCreative()) {
            user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 10);
        }

        ItemStack stackInHand = user.getStackInHand(hand);
        ItemStack ammo = this.getAmmo(user);

        if (!world.isClient) {
            if (this.hasGunpowder(user, hand)) {
                if (!ammo.isEmpty()) {
                    world.playSoundFromEntity(null, user, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 2.0f, 1.5f);

                    if (!user.getAbilities().creativeMode) {
                        user.getStackInHand(Helpers.getOppositeHand(hand)).decrement(1);
                        ammo.decrement(1);
                    }

                    return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
                }
            } else {
                world.playSoundFromEntity(null, user, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.NEUTRAL, 2.0f, 1.5f);
                return new TypedActionResult<>(ActionResult.FAIL, stackInHand);
            }
        }

        return new TypedActionResult<>(ActionResult.FAIL, stackInHand);
    }

    private boolean hasGunpowder(PlayerEntity user, Hand hand) {
        return user.getStackInHand(Helpers.getOppositeHand(hand)).getItem() == Items.GUNPOWDER || user.getAbilities().creativeMode;
    }

    private ItemStack getAmmo(PlayerEntity player) {

        for(int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack s = player.getInventory().getStack(i);
            if (this.getProjectiles().test(s)) {
                return s;
            }
        }

        return player.getAbilities().creativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
    }

}
