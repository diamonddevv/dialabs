package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.api.Identifier;
import net.diamonddev.dialabs.recipe.SynthesisRecipe;
import net.diamonddev.dialabs.recipe.serializer.SynthesisRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class InitRecipe {

    public static void register() {
        // Synthesis
        Registry.register(Registry.RECIPE_TYPE, new Identifier(SynthesisRecipe.Type.ID), SynthesisRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, SynthesisRecipeSerializer.ID, SynthesisRecipeSerializer.INSTANCE);
    }

}
