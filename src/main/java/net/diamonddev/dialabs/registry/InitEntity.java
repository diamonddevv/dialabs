package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class InitEntity implements RegistryInit {

    public static EntityType<ThrowableItemEntity> THROWN_ITEM;


    @Override
    public void init() {
        THROWN_ITEM = register(Dialabs.id.build("thrown_item"), createThrownItemEntityType(ThrowableItemEntity::new));
    }


    private static <T extends Entity> EntityType<T> register(Identifier id, EntityType<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, id, entityType);
    }

    ///////////////////

    private static <T extends Entity> EntityType<T> createThrownItemEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                .dimensions(EntityDimensions.changing(0.25f, 0.25f))
                .trackRangeBlocks(64)
                .trackedUpdateRate(1)
                .forceTrackedVelocityUpdates(true)
                .build();
    }
}
