package io.github.nitiaonstudio.ding.base.item;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import io.github.nitiaonstudio.ding.registry.SoundRegistry;
import io.github.nitiaonstudio.ding.registry.TranslateKeyRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

import static io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity.Raws.rest;
import static io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity.Raws.running;
import static software.bernie.geckolib.animation.RawAnimation.begin;


public class ForgeHammer extends Item implements GeoItem {
    public final RandomSource randomSource = RandomSource.create();

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
        state.getController().setAnimation(state.isMoving() ? begin().thenPlay(running.get()) : begin().thenLoop(rest.get()));
        state.getController().triggerableAnim("run", begin().thenPlay("running"));

        return PlayState.CONTINUE;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        int orDefault = stack.getOrDefault(ComponentRegistry.cd, 0);
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
        int damageValue = itemInHand.getDamageValue();
        //是服务器世界， 手上物品cd<=0 耐久值不是满值
        if (level instanceof ServerLevel serverLevel && itemInHand.getOrDefault(ComponentRegistry.cd, 0) <= 0 && damageValue < itemInHand.getMaxDamage()) {
            itemInHand.set(ComponentRegistry.cd, maxCd);
            triggerAnim(player, GeoItem.getOrAssignId(itemInHand, serverLevel), "ForgeHammer_" + material, "run");
            BlockEntity blockEntity = serverLevel.getBlockEntity(clickedPos);

            if (blockEntity instanceof ForgeAnvilTileEntity tileEntity) {
                ItemStack stack = tileEntity.getStack();
                DataComponentType<Integer> doubleDataComponentType = ComponentRegistry.forgeAnvilValue.get();
                int orDefault = stack.getOrDefault(doubleDataComponentType, 0);
                if (orDefault <= 1000000000) {
                    stack.set(doubleDataComponentType, orDefault + 1);

                    serverLevel.playSound(null, clickedPos, SoundRegistry.ding.get(), SoundSource.VOICE, 1F, 1.0F);
                    tileEntity.triggerAnim("ForgeAnvilBlock", "run");
                    int orDefault1 = itemInHand.getOrDefault(doubleDataComponentType, 0);
                    if (orDefault1 >= 100 && itemInHand.getDamageValue() > 0) {//锤子大于等于100的时候启用修复功能, 每300锻造值增加1个修复点
                        itemInHand.setDamageValue(itemInHand.getDamageValue() - (orDefault1 % 300));
                    }
                    if (orDefault % 500 == 499) {
                        LightningBolt spawn = EntityType.LIGHTNING_BOLT.spawn(serverLevel, clickedPos, MobSpawnType.NATURAL);
                        if (spawn != null) {
                            serverLevel.addFreshEntity(spawn);
                            if (randomSource.nextInt(1, 12) % 3 == 0) {
                                tileEntity.setStack(ItemStack.EMPTY);
                                tileEntity.setChanged();
                            }
                        }

                    }

                    int i = damageValue + 1;
                    itemInHand.setDamageValue(i);
                }

            }
        }
        return super.useOn(context);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "ForgeHammer_" + material, this::predicate));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {

        MutableComponent append = Component.empty().append(super.getName(stack));
        if (stack.getDamageValue() == stack.getMaxDamage()) {
            return append.append(TranslateKeyRegistry.destroyed);
        }
        return append;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
