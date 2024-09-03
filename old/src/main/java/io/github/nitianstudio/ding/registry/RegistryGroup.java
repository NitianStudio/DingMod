package io.github.nitianstudio.ding.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.registry.AllRegistry.GROUPS;

public enum RegistryGroup implements Supplier<CreativeModeTab> {
    ding(builder -> builder
            .title(Component.translatable("itemGroup.ding"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(RegistryItem.forge_anvil_block.asItem()::getDefaultInstance).displayItems((parameters, output) -> {
                output.accept(RegistryItem.forge_anvil_block);
            })
    );
    public final DeferredHolder<CreativeModeTab, CreativeModeTab> group;
    RegistryGroup(Consumer<CreativeModeTab.Builder> consumer) {
        group = GROUPS.register(name().toLowerCase(Locale.ROOT), () -> {
            CreativeModeTab.Builder builder = CreativeModeTab.builder();
            consumer.accept(builder);
            return builder.build();
        });
    }

    RegistryGroup(Supplier<Item> item) {
        group = GROUPS.register(name().toLowerCase(Locale.ROOT), () ->
                CreativeModeTab
                        .builder()
                        .icon(item.get()::getDefaultInstance)
                        .title(Component.translatable("itemGroup." + name().toLowerCase(Locale.ROOT)))
                        .build()
        );
    }


    public static void registry() {
    }

    @Override
    public CreativeModeTab get() {
        return group.get();
    }
}
