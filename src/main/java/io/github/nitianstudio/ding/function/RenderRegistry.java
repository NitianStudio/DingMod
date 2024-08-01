package io.github.nitianstudio.ding.function;

import io.github.nitianstudio.ding.geo.DefaultBlockRender;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.animatable.GeoAnimatable;

@FunctionalInterface
public interface RenderRegistry<T extends BlockEntity & GeoAnimatable> {
     void event(EntityRenderersEvent.RegisterRenderers event, DefaultBlockRender<T> t);
}
