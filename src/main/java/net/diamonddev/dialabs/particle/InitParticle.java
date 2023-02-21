package net.diamonddev.dialabs.particle;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitParticle implements RegistryInitializer {
    public static final SimpleParticle SPARK = new SimpleParticle(Dialabs.id("spark"), FabricParticleTypes.simple());

    public void register() {
        // Atlas Stiching

        Registry.register(Registries.PARTICLE_TYPE, SPARK.getIdentifier(), SPARK.getParticleType());


        // ParticleFactoryRegistry
        ParticleFactoryRegistry.getInstance().register(SPARK.getParticleType(), FlameParticle.Factory::new);
    }
}
