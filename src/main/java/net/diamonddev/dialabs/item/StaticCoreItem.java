package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static net.diamonddev.dialabs.api.DiaLabsGamerules.STATIC_CORE_LENGTH;
import static net.diamonddev.dialabs.api.DiaLabsGamerules.STATIC_CORE_STRENGTH;

public class StaticCoreItem extends Item {
    public StaticCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        int effectStrength = world.getGameRules().getInt(STATIC_CORE_STRENGTH);
        int durationInSec = world.getGameRules().getInt(STATIC_CORE_LENGTH);

        player.addStatusEffect(new StatusEffectInstance(InitEffects.CHARGE, durationInSec * 20, effectStrength - 1));
        player.getStackInHand(hand).decrement(1);

        return TypedActionResult.success(player.getStackInHand(hand));

    }
}
