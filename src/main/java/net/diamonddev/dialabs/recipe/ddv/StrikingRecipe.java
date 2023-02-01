package net.diamonddev.dialabs.recipe.ddv;


import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class StrikingRecipe implements DialabsRecipeType {

    public static final String ORIGINAL_BLOCK_KEY = "original";
    public static final String NEW_BLOCK_KEY = "result";
    @Override
    public Identifier getId() {
        return Dialabs.id.build("striking");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(ORIGINAL_BLOCK_KEY);
        keys.add(NEW_BLOCK_KEY);
    }

}
