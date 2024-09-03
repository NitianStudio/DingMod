package io.github.nitianstudio.ding;

import io.github.nitianstudio.ding.cfg.Config;
import io.github.nitianstudio.ding.event.DingMCEvent;
import io.github.nitianstudio.ding.event.DingModEvent;
import io.github.nitianstudio.ding.registry.AllRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Const.MODID)
public class Ding implements Const
{

    public Ding(IEventBus modEventBus, ModContainer modContainer)
    {
        AllRegistry.registry(modEventBus);
        new DingModEvent(modEventBus);
        new DingMCEvent(NeoForge.EVENT_BUS);
        Config.registry(modContainer);
    }
}
