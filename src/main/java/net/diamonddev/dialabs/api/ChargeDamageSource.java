package net.diamonddev.dialabs.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

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

    public Text getDeathMessage(LivingEntity entity) {
        String string = "death.attack." + this.name;
        return new TranslatableText(string, entity.getDisplayName(), this.source.getDisplayName());
    }

}
