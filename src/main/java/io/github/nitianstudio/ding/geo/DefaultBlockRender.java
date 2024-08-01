package io.github.nitianstudio.ding.geo;

import io.github.nitianstudio.ding.function.RenderRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.function.Function;

public class DefaultBlockRender<T extends BlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    public final RenderRegistry<T> function;
    public DefaultBlockRender(Class<T> clazz,RenderRegistry<T> function, String... mta) {
        super(new DefaultModel<>(clazz, mta));
        this.function = function;
    }

    public DefaultBlockRender(Class<T> clazz,RenderRegistry<T> function, Function<T, ResourceLocation> geo, Function<T, ResourceLocation> textures, Function<T, ResourceLocation> animation, String... mta) {
        super(new DefaultModel<>(clazz, mta) {
            @Override
            public ResourceLocation getModelResource(T animatable) {

                return geo.apply(animatable);
            }

            @Override
            public ResourceLocation getTextureResource(T animatable) {
                return textures.apply(animatable);
            }

            @Override
            public ResourceLocation getAnimationResource(T animatable) {
                return animation.apply(animatable);
            }
        });
        this.function = function;
    }

    public void event(EntityRenderersEvent.RegisterRenderers event) {
        function.event(event, this);
    }
}
