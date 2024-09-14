package io.github.nitiaonstudio.ding.base.geo;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public abstract class DefaultBlockTile<T extends DefaultBlockTile<T>> extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected int tick = 0;

    public DefaultBlockTile(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public abstract PlayState state(AnimationState<DefaultBlockTile<T>> state);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(consumer(new AnimationController<>(
                this,
                "defaultBlockTile",
                tick, this::state).setSoundKeyframeHandler(this::soundKeyFrameHandler).setParticleKeyframeHandler(this::particleKeyframeHandler)));
    }

    public abstract AnimationController<DefaultBlockTile<T>> consumer(AnimationController<DefaultBlockTile<T>> defaultBlockTile);

    public void particleKeyframeHandler(ParticleKeyframeEvent<DefaultBlockTile<T>> event) {

    }

    protected void soundKeyFrameHandler(SoundKeyframeEvent<DefaultBlockTile<T>> event) {
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public abstract RawAnimation set();
}
