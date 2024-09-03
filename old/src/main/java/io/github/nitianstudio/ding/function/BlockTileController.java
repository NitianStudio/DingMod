package io.github.nitianstudio.ding.function;

import software.bernie.geckolib.animation.AnimatableManager;

@FunctionalInterface
public interface BlockTileController {
    void controller(AnimatableManager.ControllerRegistrar registrar);
}
