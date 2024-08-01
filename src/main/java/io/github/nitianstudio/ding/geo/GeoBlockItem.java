package io.github.nitianstudio.ding.geo;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import static software.bernie.geckolib.animation.RawAnimation.begin;

public class GeoBlockItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public GeoBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers
                .add(new AnimationController<>(this, this.getDescriptionId() + this.hashCode(), 0,
                        event -> event
                                .setAndContinue(event.isMoving() ?
                                        begin().thenLoop("walking"):
                                        begin().thenLoop("idle")
                                ))
                );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
