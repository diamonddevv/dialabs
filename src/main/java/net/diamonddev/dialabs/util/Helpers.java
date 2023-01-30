package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.Dialabs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.function.BiConsumer;

public class Helpers {
    public static Collection<Integer> getEachIntegerRange(int origin, int bound) {
        Collection<Integer> intCol = new ArrayList<>();
        for (int i = origin; i <= bound; i++) {
            intCol.add(i);
        }
        return intCol;
    }

    public static <T> boolean isInTag(TagKey<T> tag, T object) {
        RegistryEntry<T> entry = RegistryEntry.of(object);
        return entry.isIn(tag);
    }

    public static <A, B> BiConsumer<A, B> and(BiConsumer<A, B> alpha, BiConsumer<A, B> bravo) {
        return (a, b) -> {
            if (alpha != null) alpha.accept(a, b);
            if (bravo != null) bravo.accept(a, b);
        };
    }


    public static void spawnParticles(World world, ParticleEffect parameters, double x, double y, double z, int count, double speed, double dX, double dY, double dZ) {
        Random random = new Random();
        for(int i = 0; i < count; ++i) {
            double g = random.nextGaussian() * dX;
            double h = random.nextGaussian() * dY;
            double j = random.nextGaussian() * dZ;
            double k = random.nextGaussian() * speed;
            double l = random.nextGaussian() * speed;
            double m = random.nextGaussian() * speed;

            try {
                world.addParticle(parameters, true, x + g, y + h, z + j, k, l, m);
            } catch (Throwable t) {
                Dialabs.LOGGER.warn("Could not spawn particle effect (using Dialabs Helpers.class) {}", parameters);
            }
        }
    }
}
