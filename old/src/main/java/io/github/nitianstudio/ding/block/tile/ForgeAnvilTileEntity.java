package io.github.nitianstudio.ding.block.tile;

import io.github.nitianstudio.ding.geo.DefaultBlockTile;
import io.github.nitianstudio.ding.registry.RegistryBlockTile;
import io.github.nitianstudio.ding.registry.RegistrySound;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;

import static software.bernie.geckolib.animation.RawAnimation.*;

@Getter
@Setter
public class ForgeAnvilTileEntity extends DefaultBlockTile {


    private ItemStack stack = ItemStack.EMPTY;
    private int cd;//当cd为0的时候可以再次锻打
    private double rotateY, moveX, moveZ;
    private double toRotateY, toMoveX, toMoveZ;

    public ForgeAnvilTileEntity(BlockPos pos, BlockState blockState) {
        super(RegistryBlockTile.forge_anvil_block.get(), pos, blockState);
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

    }

    @Override
    protected void soundKeyFrameHandler(SoundKeyframeEvent<DefaultBlockTile> event) {
        BlockPos blockPos = getBlockPos();
        if (event.getKeyframeData().getSound().matches("running")) {
            if (level != null)
                if (level.isClientSide()) {
                    level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), RegistrySound.ding.get(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
                }
        }
    }

    @Override
    public PlayState state(AnimationState<DefaultBlockTile> state) {
        return state
                .setAndContinue(state.isMoving() ? begin().thenLoop("walking") : begin().thenLoop("idle"));
    }

}
