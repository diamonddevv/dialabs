package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.recipe.serializer.SynthesisRecipeSerializer;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitRecipe implements RegistryInitializer {
    public void register() {

        // Synthesis
        Registry.register(Registries.RECIPE_SERIALIZER, Dialabs.id(SynthesisRecipeSerializer.ID), SynthesisRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Dialabs.id(SynthesisRecipe.Type.ID), SynthesisRecipe.Type.INSTANCE);

    }

}
