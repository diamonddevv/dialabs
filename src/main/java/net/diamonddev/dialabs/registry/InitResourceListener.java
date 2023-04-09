package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.resource.RootDataListener;
import net.diamonddev.dialabs.resource.recipe.DialabsRecipeDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    public static final CognitionDataListener DIALABS_RECIPES = new DialabsRecipeDataListener();
    public static final CognitionDataListener ROOT = new RootDataListener();
    @Override
    public void register() {
        CognitionDataListener.registerListener(DIALABS_RECIPES);
        CognitionDataListener.registerListener(ROOT);
    }
}
