package net.diamonddev.dialabs.api.v0.recipe;

import com.google.gson.annotations.SerializedName;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

public class DialabsRecipeManager {

    // CACHE
    public static final ArrayList<DialabsRecipe> CACHE = new ArrayList<>();

    private static final HashMap<Identifier, DialabsRecipeType> TYPES = new HashMap<>();



    public static void registerType(Identifier identifier, DialabsRecipeType type) {
        TYPES.put(identifier, type);
    }
    public static Identifier deserializeType(JsonRecipeSerializer serializer) {
        return new Identifier(serializer.stringifiedTypeIdentifier);
    }


    private static final String IDPARAM = "dialabs_recipe_type";
    public static class JsonRecipeSerializer {
        @SerializedName(IDPARAM)
        String stringifiedTypeIdentifier;
    }
}
