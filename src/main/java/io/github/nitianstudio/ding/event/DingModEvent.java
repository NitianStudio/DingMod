package io.github.nitianstudio.ding.event;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import static com.mojang.text2speech.Narrator.LOGGER;

public class DingModEvent {
    public DingModEvent(IEventBus modEventBus) {
        modEventBus.register(this);
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
    }
}
