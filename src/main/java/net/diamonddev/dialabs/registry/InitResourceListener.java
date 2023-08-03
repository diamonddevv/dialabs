package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.resource.MiscDataListener;
import net.diamonddev.dialabs.resource.VanillaSyntheticEnchantment;
import net.diamonddev.dialabs.resource.recipe.DialabsRecipeDataListener;
import net.diamonddev.dialabs.resource.recipe.SoulFireEnrichmentRecipe;
import net.diamonddev.dialabs.resource.recipe.StrikingRecipe;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    public static final CognitionResourceType STRIKING = new StrikingRecipe();
    public static final CognitionResourceType SOUL_FIRE_ENRICHING = new SoulFireEnrichmentRecipe();
    public static final CognitionResourceType VANILLA_SYNTHETICS = new VanillaSyntheticEnchantment();

    public static final CognitionDataListener DIALABS_RECIPES = new DialabsRecipeDataListener();
    public static final CognitionDataListener MISC = new MiscDataListener();


    @Override
    public void register() {
        CognitionRegistry.registerListener(DIALABS_RECIPES);
        CognitionRegistry.registerListener(MISC);

        // Dialabs Recipes
        CognitionRegistry.registerType(InitResourceListener.DIALABS_RECIPES, STRIKING);
        CognitionRegistry.registerType(InitResourceListener.DIALABS_RECIPES, SOUL_FIRE_ENRICHING);

        // Root
        CognitionRegistry.registerType(InitResourceListener.MISC, VANILLA_SYNTHETICS);
    }
}
