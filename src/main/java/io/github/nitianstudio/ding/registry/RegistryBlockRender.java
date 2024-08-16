package io.github.nitianstudio.ding.registry;

import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import io.github.nitianstudio.ding.function.RenderRegistry;
import io.github.nitianstudio.ding.geo.DefaultBlockRender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import software.bernie.geckolib.animatable.GeoAnimatable;

import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.Ding.MODID;


public enum RegistryBlockRender implements Supplier<DefaultBlockRender<?>> {
    forge_anvil_block(
            ForgeAnvilTileEntity.class,
            (event, t) -> {
                event.registerBlockEntityRenderer(RegistryBlockTile.forge_anvil_block.get(), context -> t);
            },
            resource -> {
                ItemStack stack = resource.getStack();

                return ResourceLocation.fromNamespaceAndPath(MODID,"geo/block/forge_anvil_block.geo.json");
            },
            resource -> {
//                Ingredient ingredient = Ingredient.of(RegistryTagItem.ingot.get());

                return ResourceLocation.fromNamespaceAndPath(MODID,"textures/block/forge_anvil_block.png");
            },
            resource -> {
                return ResourceLocation.fromNamespaceAndPath(MODID,"animations/forge_anvil_block.animation.json");
            }
    ),
    ;
    private final DefaultBlockRender<?> blockRender;
    <T extends BlockEntity & GeoAnimatable> RegistryBlockRender(Class<T> clazz, RenderRegistry<T> function, String... mta) {
        blockRender = new DefaultBlockRender<>(clazz,function, mta);
    }

    <T extends BlockEntity & GeoAnimatable> RegistryBlockRender(Class<T> clazz, RenderRegistry<T> function, Function<T, ResourceLocation> geo, Function<T, ResourceLocation> textures, Function<T, ResourceLocation> animation) {
        blockRender = new DefaultBlockRender<>(clazz,function, geo, textures, animation);
    }



    @Override
    public DefaultBlockRender<?> get() {
        return blockRender;
    }
}
