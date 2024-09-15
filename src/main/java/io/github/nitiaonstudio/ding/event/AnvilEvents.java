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
    @SuppressWarnings("resource")
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {//带打磨值的物品皆可修复带耐久的工具
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (right.has(ComponentRegistry.forgeAnvilValue) && left.isDamaged()) {
            int orDefault = right.getOrDefault(ComponentRegistry.forgeAnvilValue, 0);
            ItemStack copy = left.copy();
            int damageValue = copy.getDamageValue();
            int count = right.getCount();
            while (damageValue > 0 && count > 0) {
                damageValue-=orDefault / 1000;
                if (damageValue < 0) {
                    damageValue = 0;
                }
            }
            copy.setDamageValue(damageValue);
            event.setOutput(copy);
            event.setMaterialCost(right.getCount() - count);
            event.setCost((right.getCount() - count));
            Player player = event.getPlayer();
            Level level = player.level();
            player.hurt(level.damageSources().source(DamageTypes.PLAYER_ATTACK), 1);
        }

    }
}
