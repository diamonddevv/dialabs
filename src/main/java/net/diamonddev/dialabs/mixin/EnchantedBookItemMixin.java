package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static net.minecraft.item.EnchantedBookItem.getEnchantmentNbt;


@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {

    @Inject(method = "appendStacks", at = @At("TAIL"))
    private void dialabs$removeSyntheticEnchantmentBookEntries(ItemGroup group, DefaultedList<ItemStack> stacks, CallbackInfo ci) {
        stacks.removeIf(stack -> {
            boolean bl = false;
            if (stack.getItem() instanceof EnchantedBookItem) {
                Map<Enchantment, Integer> enchantMapData = EnchantmentHelper.fromNbt(getEnchantmentNbt(stack));
                for (Enchantment e : enchantMapData.keySet()) {
                    if (e instanceof SyntheticEnchantment) {
                        bl = true;
                        break;
                    }
                }
            }
            return bl;
        });
    }
}
