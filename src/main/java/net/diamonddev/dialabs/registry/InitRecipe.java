package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.recipe.serializer.SynthesisRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitRecipe implements RegistryInit {

    public void init() {
        // Synthesis
        Registry.register(Registries.RECIPE_SERIALIZER, Dialabs.id.build(SynthesisRecipeSerializer.ID), SynthesisRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Dialabs.id.build(SynthesisRecipe.Type.ID), SynthesisRecipe.Type.INSTANCE);
    }

}
