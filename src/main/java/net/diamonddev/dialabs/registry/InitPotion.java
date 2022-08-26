package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.mixin.BrewingRecipeInvoker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;

public class InitPotion {

    public static Potion DARKNESS = new Potion(new StatusEffectInstance(StatusEffects.DARKNESS, 600, 0));
    public static Potion DECAY = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 600, 0));
    public static Potion DECAY_LONG = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 800, 0));
    public static Potion DECAY_STRONG = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 300, 1));

    public static void register() {
        Registry.register(Registry.POTION, new Identifier("darkness"), DARKNESS);
        Registry.register(Registry.POTION, new Identifier("decay"), DECAY);
        Registry.register(Registry.POTION, new Identifier("decay_long"), DECAY_LONG);
        Registry.register(Registry.POTION, new Identifier("decay_strong"), DECAY_STRONG);


        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(Potions.AWKWARD, Items.ECHO_SHARD, DARKNESS);
        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(Potions.AWKWARD, Items.WITHER_ROSE, DECAY);
        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(DECAY, Items.REDSTONE, DECAY_LONG);
        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(DECAY, Items.GLOWSTONE_DUST, DECAY_STRONG);
    }
}
