package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static net.diamonddev.dialabs.api.DiaLabsGamerules.CRYSTAL_SHARD_LENGTH;
import static net.diamonddev.dialabs.api.DiaLabsGamerules.CRYSTAL_SHARD_STRENGTH;


public class CrystalShardItem extends Item {

    public CrystalShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        int effectStrength = world.getGameRules().getInt(CRYSTAL_SHARD_STRENGTH);
        int durationInSec = world.getGameRules().getInt(CRYSTAL_SHARD_LENGTH);

        player.addStatusEffect(new StatusEffectInstance(InitEffects.CRYSTALLISE, durationInSec * 20, effectStrength - 1));
        player.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1.0f, 2.0f);
        player.getStackInHand(hand).decrement(1);

        return TypedActionResult.success(player.getStackInHand(hand));

    }
}
