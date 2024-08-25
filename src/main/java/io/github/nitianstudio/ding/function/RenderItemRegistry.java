package io.github.nitianstudio.ding.function;

import io.github.nitianstudio.ding.geo.DefaultItemRender;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.animatable.GeoItem;

@FunctionalInterface
public interface RenderItemRegistry<T extends Item & GeoItem> {
    void event(EntityRenderersEvent.RegisterRenderers event, DefaultItemRender<T> t);
}
