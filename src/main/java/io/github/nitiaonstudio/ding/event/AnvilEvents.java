package io.github.nitiaonstudio.ding.event;

import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import io.github.nitiaonstudio.ding.registry.TagRegistry;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public class AnvilEvents {
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {//带打磨值的物品皆可修复带耐久的工具
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left.isDamaged()) {
            for (TagRegistry.Items tag : TagRegistry.Items.forge_anvil_block_tags) {
                if (right.is(tag.get())) {
                    int orDefault = right.getOrDefault(ComponentRegistry.forgeAnvilValue, 0);
                    ItemStack copy = left.copy();
                    int damageValue = copy.getDamageValue();
                    int count = right.getCount();
                    while (damageValue >= 0 && count > 0) {
                        damageValue-=orDefault / 1000;
                    }
                    copy.setDamageValue(damageValue);
                    event.setOutput(copy);
                    event.setMaterialCost(right.getCount() - count);
                    event.setCost((right.getCount() - count) * 3L);
                    break;
                }
            }
        }
    }
}
