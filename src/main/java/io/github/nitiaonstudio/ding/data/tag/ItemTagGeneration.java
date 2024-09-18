package io.github.nitiaonstudio.ding.data.tag;

import dev.dubhe.anvilcraft.init.ModItems;
import io.github.nitiaonstudio.ding.data.FunctionIII;
import io.github.nitiaonstudio.ding.registry.ItemRegistry;
import io.github.nitiaonstudio.ding.registry.TagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static io.github.nitiaonstudio.ding.Ding.MODID;

public class ItemTagGeneration extends ItemTagsProvider {


    public ItemTagGeneration(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, BlockTagGeneration btg) {
        super(output, lookupProvider, btg.contentsGetter(), MODID, existingFileHelper);
    }

    public IntrinsicTagAppender<Item> tag(Supplier<TagKey<Item>> supplier) {
        return tag(supplier.get());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        IntrinsicTagAppender<Item> ding = tag(TagRegistry.Items.ding.get());
        IntrinsicTagAppender<Item> ingot = tag(TagRegistry.Items.ingot.get());
        IntrinsicTagAppender<Item> diamond = tag(TagRegistry.Items.diamond.get());
        IntrinsicTagAppender<Item> gem = tag(TagRegistry.Items.gem.get());
        IntrinsicTagAppender<Item> pickaxe = tag(TagRegistry.Items.pickaxe.get());
        IntrinsicTagAppender<Item> shovel = tag(TagRegistry.Items.shovel.get());
        IntrinsicTagAppender<Item> axe = tag(TagRegistry.Items.axe.get());
        IntrinsicTagAppender<Item> sword = tag(TagRegistry.Items.sword.get());
        IntrinsicTagAppender<Item> hoe = tag(TagRegistry.Items.hoe.get());
        IntrinsicTagAppender<Item> chest = tag(TagRegistry.Items.chest.get());
        IntrinsicTagAppender<Item> helmet = tag(TagRegistry.Items.helmet.get());
        IntrinsicTagAppender<Item> legging = tag(TagRegistry.Items.legging.get());
        IntrinsicTagAppender<Item> boot = tag(TagRegistry.Items.boot.get());
        IntrinsicTagAppender<Item> body = tag(TagRegistry.Items.body.get());
        IntrinsicTagAppender<Item> nugget = tag(TagRegistry.Items.nugget.get());
        IntrinsicTagAppender<Item> star = tag(TagRegistry.Items.star.get());
        IntrinsicTagAppender<Item> linkage = tag(TagRegistry.Items.linkage.get());

        ingot.add(
                Items.IRON_INGOT,
                Items.COPPER_INGOT,
                Items.GOLD_INGOT,
                Items.NETHERITE_INGOT
        );//ingot
        diamond.add(Items.DIAMOND);//diamond
        gem.add(Items.EMERALD);//gem
        add(pickaxe, PickaxeItem.class);// pickaxes
        add(shovel, ShovelItem.class);//shovel
        add(axe, AxeItem.class);// axe
        add(sword, SwordItem.class);
        add(hoe, HoeItem.class);
        chest.add(
                Items.IRON_CHESTPLATE,
                Items.GOLDEN_CHESTPLATE,
                Items.DIAMOND_CHESTPLATE,
                Items.NETHERITE_CHESTPLATE
        );
        helmet.add(
                Items.IRON_HELMET,
                Items.GOLDEN_HELMET,
                Items.DIAMOND_HELMET,
                Items.NETHERITE_HELMET
        );
        legging.add(
                Items.IRON_LEGGINGS,
                Items.GOLDEN_LEGGINGS,
                Items.DIAMOND_LEGGINGS,
                Items.NETHERITE_LEGGINGS
        );
        boot.add(
                Items.IRON_BOOTS,
                Items.GOLDEN_BOOTS,
                Items.DIAMOND_BOOTS,
                Items.NETHERITE_BOOTS
        );

        nugget.add(Items.IRON_NUGGET, Items.GOLD_NUGGET);
        star.add(Items.NETHER_STAR);
        ding.add(
                ItemRegistry.forge_hammer.get(),
                ItemRegistry.forge_hammer_gold.get(),
                ItemRegistry.forge_hammer_copper.get(),
                ItemRegistry.forge_hammer_diamond.get(),
                ItemRegistry.forge_hammer_netherite.get()
        );
        linkage.add(
                ModItems.MAGNET.get(),
                ModItems.TUNGSTEN_INGOT.get(),
                ModItems.CURSED_GOLD_INGOT.get(),
                ModItems.ROYAL_STEEL_INGOT.get(),
                ModItems.TITANIUM_INGOT.get(),
                ModItems.ZINC_INGOT.get(),
                ModItems.LEAD_INGOT.get()
        );


    }

    public static <T extends Item> void add(IntrinsicTagAppender<Item> appender, Class<T> clazz) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item.getClass().isAssignableFrom(clazz)) {
                appender.add(item);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item> void add(IntrinsicTagAppender<Item> appender, Class<T> clazz, FunctionIII<IntrinsicTagAppender<Item>, IntrinsicTagAppender<Item>, T> functionIII) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item.getClass().isAssignableFrom(clazz)) {
                functionIII.apply(appender, (T) item);
            }
        }
    }
}
