package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.resource.RootDataListener;
import net.diamonddev.dialabs.resource.VanillaSyntheticEnchantment;
import net.diamonddev.dialabs.resource.recipe.DialabsRecipeDataListener;
import net.diamonddev.dialabs.resource.recipe.SoulFireEnrichmentRecipe;
import net.diamonddev.dialabs.resource.recipe.StrikingRecipe;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    public static final CognitionResourceType STRIKING = new StrikingRecipe();
    public static final CognitionResourceType SOUL_FIRE_ENRICHING = new SoulFireEnrichmentRecipe();
    public static final CognitionResourceType VANILLA_SYNTHETICS = new VanillaSyntheticEnchantment();

    public static final CognitionDataListener DIALABS_RECIPES = new DialabsRecipeDataListener();
    public static final CognitionDataListener ROOT = new RootDataListener();


    @Override
    public void register() {
        CognitionDataListener.registerListener(DIALABS_RECIPES);
        CognitionDataListener.registerListener(ROOT);

        // Dialabs Recipes
        CognitionResourceManager.registerType(InitResourceListener.DIALABS_RECIPES, STRIKING);
        CognitionResourceManager.registerType(InitResourceListener.DIALABS_RECIPES, SOUL_FIRE_ENRICHING);

        // Root
        CognitionResourceManager.registerType(InitResourceListener.ROOT, VANILLA_SYNTHETICS);
    }
}
