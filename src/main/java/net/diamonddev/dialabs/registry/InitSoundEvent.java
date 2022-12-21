package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.lib.RegistryInit;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class InitSoundEvent implements RegistryInit {

    public static SoundEvent SYNTHESIZE_DISC;
    public static SoundEvent BURN_DISC;

    @Override
    public void init() {
        SYNTHESIZE_DISC = createSound("synthesis.dialabs.synthesize");
        BURN_DISC = createSound("synthesis.dialabs.burn");
    }

    private SoundEvent createSound(String name) {
        Identifier id = new Identifier(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
