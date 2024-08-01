package io.github.nitianstudio.ding.registry;

import io.github.nitianstudio.ding.geo.GeoBlockItem;
import io.github.nitianstudio.ding.item.ForgeIngot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.registry.AllRegistry.ITEMS;

public enum RegistryItem implements ItemLike {
    forge_anvil_block(RegistryBlock.forge_anvil_block, new Item.Properties()),
    forge_ingot(ForgeIngot::new);
    public final DeferredItem<? extends Item> item;
    RegistryItem(Function<Item.Properties, Item> function) {
        item = ITEMS.registerItem(name().toLowerCase(Locale.ROOT), function);
    }
    RegistryItem(RegistryBlock block, final Item.Properties properties) {
        item = ITEMS.register(name().toLowerCase(Locale.ROOT), key -> {
            Block v = block.get();
            if (v instanceof GeoBlockEntity) {
                return new GeoBlockItem(v, properties);
            } else {
                return new BlockItem(v, properties);
            }
        });
    }

    public static void registry() {

    }

    @Override
    @NotNull
    public Item asItem() {
        return item.get().asItem();
    }
}
