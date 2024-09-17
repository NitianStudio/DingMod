package io.github.nitiaonstudio.ding.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.mojang.serialization.Codec.*;
import static io.github.nitiaonstudio.ding.registry.AllRegistry.components;

public class ComponentRegistry {
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> forgeAnvilValue = components.registerComponentType("forge_anvil_value", builder -> builder
            .persistent(INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> cd = components.registerComponentType("cd", builder -> builder
            .persistent(INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> compression = components.registerComponentType("compression", builder -> builder
            .persistent(STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
    public static void registry() {

    }
}
