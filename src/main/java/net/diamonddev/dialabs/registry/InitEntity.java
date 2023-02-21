package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.entity.FlintlockPelletEntity;
import net.diamonddev.dialabs.entity.ThrowableItemEntity;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class InitEntity implements RegistryInitializer {

    public static EntityType<ThrowableItemEntity> THROWN_ITEM;
    public static EntityType<FlintlockPelletEntity> FLINTLOCK_PELLET;


    @Override
    public void register() {
        THROWN_ITEM = register(Dialabs.id("thrown_item"), createThrownItemEntityType(ThrowableItemEntity::new));

        FLINTLOCK_PELLET = register(Dialabs.id("flintlock_pellet"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, FlintlockPelletEntity::new)
                .dimensions(EntityDimensions.changing(.1f, .1f))
                .trackRangeBlocks(64)
                .trackedUpdateRate(1)
                .forceTrackedVelocityUpdates(true)
                .build()
        );
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
