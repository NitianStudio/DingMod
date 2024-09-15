package io.github.nitiaonstudio.ding.mixin;

import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

}
