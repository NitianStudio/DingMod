package io.github.nitiaonstudio.ding;

import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.Renders;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.value.Variable;

import static io.github.nitiaonstudio.ding.Ding.LOGGER;
import static io.github.nitiaonstudio.ding.Ding.MODID;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DingClient {
    public DingClient() {
        registry(
                Renders.forgeAnvilBlockRotateY,
                Renders.forgeAnvilBlockRotateToY,
                Renders.forgeAnvilBlockMoveX,
                Renders.forgeAnvilBlockMoveToX,
                Renders.forgeAnvilBlockMoveZ,
                Renders.forgeAnvilBlockMoveToZ
        );
    }

    public static void registry(String... names) {
        for (String name : names) {
            MathParser.registerVariable(new Variable(name, () -> 0));
        }
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockRegistry.BlockEntityRegistry.forge_anvil_block.get(), context -> Renders.forge_anvil_block_renderer);


    }
}
