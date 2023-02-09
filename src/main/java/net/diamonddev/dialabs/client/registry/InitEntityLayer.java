package net.diamonddev.dialabs.client.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class InitEntityLayer implements RegistryInit {

    public static EntityModelLayer FLINTLOCK_PELLET;

    @Override
    public void init() {
        FLINTLOCK_PELLET = registerMain("flintlock_pellet");
    }

    private static EntityModelLayer registerMain(String id) {
        return new EntityModelLayer(Dialabs.id.build(id), "main");
    }
}
