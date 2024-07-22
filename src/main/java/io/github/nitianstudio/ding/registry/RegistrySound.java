package io.github.nitianstudio.ding.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Locale;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.Ding.MODID;
import static io.github.nitianstudio.ding.registry.AllRegistry.SOUNDS;

public enum RegistrySound implements Supplier<SoundEvent> {
    ding;

    private final DeferredHolder<SoundEvent, SoundEvent> sound;
    RegistrySound() {
        String name = name().toLowerCase(Locale.ROOT);

        sound = SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, name)));
    }

    public static void registry() {}

    @Override
    public SoundEvent get() {
        return sound.get();
    }
}
