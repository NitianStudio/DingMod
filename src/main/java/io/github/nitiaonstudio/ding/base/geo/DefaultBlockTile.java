package io.github.nitiaonstudio.ding.base.geo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class DefaultBlockTile<T extends DefaultBlockTile<T>> extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected int tick = 0;

    public DefaultBlockTile(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public abstract PlayState state(AnimationState<DefaultBlockTile<T>> state);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(
                this,
                this.getBlockState().getBlock().getDescriptionId() + this.hashCode(),
                tick, this::state).setSoundKeyframeHandler(this::soundKeyFrameHandler).setParticleKeyframeHandler(this::particleKeyframeHandler));
    }

    public void particleKeyframeHandler(ParticleKeyframeEvent<DefaultBlockTile<T>> event) {

    }

    protected void soundKeyFrameHandler(SoundKeyframeEvent<DefaultBlockTile<T>> event) {
        tick = (int) event.getAnimationTick();
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        tick = tag.getInt("geo_tick");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("geo_tick", tick);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
