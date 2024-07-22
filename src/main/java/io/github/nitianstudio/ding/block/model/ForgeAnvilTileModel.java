package io.github.nitianstudio.ding.block.model;

import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.resources.ResourceLocation;

import static io.github.nitianstudio.ding.Ding.MODID;

public class ForgeAnvilTileModel extends GeoModel<ForgeAnvilTileEntity> {
    private static final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(MODID, "geo/block/forge_anvil.geo.json");
    private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/forge_anvil.png");
    private static final ResourceLocation animation = ResourceLocation.fromNamespaceAndPath(MODID, "animations/forge_anvil.json");
    @Override
    public ResourceLocation getModelResource(ForgeAnvilTileEntity animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(ForgeAnvilTileEntity animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(ForgeAnvilTileEntity animatable) {
        return animation;
    }
}
