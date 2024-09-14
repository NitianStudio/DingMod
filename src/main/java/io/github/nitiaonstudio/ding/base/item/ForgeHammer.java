package io.github.nitiaonstudio.ding.base.item;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.base.block.ForgeAnvilBlock;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import io.github.nitiaonstudio.ding.registry.SoundRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Random;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

import static software.bernie.geckolib.animation.RawAnimation.begin;


public class ForgeHammer extends Item implements GeoItem {
    public static final RandomSource randomSource = RandomSource.create();

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String material;
    private final int maxCd;
    public ForgeHammer(Properties properties, String material, int maxCd) {
        super(properties);
        this.material = material;
        this.maxCd = maxCd;
        GeoItem.registerSyncedAnimatable(this);
    }

    public PlayState predicate(AnimationState<ForgeHammer> state) {
        state.getController().setAnimation(RawAnimation.begin().then("rest", Animation.LoopType.LOOP));
        state.getController().triggerableAnim("run", begin().thenPlay("running"));

        return PlayState.CONTINUE;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        Integer orDefault = stack.getOrDefault(ComponentRegistry.cd, 0);
        if (orDefault > 0) {
            stack.set(ComponentRegistry.cd, --orDefault);
        }
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoItemRenderer<ForgeHammer> renderer;
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (renderer == null) {
                    renderer = new GeoItemRenderer<>(new GeoModel<>() {
                        @Override
                        public ResourceLocation getModelResource(ForgeHammer animatable) {
                            return Ding.id("geo/item/forge_hammer.geo.json");
                        }

                        @Override
                        public ResourceLocation getTextureResource(ForgeHammer animatable) {
                            return Ding.id("textures/item/forge_hammer/" + animatable.material + ".png");
                        }

                        @Override
                        public ResourceLocation getAnimationResource(ForgeHammer animatable) {
                            return Ding.id("animations/forge_hammer.animation.json");
                        }
                    });
                }
                return renderer;
            }
        });
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemInHand = context.getItemInHand();

        if (level instanceof ServerLevel serverLevel && itemInHand.getOrDefault(ComponentRegistry.cd, 0) <= 0) {
            itemInHand.set(ComponentRegistry.cd, maxCd);
            triggerAnim(player, GeoItem.getOrAssignId(itemInHand, serverLevel), "ForgeHammer", "run");
            BlockState blockState = serverLevel.getBlockState(clickedPos);
            BlockEntity blockEntity = serverLevel.getBlockEntity(clickedPos);
            if (blockState.is(BlockRegistry.forge_anvil_block) && blockEntity instanceof ForgeAnvilTileEntity tileEntity) {
                Ding.LOGGER.info("test");
                tileEntity.setMoveX(tileEntity.getToMoveX());
                tileEntity.setMoveZ(tileEntity.getToMoveZ());
                tileEntity.setRotateY(tileEntity.getToRotateY());
                tileEntity.setToMoveX(randomSource.triangle(-7, 7));
                tileEntity.setToMoveZ(randomSource.triangle(-7, 7));
                tileEntity.setRotateY(randomSource.triangle(0, 60));
                tileEntity.setHold(true);
                ItemStack stack = tileEntity.getStack();
                DataComponentType<Integer> doubleDataComponentType = ComponentRegistry.forgeAnvilValue.get();
                stack.set(doubleDataComponentType, stack.getOrDefault(doubleDataComponentType, 0) + 1);
                serverLevel.playLocalSound(clickedPos, SoundRegistry.ding.get(), SoundSource.MUSIC, 1F, 1.0F, true);
                tileEntity.setChanged();
                tileEntity.triggerAnim("defaultBlockTile", "run");

            }
        }
        return super.useOn(context);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "ForgeHammer", this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
