package io.github.nitiaonstudio.ding.data.sounds;

import io.github.nitiaonstudio.ding.registry.SoundRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Consumer;

public class SoundGeneration extends SoundDefinitionsProvider {
    public SoundGeneration(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    public void add(DeferredHolder<SoundEvent, SoundEvent> holder, Consumer<SoundDefinition> consumer) {
        SoundDefinition definition = definition();
        consumer.accept(definition);
        add(holder, definition);
    }

    public void add(DeferredHolder<SoundEvent, SoundEvent> holder) {

        add(holder, definition());
    }

    @Override
    public void registerSounds() {
        add(SoundRegistry.ding, definition -> {
            ResourceLocation location = SoundRegistry.ding.get().getLocation();
            definition
                    .subtitle("sound.%s.%s".formatted(location.getNamespace(), location.getPath()))
                    .replace(true)
                    .with(sound(location, SoundDefinition.SoundType.SOUND).volume(0.8).pitch(1.2).weight(2).attenuationDistance(8).stream().preload());
        });
    }
}
