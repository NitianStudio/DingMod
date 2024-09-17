package io.github.nitiaonstudio.ding.mixin.block;

import io.github.nitiaonstudio.ding.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.CrafterBlock;
import net.minecraft.world.level.block.entity.CrafterBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(CrafterBlock.class)
public class CraftBlockMixin {
    @Inject(method = "dispenseItem",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;Ljava/util/List;)V"),
    locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void trigger(ServerLevel level,
                         BlockPos pos,
                         CrafterBlockEntity crafter,
                         ItemStack stack,
                         BlockState state,
                         RecipeHolder<CraftingRecipe> recipe,
                         CallbackInfo ci,
                         Direction direction,
                         Container container,
                         ItemStack itemstack,
                         Vec3 vec3,
                         Vec3 vec31,
                         Iterator var12,
                         ServerPlayer serverplayer) {
        TriggerRegistry.component_geted_crafter.get().trigger(serverplayer, recipe.id(), crafter.getItems());
    }
}
