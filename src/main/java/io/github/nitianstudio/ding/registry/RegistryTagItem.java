package io.github.nitianstudio.ding.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Locale;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.Ding.MODID;

public enum RegistryTagItem implements Supplier<TagKey<Item>> {
    ingot;

    private final TagKey<Item> tag;
    RegistryTagItem() {
        tag = dingTags(name().toLowerCase(Locale.ROOT));
    }

    private static TagKey<Item> dingTags(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    @Override
    public TagKey<Item> get() {
        return tag;
    }
}
