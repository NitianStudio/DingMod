package io.github.nitianstudio.ding;

import io.github.nitianstudio.ding.registry.RegistryBlockRender;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(modid = Const.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DingClient implements Const {
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
        for (RegistryBlockRender value : RegistryBlockRender.values()) {
            value.get().event(event);
        }
    }


}
