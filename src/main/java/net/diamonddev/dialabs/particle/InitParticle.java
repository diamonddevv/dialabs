package net.diamonddev.dialabs.particle;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.dialabs.lib.RegistryInit;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitParticle implements RegistryInit {
    public static final SimpleParticle SPARK = new SimpleParticle(Dialabs.id.build("spark"), FabricParticleTypes.simple());

    public void init() {
        // Atlas Stiching

        Registry.register(Registries.PARTICLE_TYPE, SPARK.getIdentifier(), SPARK.getParticleType());


        // ParticleFactoryRegistry
        ParticleFactoryRegistry.getInstance().register(SPARK.getParticleType(), FlameParticle.Factory::new);
    }
}
