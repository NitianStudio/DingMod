package io.github.nitianstudio.ding.event;

import io.github.nitianstudio.ding.Const;
import io.github.nitianstudio.ding.block.ForgeAnvilBlock;
import io.github.nitianstudio.ding.item.ForgeIngot;
import io.github.nitianstudio.ding.registry.RegistryBlock;
import io.github.nitianstudio.ding.registry.RegistryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


public class DingMCEvent implements Const {
    public DingMCEvent(IEventBus forgeBus) {
        forgeBus.register(this);
    }
    @SubscribeEvent
    public void useBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getItemStack();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof AnvilBlock) {
            if (stack.getItem().equals(RegistryItem.forge_ingot.asItem())) {
                BlockState blockState1 = RegistryBlock.forge_anvil_block.get().defaultBlockState();
                blockState1.setValue(ForgeAnvilBlock.FACING, blockState.getValue(AnvilBlock.FACING));
                level.setBlockAndUpdate(pos, blockState1);
            }

        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
