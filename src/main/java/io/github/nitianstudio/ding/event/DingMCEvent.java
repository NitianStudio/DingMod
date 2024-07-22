package io.github.nitianstudio.ding.event;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static com.mojang.text2speech.Narrator.LOGGER;

public class DingMCEvent {
    public DingMCEvent(IEventBus forgeBus) {
        forgeBus.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
