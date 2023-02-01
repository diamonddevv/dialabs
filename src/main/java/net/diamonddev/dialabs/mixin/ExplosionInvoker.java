package net.diamonddev.dialabs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Explosion.class)
public interface ExplosionInvoker {

    @Invoker("getExposure")
    static float invokeGetExposure(Vec3d source, Entity entity) {
        throw new RuntimeException("THE CODE IS BURNING AND YOU'RE GONNA BURN WITH IT");
    }

}
