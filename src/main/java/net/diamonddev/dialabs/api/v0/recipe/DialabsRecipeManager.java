package net.diamonddev.dialabs.api.v0.recipe;

import net.diamonddev.dialabs.api.v0.util.KeyedArrayCache;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Consumer;

public class DialabsRecipeManager {

    // CACHE
    public static final KeyedArrayCache<DialabsRecipeType, DialabsRecipe> CACHE = new KeyedArrayCache<>();

    private static final HashMap<Identifier, DialabsRecipeType> TYPES = new HashMap<>();

    ///
    public static void registerType(DialabsRecipeType type) {
        TYPES.put(type.getId(), type);
    }

    public static DialabsRecipeType getType(Identifier typeId) {
        return TYPES.get(typeId);
    }

    public static void forEachRecipe(DialabsRecipeType recipeType, Consumer<DialabsRecipe> recipeConsumer) {
        CACHE.get(recipeType).forEach(recipeConsumer);
    }
    //
    public static final String IDPARAM = "dialabs_recipe_type";
}
