package net.diamonddev.dialabs.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeInvoker {
    @Invoker("registerPotionRecipe")
    static void dialabs$invokeRegisterPotionRecipe(Potion input, Item item, Potion output) {
        throw new AssertionError();
    }
}
