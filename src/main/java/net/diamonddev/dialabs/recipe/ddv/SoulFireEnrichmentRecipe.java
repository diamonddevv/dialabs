package net.diamonddev.dialabs.recipe.ddv;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.api.v0.recipe.DialabsRecipeType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class SoulFireEnrichmentRecipe implements DialabsRecipeType {

    public static final String ALPHA_IN = "input_a";
    public static final String BETA_IN = "input_b";
    public static final String OUTPUT = "output";

    @Override
    public Identifier getId() {
        return Dialabs.id.build("soul_fire_enrichment");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(ALPHA_IN);
        keys.add(BETA_IN);
        keys.add(OUTPUT);
    }
}
