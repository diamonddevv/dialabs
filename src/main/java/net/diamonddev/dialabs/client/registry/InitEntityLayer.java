package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.client.render.model.FlintlockPelletEntityModel;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class InitEntityLayer implements RegistryInitializer {

    public static EntityModelLayer FLINTLOCK_PELLET;

    @Override
    public void register() {
        FLINTLOCK_PELLET = registerMain("flintlock_pellet", FlintlockPelletEntityModel::getTexturedModelData);
    }

    private static EntityModelLayer registerMain(String id, EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider) {
        EntityModelLayer layer = new EntityModelLayer(Dialabs.id(id), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, texturedModelDataProvider);
        return layer;
    }
}
