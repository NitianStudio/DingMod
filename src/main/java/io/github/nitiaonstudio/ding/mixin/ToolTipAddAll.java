package io.github.nitiaonstudio.ding.mixin;

import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static io.github.nitiaonstudio.ding.registry.TranslateKeyRegistry.forgeAnvilValue;

@Mixin(Item.class)
public class ToolTipAddAll {
    @Inject(method = "appendHoverText", at = @At("RETURN"))
    private void tooltip(ItemStack stack,
                         Item.TooltipContext context,
                         List<Component> tooltipComponents,
                         TooltipFlag tooltipFlag,
                         CallbackInfo ci) {
        if (stack.has(ComponentRegistry.forgeAnvilValue)) {
            tooltipComponents.add(Component.empty().append(forgeAnvilValue).append(String.valueOf(stack.get(ComponentRegistry.forgeAnvilValue))));
        }

    }

    @SuppressWarnings("resource")
    @Inject(method = "hurtEnemy", at = @At("RETURN"))
    private void hurtEnemy(ItemStack stack,
                           LivingEntity target,
                           LivingEntity attacker,
                           CallbackInfoReturnable<Boolean> cir) {
        if (stack.has(ComponentRegistry.forgeAnvilValue)) {
            Integer orDefault = stack.getOrDefault(ComponentRegistry.forgeAnvilValue, 0);
            target.hurt(target.level().damageSources().source(DamageTypes.CACTUS), (float) orDefault / 100);
        }
    }

}
