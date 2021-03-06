package net.diamonddev.dialabs.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;

public class ChargeDamageSource extends DamageSource {
    public final Entity source;

    public ChargeDamageSource(String name, Entity source) {
        super(name);
        this.source = source;
    }

    public Entity getAttacker() {
        return this.source;
    }

    public String toString() {
        return "ChargeDamageSource (" + this.source + ")";
    }

    public Text getDeathMessage(LivingEntity deadEntity) {
        String string = "death.attack." + this.name + ".player";
        return Text.translatable(string, deadEntity.getDisplayName(), this.source.getDisplayName());
    }

}
