package io.github.nitianstudio.ding.item;

import io.github.nitianstudio.ding.geo.DefaultModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ItemRender extends GeoItemRenderer<ForgingHammer> {
    public ItemRender(GeoModel<ForgingHammer> model) {
        super(new DefaultModel<>(ForgingHammer.class));
    }
}
