package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.base.item.ForgeHammer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static io.github.nitiaonstudio.ding.registry.AllRegistry.items;

public class ItemRegistry {
    public static final DeferredItem<ForgeHammer> forge_hammer = items.registerItem("forge_hammer", properties -> new ForgeHammer(properties.durability(10), "base", 30));
    public static void registry() {

    }
}
