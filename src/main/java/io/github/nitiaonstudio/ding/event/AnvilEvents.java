package io.github.nitiaonstudio.ding.event;

import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import io.github.nitiaonstudio.ding.registry.TagRegistry;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public class AnvilEvents {
//    @SubscribeEvent
//    public void onAnvilUpdate(AnvilUpdateEvent event) {//带打磨值的物品皆可修复带耐久的工具 700锻造值起
//        ItemStack left = event.getLeft();
//        ItemStack right = event.getRight();
//        if (right.has(ComponentRegistry.forgeAnvilValue) && left.isDamaged()) {
//            int orDefault = right.getOrDefault(ComponentRegistry.forgeAnvilValue, 0);
//
//            int count = right.getCount();
//            int i = orDefault / 700;
//            if (i == 0) {
//                return;
//            }
//            ItemStack copy = left.copy();
//            int damageValue = copy.getDamageValue();
//            int hasCount = damageValue / i;
//            if (hasCount > count) {
//                copy.setDamageValue(0);
//                event.setMaterialCost(count);
//                event.setCost(count);
//            } else {
//                copy.setDamageValue(copy.getDamageValue() - hasCount * i);
//                event.setMaterialCost(hasCount);
//                event.setCost(hasCount);
//            }
//            event.setOutput(copy);
//
//        }
//
//    }
}
