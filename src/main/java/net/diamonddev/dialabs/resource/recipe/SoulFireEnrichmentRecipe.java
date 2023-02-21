package net.diamonddev.dialabs.resource.recipe;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class SoulFireEnrichmentRecipe implements DataLoaderResourceType {

    public static final String ALPHA_IN = "input_a";
    public static final String BETA_IN = "input_b";
    public static final String OUTPUT = "output";

    @Override
    public Identifier getId() {
        return Dialabs.id("soul_fire_enrichment");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(ALPHA_IN);
        keys.add(BETA_IN);
        keys.add(OUTPUT);
    }
}
