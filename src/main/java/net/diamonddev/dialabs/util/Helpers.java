package net.diamonddev.dialabs.util;

import net.diamonddev.dialabs.Dialabs;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.function.BiConsumer;

public class Helpers {
    private static final Random r = new Random();
    public static Collection<Integer> getEachIntegerRange(int origin, int bound) {
        Collection<Integer> intCol = new ArrayList<>();
        for (int i = origin; i <= bound; i++) {
            intCol.add(i);
        }
        return intCol;
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
    public static boolean rollRandom(double chance) {
        return r.nextDouble(0, 1) <= chance;
    }

    public static boolean isBlockInYRangeOfPos(World world, BlockPos origin, int range, Block find) {
        if (range == 0) return world.getBlockState(origin).getBlock() == find;

        boolean found = false;
        BlockPos search;

        if (range > 0) {
            for (int i = 0; i < range; i++) {
                search = origin;
                for (int j = 0; j < i; j++) {
                    search.down();
                }
                found = world.getBlockState(search).getBlock() == find;
                if (found) break;
            }
        } else {
            for (int i = 0; i > range; i--) {
                search = origin;
                for (int j = 0; j > i; j--) {
                    search.down();
                }
                found = world.getBlockState(search).getBlock() == find;
                if (found) break;
            }
        }
        return found;
    }

    public static void dropItem(ItemStack stack, World world, BlockPos pos) {
        if (!stack.isEmpty() && !world.isClient && pos != null) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

    public static Hand getOppositeHand(Hand hand) {
        return hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

}
