package io.github.nitianstudio.ding.geo;

import io.github.nitianstudio.ding.Const;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class DefaultModel<T extends GeoAnimatable> extends GeoModel<T> implements Const {
    private final ResourceLocation model;
    private static final ResourceLocation emptyModel = ResourceLocation.fromNamespaceAndPath(MODID, "geo/empty.json");
    private static final ResourceLocation emptyTexture = ResourceLocation.fromNamespaceAndPath(MODID, "textures/empty.png");
    private static final ResourceLocation emptyAnimation = ResourceLocation.fromNamespaceAndPath(MODID, "animations/empty.animation.png");
    private final ResourceLocation texture;
    private final ResourceLocation animation;
    public DefaultModel(Class<T> clazz, String... mta) {// model, texture, animation
        model = mta.length >= 1 && mta[0] != null ?
                ResourceLocation.fromNamespaceAndPath(MODID, mta[0])
                :emptyModel;
        texture = mta.length >= 2 && mta[1] != null ?
                ResourceLocation.fromNamespaceAndPath(MODID, mta[1])
                :emptyTexture;
        animation = mta.length >= 3 && mta[2] != null ?
                ResourceLocation.fromNamespaceAndPath(MODID, mta[2])
                :emptyAnimation;
    }

    public DefaultModel(Class<T> clazz) {// model, texture, animation
        model = emptyModel;
        texture = emptyTexture;
        animation = emptyAnimation;
    }
    @Override
    public ResourceLocation getModelResource(T animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }


}
