package io.github.nitianstudio.ding.registry;

import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import io.github.nitianstudio.ding.function.RenderRegistry;
import io.github.nitianstudio.ding.geo.DefaultBlockRender;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import software.bernie.geckolib.animatable.GeoAnimatable;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public enum RegistryBlockRender implements Supplier<DefaultBlockRender<?>> {
    forge_anvil_block(
            ForgeAnvilTileEntity.class,
            (event, t) -> {
                event.registerBlockEntityRenderer((BlockEntityType<? extends ForgeAnvilTileEntity>) RegistryBlockTile.forge_anvil_block.get(), context -> t);
            },
            "geo/block/forge_anvil_block.geo.json",
            "textures/block/forge_anvil_block.png",
            "animations/forge_anvil_block.animation.json"
    ),
    ;
    private final DefaultBlockRender<?> blockRender;
    <T extends BlockEntity & GeoAnimatable> RegistryBlockRender(Class<T> clazz, RenderRegistry<T> function, String... mta) {
        blockRender = new DefaultBlockRender<>(clazz,function, mta);
    }



    @Override
    public DefaultBlockRender<?> get() {
        return blockRender;
    }
}
