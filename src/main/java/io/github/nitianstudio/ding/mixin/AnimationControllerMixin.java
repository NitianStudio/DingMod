package io.github.nitianstudio.ding.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.animation.AnimationController;

@Mixin(AnimationController.class)
public interface AnimationControllerMixin {
    @Accessor("transitionLength")
    double getTransitionLength();

}
