package net.diamonddev.dialabs.util;

import com.google.gson.JsonSyntaxException;
import net.minecraft.recipe.Ingredient;

public class OrEmptyIngredient {

    private Ingredient ingredient;

    public OrEmptyIngredient(Ingredient ingredient) {
        try {
            this.ingredient = ingredient;
        } catch (JsonSyntaxException e) {
            this.ingredient = getEmptyIngredient();
        }
    }

    public Ingredient get() {
        if (ingredient.isEmpty() || ingredient == null) {
            return getEmptyIngredient();
        }
        return ingredient;
    }

    public static Ingredient getEmptyIngredient() {
        return Ingredient.empty();
    }
}
