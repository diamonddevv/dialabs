package net.diamonddev.dialabs.resource;

import net.diamonddev.dialabs.resource.recipe.SoulFireEnrichmentRecipe;
import net.diamonddev.dialabs.resource.recipe.StrikingRecipe;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceManager;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitDataResourceTypes implements RegistryInitializer {

    public static final DataLoaderResourceType STRIKING = new StrikingRecipe();
    public static final DataLoaderResourceType SOUL_FIRE_ENRICHING = new SoulFireEnrichmentRecipe();

    @Override
    public void register() {
        // Dialabs Recipes
        DataLoaderResourceManager.registerType(STRIKING);
        DataLoaderResourceManager.registerType(SOUL_FIRE_ENRICHING);
    }
}
