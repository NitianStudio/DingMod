package io.github.nitianstudio.ding;

import io.github.nitianstudio.ding.block.render.ForgeAnvilRender;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import io.github.nitianstudio.ding.registry.RegistryBlockTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.mojang.text2speech.Narrator.LOGGER;
import static io.github.nitianstudio.ding.Ding.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DingClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType<? extends ForgeAnvilTileEntity>) RegistryBlockTile.forge_anvil.get(), context -> new ForgeAnvilRender());
    }

}
