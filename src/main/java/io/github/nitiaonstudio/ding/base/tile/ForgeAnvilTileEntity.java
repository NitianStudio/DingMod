package io.github.nitiaonstudio.ding.base.tile;

import io.github.nitiaonstudio.ding.base.geo.DefaultBlockTile;
import io.github.nitiaonstudio.ding.base.property.object.Modes;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.SoundRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;

import java.util.function.Supplier;

import static io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity.Raws.rest;
import static io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity.Raws.running;
import static software.bernie.geckolib.animation.RawAnimation.begin;

@Getter
@Setter
public class ForgeAnvilTileEntity extends DefaultBlockTile<ForgeAnvilTileEntity> {


    public enum Raws implements Supplier<String> {
        rest,
        running;

        @Override
        public String get() {
            return name();
        }
    }


    private ItemStack stack = ItemStack.EMPTY;
    private int cd;//当cd为0的时候可以再次锻打
    private double rotateY, moveX, moveZ;
    private double toRotateY, toMoveX, toMoveZ;
    private Modes MODE = Modes.BASE;
    private Raws animationName = rest;

    public ForgeAnvilTileEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.BlockEntityRegistry.forge_anvil_block.get(), pos, blockState);
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
        rotateY = tag.getDouble("rotate_y");
        toRotateY = tag.getDouble("to_rotate_y");
        moveX = tag.getDouble("move_x");
        toMoveX = tag.getDouble("to_move_x");
        moveZ = tag.getDouble("move_z");
        toMoveZ = tag.getDouble("to_move_z");
        String mode = tag.getString("mode");
        MODE = mode.isEmpty() ? Modes.BASE : Modes.valueOf(mode);
        String animation_name = tag.getString("animation_name");
        animationName = animation_name.isEmpty() ? rest : Raws.valueOf(animation_name);
    }



    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (stack != ItemStack.EMPTY)
            tag.put("anvil_stack", stack.save(registries, tag));
        tag.putInt("anvil_cd", cd);
        tag.putDouble("rotate_y", rotateY);
        tag.putDouble("to_rotate_y", toRotateY);
        tag.putDouble("move_x", moveX);
        tag.putDouble("to_move_x", toMoveX);
        tag.putDouble("move_z", moveZ);
        tag.putDouble("to_move_z", toMoveZ);
        tag.putString("mode",MODE.name());
        tag.putString("animation_name", animationName.get());
    }

    @Override
    protected void soundKeyFrameHandler(SoundKeyframeEvent<DefaultBlockTile<ForgeAnvilTileEntity>> event) {
        BlockPos blockPos = getBlockPos();
        if (event.getKeyframeData().getSound().matches(running.get())) {
            if (level != null)
                if (level.isClientSide()) {
                    level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundRegistry.ding.get(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
                }
        }
    }

    @Override
    public void particleKeyframeHandler(ParticleKeyframeEvent<DefaultBlockTile<ForgeAnvilTileEntity>> event) {
        AnimationController.State animationState = event.getController().getAnimationState();
        if (animationState.equals(AnimationController.State.STOPPED)) {
            moveX = toMoveX;
            moveZ = toMoveZ;
            rotateY = toRotateY;
            animationName = rest;
            tick = 0;
            event.getController().setAnimation(set());
        }
    }

    @Override
    public PlayState state(AnimationState<DefaultBlockTile<ForgeAnvilTileEntity>> state) {

        return state
                .setAndContinue(set());
    }

    @Override
    public RawAnimation set() {
        RawAnimation begin = begin();
        if (animationName == rest) {
            begin.thenLoop(animationName.get());
        } else {
            begin.thenPlayAndHold(animationName.get());
        }
        return begin;
    }

}
