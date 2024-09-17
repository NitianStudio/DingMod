package io.github.nitiaonstudio.ding;

import io.github.nitiaonstudio.ding.config.DingConfig;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.RendersRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static io.github.nitiaonstudio.ding.Ding.LOGGER;
import static io.github.nitiaonstudio.ding.Ding.MODID;

@SuppressWarnings("unused")
@Mod(value = MODID,dist = Dist.CLIENT)
public class DingClient {
    public DingClient(IEventBus modBus, ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (c, s) -> AutoConfig.getConfigScreen(DingConfig.class, s).get()
        );
    }


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockRegistry.BlockEntityRegistry.forge_anvil_block.get(), context -> RendersRegistry.forge_anvil_block_renderer);


    }
}
