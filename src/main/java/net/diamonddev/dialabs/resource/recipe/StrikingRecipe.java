package net.diamonddev.dialabs.resource.recipe;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class StrikingRecipe implements CognitionResourceType {

    public static final String ORIGINAL_BLOCK_KEY = "original";
    public static final String NEW_BLOCK_KEY = "result";
    @Override
    public Identifier getId() {
        return Dialabs.id("striking");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(ORIGINAL_BLOCK_KEY);
        keys.add(NEW_BLOCK_KEY);
    }

}
