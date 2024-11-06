package io.github.nitiaonstudio.ding.event;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.base.block.ForgeAnvilBlock;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.nitiaonstudio.ding.registry.ComponentRegistry.forgeAnvilValue;


public class PlayerEvents {
    @SubscribeEvent
    public void attackEntity(LivingDamageEvent.Pre event) {
        DamageContainer container = event.getContainer();
        DamageSource source = container.getSource();
        if (source.getEntity() instanceof LivingEntity livingEntity) {
            Entity directEntity = source.getDirectEntity();
            if (directEntity instanceof Projectile projectile) {
                switch (projectile) {
                    case AbstractArrow arrow -> {
                        if (arrow instanceof ThrownTrident thrownTrident) {
                            setDamage(thrownTrident.getPickupItemStackOrigin(), container, forgeAnvilValue);
                        } else {
                            mainHandOrOffHandIsClass(livingEntity, ProjectileWeaponItem.class, stack ->
                                    setDamage(stack, container, forgeAnvilValue));
                        } //三叉戟 光灵箭 箭
                    }
                    case AbstractHurtingProjectile hurtingProjectile -> {

                        if (hurtingProjectile instanceof ItemSupplier supplier) {//所有带ItemSupplier的都触发

                            setDamage(supplier.getItem(), container, forgeAnvilValue);
                        }
                    }
                    case FishingHook ignored -> mainHandOrOffHandIsClass(livingEntity, FishingRodItem.class, stack -> {
                        setDamage(stack, container, forgeAnvilValue);
                    });
                    case ThrowableProjectile throwableProjectile -> {
                        if (throwableProjectile instanceof ItemSupplier egg) {
                            setDamage(egg.getItem(), container, forgeAnvilValue);
                        }
                    }
                    default -> {
                    }
                }
            }
            else {
                setDamage(livingEntity.getMainHandItem(), container, forgeAnvilValue);

            }
        }

    }

    public <T, E extends LivingEntity> void mainHandOrOffHandIsClass(E e, Class<T> tClass, Consumer<ItemStack> consumer) {
        ItemStack mainHandItem = e.getMainHandItem();
        ItemStack offhandItem = e.getOffhandItem();
        if (mainHandItem.getItem().getClass().equals(tClass)) {
            consumer.accept(mainHandItem);
        } else if (offhandItem.getItem().getClass().equals(tClass)) {
            consumer.accept(offhandItem);
        }
    }

    public void setDamage(ItemStack stack,
                              DamageContainer container,
                              Supplier<DataComponentType<Integer>> supplier) {
        if (stack.has(supplier)) {
            int orDefault = stack.getOrDefault(supplier, 0);
            container.addModifier(DamageContainer.Reduction.ABSORPTION, (c, r) -> r + (float) orDefault / Ding.dingConfig.get().base.attack_factor);
        }
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getMainHandItem();
        if (stack.has(forgeAnvilValue) && stack.is(TagRegistry.Items.pickaxe.get()))
            event.setNewSpeed(event.getNewSpeed() + (float) stack.getOrDefault(forgeAnvilValue, 0) / Ding.dingConfig.get().base.mine_speed_factor);
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
        if (blockState.is(TagRegistry.Blocks.anvil.get())) {
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

    @SubscribeEvent//左键敲击带锻造值的锻造砧内物品消耗修复工具
    public void leftForgeAnvilBlockFixTool(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ForgeAnvilTileEntity tile) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack stack = tile.getStack();
            if (stack.has(forgeAnvilValue) && mainHandItem.isDamaged()) {
                int orDefault = stack.getOrDefault(forgeAnvilValue, 0);
                int i = orDefault / Ding.dingConfig.get().base.fix_all_factor;
                if (i == 0) return;
                int damageValue = mainHandItem.getDamageValue() - i;
                if (damageValue < 0) damageValue = 0;
                mainHandItem.setDamageValue(damageValue);
                tile.setStack(ItemStack.EMPTY);
                tile.setChanged();
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
