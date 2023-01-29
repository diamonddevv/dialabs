package net.diamonddev.dialabs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Explosion.class)
public interface ExplosionAccessor {

    @Accessor("power")
    float getPower();

    @Accessor("entity")
    Entity getEntity();

    @Accessor("world")
    World getWorld();

    @Accessor("x")
    double getX();
    @Accessor("y")
    double getY();
    @Accessor("z")
    double getZ();



}
