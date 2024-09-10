package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class TagRegistry {
    public enum Items implements Supplier<TagKey<Item>> {
        ding,
        ingot,
        diamond,
        gem,
        pickaxe,
        shovel,
        axe,
        sword,
        hoe,
        chest,
        helmet,
        legging,
        boot,
        body,
        nugget,
        star,
        ;
        private final TagKey<Item> tag;
        public static final List<TagRegistry.Items> forge_anvil_block_tags = List.of(ingot, diamond, gem, pickaxe, shovel, axe, sword, hoe, chest, helmet, legging, boot, nugget, star);
        Items() {
            tag = dingTags(name().toLowerCase(Locale.ROOT));
        }

        private static TagKey<Item> dingTags(String name) {
            return ItemTags.create(Ding.id(name));
        }

        @Override
        public TagKey<Item> get() {
            return tag;
        }
    }
}
