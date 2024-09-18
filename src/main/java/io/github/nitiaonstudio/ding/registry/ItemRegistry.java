package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.base.item.ForgeHammer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static io.github.nitiaonstudio.ding.registry.AllRegistry.items;

public class ItemRegistry {
    public static final DeferredItem<ForgeHammer> forge_hammer = items.registerItem("forge_hammer", properties -> new ForgeHammer(properties.durability(20), "base", 380));
    public static final DeferredItem<ForgeHammer> forge_hammer_gold = items.registerItem("forge_hammer_gold", properties -> new ForgeHammer(properties.durability(40), "gold", 340));
    public static final DeferredItem<ForgeHammer> forge_hammer_copper = items.registerItem("forge_hammer_copper", properties -> new ForgeHammer(properties.durability(80), "copper", 240));
    public static final DeferredItem<ForgeHammer> forge_hammer_diamond = items.registerItem("forge_hammer_diamond", properties -> new ForgeHammer(properties.durability(320), "diamond", 120));
    public static final DeferredItem<ForgeHammer> forge_hammer_netherite = items.registerItem("forge_hammer_netherite", properties -> new ForgeHammer(properties.durability(640), "netherite", 30));
    public static void registry() {

    }
}
