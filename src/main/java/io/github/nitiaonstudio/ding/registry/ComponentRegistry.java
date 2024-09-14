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
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forgeAnvilBlockMoveX = components.registerComponentType("forge_anvil_block_move_x", builder -> builder
            .persistent(DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forgeAnvilBlockMoveZ = components.registerComponentType("forge_anvil_block_move_z", builder -> builder
            .persistent(DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forgeAnvilBlockRotateY = components.registerComponentType("forge_anvil_block_rotate_y", builder -> builder
            .persistent(DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forgeAnvilBlockToMoveX = components.registerComponentType("forge_anvil_block_to_move_x", builder -> builder
            .persistent(DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forgeAnvilBlockToMoveZ = components.registerComponentType("forge_anvil_block_to_move_z", builder -> builder
            .persistent(DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forgeAnvilBlockToRotateY = components.registerComponentType("forge_anvil_block_to_rotate_y", builder -> builder
            .persistent(DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));

    public static void registry() {

    }
}
