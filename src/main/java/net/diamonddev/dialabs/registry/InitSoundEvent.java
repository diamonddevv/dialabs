package net.diamonddev.dialabs.registry;

import net.diamonddev.dialabs.Dialabs;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class InitSoundEvent implements RegistryInitializer {

    public static SoundEvent SYNTHESIZE_DISC;
    public static SoundEvent BURN_DISC;
    public static SoundEvent USE_CRYSTAL_SHARDS;
    public static SoundEvent USE_STATIC_CORE;

    @Override
    public void register() {
        SYNTHESIZE_DISC = createSound("synthesis.dialabs.synthesize");
        BURN_DISC = createSound("synthesis.dialabs.burn");
        USE_CRYSTAL_SHARDS = createSound("item.dialabs.crystal_shards.use");
        USE_STATIC_CORE = createSound("item.dialabs.static_core.use");
    }

    private SoundEvent createSound(String name) {
        Identifier id = Dialabs.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
