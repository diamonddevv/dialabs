package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.registry.InitEffects;
import net.diamonddev.dialabs.registry.InitSoundEvent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

import static net.diamonddev.dialabs.registry.InitGamerules.STATIC_CORE_LENGTH;
import static net.diamonddev.dialabs.registry.InitGamerules.STATIC_CORE_STRENGTH;

public class StaticCoreItem extends Item {
    public StaticCoreItem(Settings settings) {
        super(settings);
    }

    private static final Random random = new Random();

    private static final int COOLDOWN = 20;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        int effectStrength = world.getGameRules().getInt(STATIC_CORE_STRENGTH);
        int durationInSec = world.getGameRules().getInt(STATIC_CORE_LENGTH);

        player.addStatusEffect(new StatusEffectInstance(InitEffects.CHARGE, durationInSec * 20, effectStrength - 1));
        player.getStackInHand(hand).decrement(1);

        player.getItemCooldownManager().set(this, COOLDOWN);

        player.playSound(InitSoundEvent.USE_STATIC_CORE, 1.0f, random.nextFloat(0.5f, 1.5f));

        return TypedActionResult.success(player.getStackInHand(hand));

    }
}
