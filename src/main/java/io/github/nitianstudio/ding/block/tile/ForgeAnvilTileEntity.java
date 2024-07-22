package io.github.nitianstudio.ding.block.tile;

import io.github.nitianstudio.ding.registry.RegistryBlockTile;
import io.github.nitianstudio.ding.registry.RegistrySound;
import mod.azure.azurelib.common.api.common.animatable.GeoBlockEntity;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class ForgeAnvilTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public ForgeAnvilTileEntity(BlockPos pos, BlockState blockState) {
        super(RegistryBlockTile.forge_anvil.get(), pos, blockState);
    }

    @Override
    public void registerControllers(mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar controllers) {
        BlockPos blockPos = getBlockPos();
        controllers
                .add(new AnimationController<>(this, "controllerName", 0,
                        event -> event
                                .setAndContinue(event.isMoving() ?
                                        RawAnimation.begin().thenLoop("walking"):
                                        RawAnimation.begin().thenLoop("idle"
                                        )))
                .setSoundKeyframeHandler(event -> {
                    if (event.getKeyframeData().getSound().matches("walk")) {
                        if (Objects.requireNonNull(getLevel()).isClientSide()) {

                            getLevel().playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), RegistrySound.ding.get(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
                        }
                    }
                }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}
