package io.github.nitiaonstudio.ding.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.nitiaonstudio.ding.registry.AllRegistry.groups;
import static io.github.nitiaonstudio.ding.registry.AllRegistry.items;

public class ItemGroupRegistry {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ding = groups.register("ding", () -> CreativeModeTab
            .builder()
            .icon(() -> ItemRegistry.forge_hammer.get().getDefaultInstance())
            .displayItems((CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) -> {
                for (DeferredHolder<Item, ? extends Item> entry : items.getEntries()) {
                    output.accept(entry.get());
                }
            })
            .build());
    public static void registry() {

    }
}
