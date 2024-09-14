package io.github.nitiaonstudio.ding.base.tile;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.base.property.object.Modes;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Supplier;

import static io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity.Raws.rest;
import static io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity.Raws.running;
import static software.bernie.geckolib.animation.RawAnimation.begin;

@Getter
@Setter
public class ForgeAnvilTileEntity extends BlockEntity implements GeoBlockEntity {

    private ItemStack stack = ItemStack.EMPTY;
    private double rotateY, moveX, moveZ;
    private double toRotateY, toMoveX, toMoveZ;
    private Modes MODE = Modes.BASE;
    private boolean hold = false;

    public ForgeAnvilTileEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.BlockEntityRegistry.forge_anvil_block.get(), pos, blockState);

    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "ForgeAnvilBlock",0,  this::state)
                .setParticleKeyframeHandler(this::particleKeyframeHandler)
                .setSoundKeyframeHandler(this::soundKeyFrameHandler)
                .triggerableAnim("run", begin().thenPlay(running.get()))
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag,
                                  HolderLookup.@NotNull Provider registries) {

        super.loadAdditional(tag, registries);
        Tag stack1 = tag.get("anvil_stack");
        if (stack1 != null) {
            ItemStack.parse(registries, stack1).ifPresent(itemStack -> stack = itemStack);
        }

        rotateY = tag.getDouble("rotate_y");
        toRotateY = tag.getDouble("to_rotate_y");
        moveX = tag.getDouble("move_x");
        toMoveX = tag.getDouble("to_move_x");
        moveZ = tag.getDouble("move_z");
        toMoveZ = tag.getDouble("to_move_z");
        String mode = tag.getString("mode");
        MODE = mode.isEmpty() ? Modes.BASE : Modes.valueOf(mode);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {

        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (stack != ItemStack.EMPTY)
            tag.put("anvil_stack", stack.save(registries, tag));
        tag.putDouble("rotate_y", rotateY);
        tag.putDouble("to_rotate_y", toRotateY);
        tag.putDouble("move_x", moveX);
        tag.putDouble("to_move_x", toMoveX);
        tag.putDouble("move_z", moveZ);
        tag.putDouble("to_move_z", toMoveZ);
        tag.putString("mode",MODE.name());
    }


    protected void soundKeyFrameHandler(SoundKeyframeEvent<ForgeAnvilTileEntity> event) {

    }

    public void particleKeyframeHandler(ParticleKeyframeEvent<ForgeAnvilTileEntity> event) {

    }


    public PlayState state(AnimationState<ForgeAnvilTileEntity> state) {
        state.setAndContinue(state.isMoving() ? begin().thenPlayAndHold(running.get()) : begin().thenLoop(rest.get()));
        return PlayState.CONTINUE;
    }

    public enum Raws implements Supplier<String> {
        rest,
        running;

        @Override
        public String get() {
            return name();
        }
    }

}
