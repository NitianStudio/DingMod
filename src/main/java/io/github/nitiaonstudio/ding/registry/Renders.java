package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.base.property.object.Modes;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.value.Variable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.Locale;

public class Renders {
    public static final String forgeAnvilBlockRotateY = "forge.anvil.block.rotate.y";
    public static final String forgeAnvilBlockRotateToY ="forge.anvil.block.rotate.to.y";
    public static final String forgeAnvilBlockMoveX = "forge.anvil.block.move.x";
    public static final String forgeAnvilBlockMoveToX = "forge.anvil.block.move.to.x";
    public static final String forgeAnvilBlockMoveZ = "forge.anvil.block.move.z";
    public static final String forgeAnvilBlockMoveToZ = "forge.anvil.block.move.to.z";
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

        @Override
        public void applyMolangQueries(AnimationState<ForgeAnvilTileEntity> animationState, double animTime) {
            ForgeAnvilTileEntity entity = animationState.getAnimatable();

            MathParser.setVariable(forgeAnvilBlockRotateY, entity::getRotateY);
            MathParser.setVariable(forgeAnvilBlockRotateToY, entity::getToRotateY);
            MathParser.setVariable(forgeAnvilBlockMoveX, entity::getMoveX);
            MathParser.setVariable(forgeAnvilBlockMoveToX, entity::getToMoveX);
            MathParser.setVariable(forgeAnvilBlockMoveZ, entity::getMoveZ);
            MathParser.setVariable(forgeAnvilBlockMoveToZ, entity::getToMoveZ);
            super.applyMolangQueries(animationState, animTime);

        }


    });
}
