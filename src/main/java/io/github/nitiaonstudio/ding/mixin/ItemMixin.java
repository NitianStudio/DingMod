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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.math.BigInteger;
import java.util.List;

import static io.github.nitiaonstudio.ding.registry.ComponentRegistry.compression;
import static io.github.nitiaonstudio.ding.registry.TranslateKeyRegistry.forgeAnvilValue;

@Mixin(Item.class)
public class ItemMixin {
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

    @Inject(method = "getName", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void getCompressionName(ItemStack stack, CallbackInfoReturnable<Component> cir) {
        if (stack.has(compression)) {
            cir.setReturnValue(Component.empty().append(cir.getReturnValue()).append("x").append(stack.getOrDefault(compression, "0")));
        }
    }

}
