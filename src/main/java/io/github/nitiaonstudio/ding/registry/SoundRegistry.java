package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.nitiaonstudio.ding.registry.AllRegistry.sounds;

public class SoundRegistry {
    public static final DeferredHolder<SoundEvent, SoundEvent> ding = sounds.register("ding", () -> SoundEvent.createVariableRangeEvent(Ding.id("ding")));
}
