package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.mixin.BrewingRecipeInvoker;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitPotion implements RegistryInitializer {

    public static Potion DARKNESS = new Potion(new StatusEffectInstance(StatusEffects.DARKNESS, 600, 0));
    public static Potion DECAY = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 600, 0));
    public static Potion DECAY_LONG = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 800, 0));
    public static Potion DECAY_STRONG = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 300, 1));

    public void register() {
        Registry.register(Registries.POTION, Dialabs.id("darkness"), DARKNESS);
        Registry.register(Registries.POTION, Dialabs.id("decay"), DECAY);
        Registry.register(Registries.POTION, Dialabs.id("decay_long"), DECAY_LONG);
        Registry.register(Registries.POTION, Dialabs.id("decay_strong"), DECAY_STRONG);


        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(Potions.AWKWARD, Items.ECHO_SHARD, DARKNESS);
        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(Potions.AWKWARD, Items.WITHER_ROSE, DECAY);
        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(DECAY, Items.REDSTONE, DECAY_LONG);
        BrewingRecipeInvoker.dialabs$invokeRegisterPotionRecipe(DECAY, Items.GLOWSTONE_DUST, DECAY_STRONG);
    }
}
