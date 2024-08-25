package io.github.nitianstudio.ding.geo;

import io.github.nitianstudio.ding.function.MoLangInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Function;

public class DefaultItemRender<T extends Item & GeoItem> extends GeoItemRenderer<T> {
    public DefaultItemRender(Class<T> tClass,String... mta) {
        super(new DefaultModel<>(tClass, mta));
    }

    public DefaultItemRender(Class<T> tClass, final Function<T, ResourceLocation> geo, final Function<T, ResourceLocation> textures, final Function<T, ResourceLocation> animation, String... mta) {
        super(new DefaultModel<>(tClass, mta) {
            @Override
            public ResourceLocation getModelResource(T animatable) {
                return geo.apply(animatable);
            }

            @Override
            public ResourceLocation getAnimationResource(T animatable) {
                return animation.apply(animatable);
            }

            @Override
            public ResourceLocation getTextureResource(T animatable) {
                return textures.apply(animatable);
            }
        });
    }

    public DefaultItemRender(Class<T> tClass, final Function<T, ResourceLocation> geo, final Function<T, ResourceLocation> textures, final Function<T, ResourceLocation> animation, final MoLangInit<T> molang, String... mta) {
        super(new DefaultModel<>(tClass, mta) {
            @Override
            public ResourceLocation getModelResource(T animatable) {
                return geo.apply(animatable);
            }

            @Override
            public ResourceLocation getAnimationResource(T animatable) {
                return animation.apply(animatable);
            }

            @Override
            public ResourceLocation getTextureResource(T animatable) {
                return textures.apply(animatable);
            }

            @Override
            public void applyMolangQueries(AnimationState<T> animationState, double animTime) {
                super.applyMolangQueries(animationState, animTime);
                if (molang != null) {
                    molang.molang(animationState, animTime);
                }
            }
        });
    }
}
