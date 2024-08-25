package io.github.nitianstudio.ding.geo;

import io.github.nitianstudio.ding.function.MoLangInit;
import io.github.nitianstudio.ding.function.RenderBlockRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.function.Function;

public class DefaultBlockRender<T extends BlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    public final RenderBlockRegistry<T> function;
    public DefaultBlockRender(Class<T> clazz, RenderBlockRegistry<T> function, String... mta) {
        super(new DefaultModel<>(clazz, mta));
        this.function = function;
    }

    public DefaultBlockRender(Class<T> clazz, final RenderBlockRegistry<T> function, final Function<T, ResourceLocation> geo, final Function<T, ResourceLocation> textures, final Function<T, ResourceLocation> animation, String... mta) {
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

    public DefaultBlockRender(Class<T> clazz, final RenderBlockRegistry<T> function, final Function<T, ResourceLocation> geo, final Function<T, ResourceLocation> textures, final Function<T, ResourceLocation> animation, final MoLangInit<T> molang, String... mta) {
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

            @Override
            public void applyMolangQueries(AnimationState<T> animationState, double animTime) {
                super.applyMolangQueries(animationState, animTime);
                molang.molang(animationState, animTime);
            }
        });
        this.function = function;
    }

    public void event(EntityRenderersEvent.RegisterRenderers event) {
        function.event(event, this);
    }
}
