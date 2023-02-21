package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.resource.recipe.DialabsRecipeDataListener;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class InitResourceListener implements RegistryInitializer {
    @Override
    public void register() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new DialabsRecipeDataListener());
    }
}
