package io.github.nitianstudio.ding.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Locale;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.registry.AllRegistry.ITEMS;

public enum RegistryItem implements Supplier<Item> {
    forge_anvil(RegistryBlock.forge_anvil, new Item.Properties());
    public final DeferredItem<? extends Item> item;
    RegistryItem(Function<Item.Properties, Item> function) {
        item = ITEMS.registerItem(name().toLowerCase(Locale.ROOT), function);
    }
    RegistryItem(RegistryBlock block, Item.Properties properties) {
        item = ITEMS.registerSimpleBlockItem(name().toLowerCase(Locale.ROOT), block, properties);
    }

    public static void registry() {

    }

    @Override
    public Item get() {
        return item.get();
    }
}
