package io.github.nitianstudio.ding.function;

import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

@FunctionalInterface
public interface BlockTileController {
    void controller(AnimatableManager.ControllerRegistrar registrar);
}
