package io.github.nitiaonstudio.ding.event;

import io.github.nitiaonstudio.ding.Ding;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static io.github.nitiaonstudio.ding.registry.ComponentRegistry.compression;
import static io.github.nitiaonstudio.ding.registry.ComponentRegistry.forgeAnvilValue;


public class CraftEvent {
    @SubscribeEvent
    public void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        ItemStack crafting = event.getCrafting();
        Container inventory = event.getInventory();
        int forgeAnvilValueNew = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            forgeAnvilValueNew = checkAdd(forgeAnvilValueNew, inventory
                    .getItem(i)
                    .getOrDefault(forgeAnvilValue, 0));
        }
        if (forgeAnvilValueNew >= 0) {
            forgeAnvilValueNew = checkAdd(forgeAnvilValueNew, crafting.getOrDefault(forgeAnvilValue, 0));

            forgeAnvilValueNew/=crafting.getCount();//产物多少平均分配

            crafting.set(forgeAnvilValue, forgeAnvilValueNew);
        }
    }

    public int checkAdd(int k, int v) {
        if (Integer.MAX_VALUE - k - v < 0) {
            return ifMax(Integer.MAX_VALUE);
        } else {
            return ifMax(k + v);
        }
    }

    public int ifMax(int value) {
        if (value >= Ding.dingConfig.get().base.max_forge_anvil_value) {
            return Ding.dingConfig.get().base.max_forge_anvil_value;
        }
        return value;
    }
}
