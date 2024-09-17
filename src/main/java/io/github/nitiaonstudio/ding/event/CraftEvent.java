package io.github.nitiaonstudio.ding.event;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static io.github.nitiaonstudio.ding.registry.ComponentRegistry.forgeAnvilValue;


public class CraftEvent {
    @SubscribeEvent
    public void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        ItemStack crafting = event.getCrafting();
        Container inventory = event.getInventory();
        int forgeAnvilValueNew = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {

            forgeAnvilValueNew += inventory
                    .getItem(i)
                    .getOrDefault(forgeAnvilValue, 0);
        }
        if (forgeAnvilValueNew >= 0) {
            forgeAnvilValueNew += crafting.getOrDefault(forgeAnvilValue, 0);
            forgeAnvilValueNew/=crafting.getCount();
            crafting.set(forgeAnvilValue, forgeAnvilValueNew);
        }
    }
}
