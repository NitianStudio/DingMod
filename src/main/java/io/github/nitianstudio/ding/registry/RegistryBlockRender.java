package io.github.nitianstudio.ding.registry;

import io.github.nitianstudio.ding.Const;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import io.github.nitianstudio.ding.function.MoLangInit;
import io.github.nitianstudio.ding.function.Molang;
import io.github.nitianstudio.ding.function.RenderBlockRegistry;
import io.github.nitianstudio.ding.geo.DefaultBlockRender;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.loading.math.MathParser;

import java.util.function.Function;
import java.util.function.Supplier;


public enum RegistryBlockRender implements Supplier<DefaultBlockRender<?>>, Molang {
    forge_anvil_block(
            ForgeAnvilTileEntity.class,
            (event, t) -> {
                event.registerBlockEntityRenderer(RegistryBlockTile.forge_anvil_block.get(), context -> t);
            },
            resource -> {
                return Const.id("geo/block/forge_anvil_block.geo.json");
            },
            resource -> {
                ItemStack stack = resource.getStack();
                for (var value : RegistryTagItem.values()) {
                    if (stack.is(value.get())) {
                        ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
                        return Const.id("textures/block/forge_anvil_block/" + key.getNamespace() + "/" + key.getPath() + ".png");
                    }
                }
                return Const.id("textures/block/forge_anvil_block/iron.png");
            },
            resource -> {
                return Const.id("animations/forge_anvil_block.animation.json");
            },
            (animationState, animTime) -> {
                ForgeAnvilTileEntity entity = animationState.getAnimatable();
                MathParser.setVariable(forgeAnvilBlockRotateY, entity::getRotateY);
                MathParser.setVariable(forgeAnvilBlockRotateRestY, entity::getRestRotateY);
                MathParser.setVariable(forgeAnvilBlockRotateToY, entity::getToRotateY);
                MathParser.setVariable(forgeAnvilBlockMoveX, entity::getMoveX);
                MathParser.setVariable(forgeAnvilBlockMoveRestX, entity::getRestMoveX);
                MathParser.setVariable(forgeAnvilBlockMoveToX, entity::getToMoveX);
                MathParser.setVariable(forgeAnvilBlockMoveZ, entity::getMoveZ);
                MathParser.setVariable(forgeAnvilBlockMoveRestZ, entity::getRestMoveZ);
                MathParser.setVariable(forgeAnvilBlockMoveToZ, entity::getToMoveZ);
            }
    ),
    ;
    private final DefaultBlockRender<?> blockRender;
    <T extends BlockEntity & GeoAnimatable> RegistryBlockRender(Class<T> clazz, RenderBlockRegistry<T> function, String... mta) {
        blockRender = new DefaultBlockRender<>(clazz,function, mta);
    }

    <T extends BlockEntity & GeoAnimatable> RegistryBlockRender(Class<T> clazz, RenderBlockRegistry<T> function, Function<T, ResourceLocation> geo, Function<T, ResourceLocation> textures, Function<T, ResourceLocation> animation) {
        blockRender = new DefaultBlockRender<>(clazz,function, geo, textures, animation);
    }

    <T extends BlockEntity & GeoAnimatable> RegistryBlockRender(Class<T> clazz, RenderBlockRegistry<T> function, Function<T, ResourceLocation> geo, Function<T, ResourceLocation> textures, Function<T, ResourceLocation> animation, MoLangInit<T> molang) {
        blockRender = new DefaultBlockRender<>(clazz,function, geo, textures, animation, molang);
    }



    @Override
    public DefaultBlockRender<?> get() {
        return blockRender;
    }
}
