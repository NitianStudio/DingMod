package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

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
        linkage,
        ;
        private final TagKey<Item> tag;
        public static final List<TagRegistry.Items> forge_anvil_block_tags = List.of(ingot, diamond, gem, pickaxe, shovel, axe, sword, hoe, chest, helmet, legging, boot, nugget, star, ding, linkage);
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
    public enum Blocks implements Supplier<TagKey<Block>> {
        anvil;

        private final TagKey<Block> tag;

        Blocks() {
            tag = BlockTags.create(Ding.id(name().toLowerCase(Locale.ROOT)));
        }

        /**
         * Gets a result.
         *
         * @return a result
         */
        @Override
        public TagKey<Block> get() {
            return tag;
        }
    }
}
