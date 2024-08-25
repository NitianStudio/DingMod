package io.github.nitianstudio.ding.function;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;

@FunctionalInterface
public interface MoLangInit<T extends GeoAnimatable> {
    void molang(AnimationState<T> animationState, double animTime);
}
