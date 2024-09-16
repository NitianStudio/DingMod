package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.Locale;

public class RendersRegistry {
    public static final String id = "geckolib.id";
    public static final GeoBlockRenderer<ForgeAnvilTileEntity> forge_anvil_block_renderer = new GeoBlockRenderer<>(new GeoModel<>() {

        @Override
        public ResourceLocation getModelResource(ForgeAnvilTileEntity animatable) {
            return Ding.id("geo/block/forge_anvil_block.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(ForgeAnvilTileEntity animatable) {
            var mode = animatable.getMODE().name().toLowerCase(Locale.ROOT);
            ItemStack stack = animatable.getStack();

            if (!stack.isEmpty()) {
                for (var value : TagRegistry.Items.values()) {
                    if (stack.is(value.get())) {
                        ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
                        return Ding.id("textures/block/forge_anvil_block/", key.getNamespace(), "/", mode, "_", key.getPath(), ".png");
                    }
                }
            }
            return Ding.id("textures/block/forge_anvil_block/", mode, ".png");
        }

        @Override
        public ResourceLocation getAnimationResource(ForgeAnvilTileEntity animatable) {
            return Ding.id("animations/forge_anvil_block.animation.json");
        }

    });
}
