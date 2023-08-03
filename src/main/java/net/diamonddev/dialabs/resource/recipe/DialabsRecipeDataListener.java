package net.diamonddev.dialabs.resource.recipe;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class DialabsRecipeDataListener extends CognitionDataListener {
    public DialabsRecipeDataListener() {
        super("Dialabs Recipes Loader", Dialabs.id("dialabs_recipes"), "dialabs_recipes", ResourceType.SERVER_DATA);
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {

    }

    @Override
    public void onFinishReload() {

    }

    @Override
    public void onClearCachePhase() {

    }
}
