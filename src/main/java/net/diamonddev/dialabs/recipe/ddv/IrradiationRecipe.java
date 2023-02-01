package net.diamonddev.dialabs.recipe.ddv;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class IrradiationRecipe implements DialabsRecipeType {

    public static final String JSON_ORIGINAL_KEY = "original";
    public static final String JSON_CATALYST_KEY = "catalyst";
    public static final String JSON_NEW_KEY = "new";


    @Override
    public Identifier getId() {
        return Dialabs.id.build("irradiation");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(JSON_ORIGINAL_KEY);
        keys.add(JSON_CATALYST_KEY);
        keys.add(JSON_NEW_KEY);
    }
}
