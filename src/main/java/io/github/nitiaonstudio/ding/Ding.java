package io.github.nitiaonstudio.ding;

import com.mojang.logging.LogUtils;
import io.github.nitiaonstudio.ding.event.PlayerEvents;
import io.github.nitiaonstudio.ding.registry.AllRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Ding.MODID)
public class Ding {

    public static final String MODID = "ding";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Ding(IEventBus modEventBus, ModContainer modContainer) {
        AllRegistry.registerAll(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PlayerEvents());
//        NeoForge.EVENT_BUS.register(new AnvilEvents()); 无法实现
    }

    public static ResourceLocation id(String... paths) {
        return ResourceLocation.fromNamespaceAndPath(MODID, String.join("", paths));
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

        LOGGER.info("HELLO from server starting");
    }
}
