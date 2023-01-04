package net.diamonddev.dialabs.mixin;

import net.diamonddev.dialabs.enchant.SyntheticEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.command.EnchantCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {

    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"
            )
    )
    private static int dialabs$checkMaxSyntheticLevelIfHigherInstead(Enchantment instance) {
        int normal = instance.getMaxLevel();
        if (SyntheticEnchantment.validSyntheticEnchantments.contains(instance)) {
            int synth =  SyntheticEnchantment.hashSyntheticEnchantMaxLevel.get(instance);
            if (synth > normal) {
                return synth;
            }
        }
        return normal;
    }
}
