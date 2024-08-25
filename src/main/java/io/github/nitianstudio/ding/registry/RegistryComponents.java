package io.github.nitianstudio.ding.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.nitianstudio.ding.registry.AllRegistry.COMPONENTS;

public class RegistryComponents {
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> forge_anvil_ding
            = COMPONENTS.registerComponentType("forge_anvil_ding", builder -> builder.persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static void register() {

    }
}
