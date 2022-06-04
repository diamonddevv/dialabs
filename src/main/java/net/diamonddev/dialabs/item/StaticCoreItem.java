package net.diamonddev.dialabs.item;

import net.diamonddev.dialabs.init.InitEffects;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

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

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        if (Screen.hasControlDown()) {
            tooltip.add(new TranslatableText("tooltip.dialabs.static_core").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        } else {
            tooltip.add(new TranslatableText("tooltip.dialabs.common_hold_down_message"));
        }

    }
}
