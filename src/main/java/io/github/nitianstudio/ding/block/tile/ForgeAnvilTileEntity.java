package io.github.nitianstudio.ding.block.tile;

import io.github.nitianstudio.ding.geo.DefaultBlockTile;
import io.github.nitianstudio.ding.registry.RegistryBlockTile;
import io.github.nitianstudio.ding.registry.RegistrySound;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

import static software.bernie.geckolib.animation.RawAnimation.*;

public class ForgeAnvilTileEntity extends DefaultBlockTile {


    private ItemStack stack = ItemStack.EMPTY;
    private int cd;//当cd为0的时候可以再次锻打

    public ForgeAnvilTileEntity(BlockPos pos, BlockState blockState) {
        super(RegistryBlockTile.forge_anvil_block.get(), pos, blockState);
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        Tag stack1 = tag.get("anvil_stack");
        if (stack1 != null) {
            ItemStack.parse(registries, stack1).ifPresent(itemStack -> stack = itemStack);
        }
        cd = tag.getInt("anvil_cd");
    }



    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (stack != ItemStack.EMPTY)
            tag.put("anvil_stack", stack.save(registries, tag));
        tag.putInt("anvil_cd", cd);
    }

    @Override
    protected void soundKeyFrameHandler(SoundKeyframeEvent<DefaultBlockTile> event) {
        BlockPos blockPos = getBlockPos();
        if (event.getKeyframeData().getSound().matches("walking")) {
            if (Objects.requireNonNull(getLevel()).isClientSide()) {
                getLevel().playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), RegistrySound.ding.get(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
            }
        }
    }

    @Override
    public PlayState state(AnimationState<DefaultBlockTile> state) {

        return state
                .setAndContinue(state.isMoving() ? begin().thenLoop("walking") : begin().thenLoop("idle"));
    }


}
