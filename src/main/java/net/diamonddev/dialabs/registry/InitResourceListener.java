package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.lib.RegistryInit;
import net.diamonddev.dialabs.resource.DialabsRecipeDataListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitResourceListener implements RegistryInit {

    public static final Logger RESOURCE_MANAGER_LOGGER = LoggerFactory.getLogger("Dialabs  - Resource Manager");


    @Override
    public void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new DialabsRecipeDataListener());
    }
}
