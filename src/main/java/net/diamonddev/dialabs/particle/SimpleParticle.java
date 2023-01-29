package net.diamonddev.dialabs.particle;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

public class SimpleParticle {
    private final Identifier identifier;
    private final DefaultParticleType particle;

    public SimpleParticle(Identifier id, DefaultParticleType particle) {
        this.identifier = id;
        this.particle = particle;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public DefaultParticleType getParticleType() {
        return this.particle;
    }
}

