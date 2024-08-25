package io.github.nitianstudio.ding.geo;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Function;


public abstract class DefaultGeoItem<T extends DefaultGeoItem<T>> extends Item implements GeoItem {
    @SuppressWarnings("unchecked")
    private final Class<T> tClass = (Class<T>) this.getClass();
    private final Function<T, ResourceLocation> geo;
    private final Function<T, ResourceLocation> textures;
    private final Function<T, ResourceLocation> animations;
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public DefaultGeoItem(Properties properties, Function<T, ResourceLocation> geo, Function<T, ResourceLocation> textures, Function<T, ResourceLocation> animations) {
        super(properties);
        this.geo = geo;
        this.textures = textures;
        this.animations = animations;

    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private DefaultItemRender<T> render;
            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (render == null) {
                    render = new DefaultItemRender<>(tClass, geo, textures, animations);
                }
                return render;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
