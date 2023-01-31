package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeManager;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeType;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.recipe.ddv.StrikingRecipe;
import net.diamonddev.dialabs.recipe.serializer.SynthesisRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitRecipe implements RegistryInit {

    public static final DialabsRecipeType STRIKING = new StrikingRecipe();
    public void init() {
        // Dialabs Recipes
        DialabsRecipeManager.registerType(STRIKING);


        // Synthesis
        Registry.register(Registries.RECIPE_SERIALIZER, Dialabs.id.build(SynthesisRecipeSerializer.ID), SynthesisRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Dialabs.id.build(SynthesisRecipe.Type.ID), SynthesisRecipe.Type.INSTANCE);

    }

}
