package io.github.nitiaonstudio.ding.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import io.github.nitiaonstudio.ding.Ding;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static io.github.nitiaonstudio.ding.registry.ComponentRegistry.forgeAnvilValue;

@Pseudo
@Mixin(targets = "dev.dubhe.anvilcraft.item.MagnetItem")
@Debug(export = true)
public class MagnetMixin {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;<init>(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void use(@NotNull Level level,
                     @NotNull Player player,
                     @NotNull InteractionHand usedHand,
                     CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir,
                     ItemStack item,
                     @Local LocalDoubleRef radius) {

        if (item.has(forgeAnvilValue)) {
            int orDefault = item.getOrDefault(forgeAnvilValue, 0);
            radius.set(radius.get() + (double) orDefault / Ding.dingConfig.get().linkage.magnet_radius);
        }//联动,在new AABB之前注入
    }

}

