package io.github.nitianstudio.ding.registry;

import io.github.nitianstudio.ding.Const;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import io.github.nitianstudio.ding.geo.GeoBlockItem;
import io.github.nitianstudio.ding.item.ForgeIngot;
import io.github.nitianstudio.ding.item.ForgingHammer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;

import java.util.Locale;
import java.util.function.Function;

import static io.github.nitianstudio.ding.registry.AllRegistry.ITEMS;

public enum RegistryItem implements ItemLike {
    forge_anvil_block(RegistryBlock.forge_anvil_block, new Item.Properties(), ForgeAnvilTileEntity.class),
    forge_ingot(ForgeIngot::new),
    forging_hammer(properties -> new ForgingHammer(
            properties,
            forgingHammer -> {
                return Const.id("geo/item/forging_hammer.geo.json");
            },
            forgingHammer -> {
                return Const.id("textures/item/forging_hammer.png");
            },
            forgingHammer -> {
                return Const.id("animations/item/forging_hammer.json");
            })),
    all_things_alloy(Item::new);
    public final DeferredItem<? extends Item> item;
    RegistryItem(Function<Item.Properties, Item> function) {
        item = ITEMS.registerItem(name().toLowerCase(Locale.ROOT), function);
    }

    <T extends Block & EntityBlock,E extends BlockEntity & GeoAnimatable> RegistryItem(RegistryBlock block, final Item.Properties properties, Class<E> eClass) {
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
