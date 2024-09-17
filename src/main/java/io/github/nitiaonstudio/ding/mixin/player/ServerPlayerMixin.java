package io.github.nitiaonstudio.ding.mixin.player;

import io.github.nitiaonstudio.ding.registry.TriggerRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "triggerRecipeCrafted", at = @At("HEAD"))
    private void trigger(RecipeHolder<?> recipe, List<ItemStack> items, CallbackInfo ci) {
        TriggerRegistry.component_geted.get().trigger((ServerPlayer) (Object) this, recipe.id(), items);
    }
}
