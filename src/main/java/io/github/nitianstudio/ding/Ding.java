package io.github.nitianstudio.ding;

import com.mojang.logging.LogUtils;
import io.github.nitianstudio.ding.cfg.Config;
import io.github.nitianstudio.ding.event.DingMCEvent;
import io.github.nitianstudio.ding.event.DingModEvent;
import io.github.nitianstudio.ding.registry.AllRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Ding.MODID)
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
