package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.item.SyntheticEnchantmentDiscItem;
import net.diamonddev.dialabs.registry.InitItem;
import net.diamonddev.dialabs.util.EnchantHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow @Final private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At("HEAD"))
    private void dialabs$allowPullingStoredSyntheticDiscEnchantments(CallbackInfo ci) {
        ItemStack discInput = this.input.getStack(0);
        ItemStack thingToEnchant = this.input.getStack(1);
        ItemStack disc = discInput.copy();

        if (discInput.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
            this.levelCost.set(0);
        } else {
            Map<Enchantment, Integer> mappedDisc = EnchantHelper.getMappedStoredEnchantments(disc);
            if (!thingToEnchant.isEmpty()) {
                boolean bl;
                if (thingToEnchant.isOf(InitItem.SYNTHETIC_ENCHANTMENT_DISC) &&
                        !SyntheticEnchantmentDiscItem.getEnchantmentNbt(thingToEnchant).isEmpty()) {

                } else if (mappedDisc.forEach((enchantment, integer) ->
                        benchantment.isAcceptableItem(thingToEnchant))) {

                }

            }
        }
    }


}
