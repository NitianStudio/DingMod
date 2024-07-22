package io.github.nitianstudio.ding.block.render;

import io.github.nitianstudio.ding.block.model.ForgeAnvilTileModel;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import mod.azure.azurelib.common.api.client.renderer.GeoBlockRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ForgeAnvilRender extends GeoBlockRenderer<ForgeAnvilTileEntity> {

    public ForgeAnvilRender() {
        super(new ForgeAnvilTileModel());
    }
}
