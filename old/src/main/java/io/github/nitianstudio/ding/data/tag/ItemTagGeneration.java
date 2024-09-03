package io.github.nitianstudio.ding.data.tag;

import com.mojang.datafixers.util.Function3;
import io.github.nitianstudio.ding.Const;
import io.github.nitianstudio.ding.data.FunctionIII;
import io.github.nitianstudio.ding.registry.RegistryTagItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ItemTagGeneration extends ItemTagsProvider implements Const {


    public ItemTagGeneration(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, BlockTagGeneration btg) {
        super(output, lookupProvider, btg.contentsGetter(), MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        IntrinsicTagAppender<Item> ingot = tag(RegistryTagItem.ingot.get());
        IntrinsicTagAppender<Item> diamond = tag(RegistryTagItem.diamond.get());
        IntrinsicTagAppender<Item> gem = tag(RegistryTagItem.gem.get());
        IntrinsicTagAppender<Item> pickaxe = tag(RegistryTagItem.pickaxe.get());
        IntrinsicTagAppender<Item> shovel = tag(RegistryTagItem.shovel.get());
        IntrinsicTagAppender<Item> axe = tag(RegistryTagItem.axe.get());
        IntrinsicTagAppender<Item> sword = tag(RegistryTagItem.sword.get());
        IntrinsicTagAppender<Item> hoe = tag(RegistryTagItem.hoe.get());
        IntrinsicTagAppender<Item> chest = tag(RegistryTagItem.chest.get());
        IntrinsicTagAppender<Item> helmet = tag(RegistryTagItem.helmet.get());
        IntrinsicTagAppender<Item> legging = tag(RegistryTagItem.legging.get());
        IntrinsicTagAppender<Item> boot = tag(RegistryTagItem.boot.get());
        IntrinsicTagAppender<Item> body = tag(RegistryTagItem.body.get());
        IntrinsicTagAppender<Item> nugget = tag(RegistryTagItem.nugget.get());
        IntrinsicTagAppender<Item> star = tag(RegistryTagItem.star.get());
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
        add(chest, ArmorItem.class, (appender, armorItem) -> {
            if(armorItem.getType().equals(ArmorItem.Type.CHESTPLATE)) {
                appender.add(armorItem);
            }
            return appender;
        });
        add(helmet, ArmorItem.class, (appender, armorItem) -> {
            if(armorItem.getType().equals(ArmorItem.Type.HELMET)) {
                appender.add(armorItem);
            }
            return appender;
        });
        add(legging, ArmorItem.class, (appender, armorItem) -> {
            if(armorItem.getType().equals(ArmorItem.Type.LEGGINGS)) {
                appender.add(armorItem);
            }
            return appender;
        });
        add(boot, ArmorItem.class, (appender, armorItem) -> {
            if(armorItem.getType().equals(ArmorItem.Type.BOOTS)) {
                appender.add(armorItem);
            }
            return appender;
        });
        add(body, ArmorItem.class, (appender, armorItem) -> {
            if(armorItem.getType().equals(ArmorItem.Type.BODY)) {
                appender.add(armorItem);
            }
            return appender;
        });
        nugget.add(Items.IRON_NUGGET, Items.GOLD_NUGGET);
        star.add(Items.NETHER_STAR);

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
