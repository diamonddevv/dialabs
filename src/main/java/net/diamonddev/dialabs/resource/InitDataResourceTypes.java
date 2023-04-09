package net.diamonddev.dialabs.resource;

import net.diamonddev.dialabs.registry.InitResourceListener;
import net.diamonddev.dialabs.resource.recipe.SoulFireEnrichmentRecipe;
import net.diamonddev.dialabs.resource.recipe.StrikingRecipe;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitDataResourceTypes implements RegistryInitializer {

    public static final CognitionResourceType STRIKING = new StrikingRecipe();
    public static final CognitionResourceType SOUL_FIRE_ENRICHING = new SoulFireEnrichmentRecipe();

    public static final CognitionResourceType VANILLA_SYNTHETICS = new VanillaSyntheticEnchantment();

    @Override
    public void register() {
        // Dialabs Recipes
        CognitionResourceManager.registerType(InitResourceListener.DIALABS_RECIPES, STRIKING);
        CognitionResourceManager.registerType(InitResourceListener.DIALABS_RECIPES, SOUL_FIRE_ENRICHING);

        // Root
        CognitionResourceManager.registerType(InitResourceListener.ROOT, VANILLA_SYNTHETICS);
    }
}
