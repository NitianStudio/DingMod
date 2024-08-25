package io.github.nitianstudio.ding.item;

import io.github.nitianstudio.ding.geo.DefaultGeoItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimatableManager;

import java.util.function.Function;

public class ForgingHammer extends DefaultGeoItem<ForgingHammer> {
    public ForgingHammer(Properties props,
                         Function<ForgingHammer, ResourceLocation> geo,
                         Function<ForgingHammer, ResourceLocation> textures,
                         Function<ForgingHammer, ResourceLocation> animations) {
        super(props, geo, textures, animations);
    }//锻造锤



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }
}
