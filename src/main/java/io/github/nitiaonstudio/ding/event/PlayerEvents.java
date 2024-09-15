package io.github.nitiaonstudio.ding.event;

import io.github.nitiaonstudio.ding.base.block.ForgeAnvilBlock;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import io.github.nitiaonstudio.ding.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.function.Consumer;


public class PlayerEvents {

    @SubscribeEvent
    public void attackEntity(LivingDamageEvent.Pre event) {
        DamageContainer container = event.getContainer();
        DamageSource source = container.getSource();
        if (source.getDirectEntity() instanceof AbstractArrow) {
            return;
        }

        if (source.getEntity() instanceof LivingEntity livingEntity) {
            ItemStack stack = livingEntity.getMainHandItem();

            if (stack.has(ComponentRegistry.forgeAnvilValue)) {
                int orDefault = stack.getOrDefault(ComponentRegistry.forgeAnvilValue, 0);
                container.addModifier(DamageContainer.Reduction.ABSORPTION, (c, r) -> r + (float) orDefault / 100);
            }
        }

    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getMainHandItem();
        if (stack.has(ComponentRegistry.forgeAnvilValue) && stack.is(TagRegistry.Items.pickaxe.get()))
            event.setNewSpeed(event.getNewSpeed() + (float) stack.getOrDefault(ComponentRegistry.forgeAnvilValue, 0) / 100);
    }

    /*
    空手左键单机取出物品
     */
    @SubscribeEvent
    public void leftClickAnvilBlock(PlayerInteractEvent.LeftClickBlock event) {

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeAnvilTileEntity forgeAnvilTile) {
            ItemStack stack = forgeAnvilTile.getStack();
            if (!stack.isEmpty() && (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty())) {
                return;
            }
            player.addItem(stack);
            forgeAnvilTile.setStack(ItemStack.EMPTY);
            forgeAnvilTile.setMoveX(0);
            forgeAnvilTile.setToMoveX(0);
            forgeAnvilTile.setMoveZ(0);
            forgeAnvilTile.setToMoveZ(0);
            forgeAnvilTile.setRotateY(0);
            forgeAnvilTile.setToRotateY(0);
            forgeAnvilTile.setChanged();


        }
    }
    /*
    当方块是铁砧时，放置指定tag组的材料就会变成锻造砧
     */
    @SubscribeEvent
    public void rightClickAnvilBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof AnvilBlock) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            if (mainHandItem.isEmpty() && offhandItem.isEmpty()) return;
            if (TagRegistry.Items.forge_anvil_block_tags
                    .stream()
                    .map(TagRegistry.Items::get)
                    .anyMatch(mainHandItem::is)
            ) {
                ItemStack copy = mainHandItem.copy();
                copy.setCount(1);
                setForgeAnvilBlockEntity(level, pos, blockState, mainHandItem);
            } else if (TagRegistry.Items.forge_anvil_block_tags
                    .stream()
                    .map(TagRegistry.Items::get)
                    .anyMatch(offhandItem::is)
            ) {
                ItemStack copy = offhandItem.copy();
                copy.setCount(1);
                setForgeAnvilBlockEntity(level, pos, blockState, offhandItem);
            }
        }
    }

    /*
    当方块是锻造砧时
    砧内没物品
    把手上符合的物品塞入砧内
     */
    @SubscribeEvent
    public void rightClickForgeAnvilBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();

        BlockPos pos = event.getPos();


        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeAnvilTileEntity forgeAnvilTile) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            if (forgeAnvilTile.getStack().isEmpty()) {

                if (!mainHandItem.isEmpty() && TagRegistry.Items.forge_anvil_block_tags
                        .stream()
                        .map(TagRegistry.Items::get)
                        .anyMatch(mainHandItem::is)
                ) {
                    ItemStack copy = mainHandItem.copy();
                    copy.setCount(1);
                    setBlockEntity(mainHandItem, forgeAnvilTile, tile -> tile.setStack(copy));
                } else if (!offhandItem.isEmpty() && TagRegistry.Items.forge_anvil_block_tags
                        .stream()
                        .map(TagRegistry.Items::get)
                        .anyMatch(offhandItem::is)
                ) {
                    ItemStack copy = offhandItem.copy();
                    copy.setCount(1);
                    setBlockEntity(offhandItem, forgeAnvilTile, tile -> tile.setStack(copy));
                }
            }
        }

    }

    private void setForgeAnvilBlockEntity(Level level, BlockPos pos, BlockState blockState, ItemStack hand) {
        Direction value = blockState.getValue(AnvilBlock.FACING);
        BlockState state = BlockRegistry.forge_anvil_block.get().defaultBlockState();
        state.setValue(ForgeAnvilBlock.FACING, value);
        level.setBlockAndUpdate(pos, state);
        ItemStack copy = hand.copy();
        copy.setCount(1);
        ForgeAnvilTileEntity forgeAnvilTile = setBlockEntity(hand, new ForgeAnvilTileEntity(pos, state), tile -> tile.setStack(copy));
        level.setBlockEntity(forgeAnvilTile);
    }

    private <T extends BlockEntity> T setBlockEntity(ItemStack hand, T forgeAnvilTile, Consumer<T> consumer) {
        hand.setCount(hand.getCount() - 1);
        consumer.accept(forgeAnvilTile);
        forgeAnvilTile.setChanged();
        return forgeAnvilTile;
    }
}
