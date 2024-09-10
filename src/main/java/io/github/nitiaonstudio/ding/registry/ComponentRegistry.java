package io.github.nitiaonstudio.ding.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.mojang.serialization.Codec.STRING;
import static io.github.nitiaonstudio.ding.registry.AllRegistry.components;

public class ComponentRegistry {
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> modes = components.registerComponentType("forge_anvil_block_modes", builder -> builder
            .persistent(STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
    public static void registry() {

    }
}
