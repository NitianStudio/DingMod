package io.github.nitiaonstudio.ding;

import com.mojang.logging.LogUtils;
import io.github.nitiaonstudio.ding.config.DingConfig;
import io.github.nitiaonstudio.ding.event.CraftEvent;
import io.github.nitiaonstudio.ding.event.PlayerEvents;
import io.github.nitiaonstudio.ding.registry.AllRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Ding.MODID)
public class Ding {

    public static final String MODID = "ding";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final AtomicReference<DingConfig> dingConfig = new AtomicReference<>();

    public Ding(IEventBus modEventBus, ModContainer modContainer) {
        dingConfig.set(AutoConfig.register(DingConfig.class, JanksonConfigSerializer::new).get());
        AllRegistry.registerAll(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PlayerEvents());
        NeoForge.EVENT_BUS.register(new CraftEvent());
//        NeoForge.EVENT_BUS.register(new AnvilEvents()); 无法实现
    }

    public static ResourceLocation id(String... paths) {
        return ResourceLocation.fromNamespaceAndPath(MODID, String.join("", paths));
    }

    public static Object loadRunLinkage(String modid, boolean newInstance, Object[] instanceArgs, String className, String methodNme, Object... args) {
        // example: anvilcraft

        if (ModList.get().getModFileById(modid) != null) {
            try {
                Class<?> aClass = Class.forName(className);
                if (newInstance) {
                    Constructor<?> constructor = aClass
                            .getConstructor(Arrays.stream(instanceArgs).map(Object::getClass).toArray(value -> new Class[0]));
                    Object o = constructor.newInstance(instanceArgs);
                    Method method = aClass.getDeclaredMethod(methodNme, Arrays.stream(args).map(Object::getClass).toArray(value -> new Class[0]));
                    method.setAccessible(true);
                    return method.invoke(o, args);
                } else {
                    Method method = aClass.getDeclaredMethod(methodNme, Arrays.stream(args).map(Object::getClass).toArray(value -> new Class[0]));
                    method.setAccessible(true);
                    return method.invoke(null, args);
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }

        }
        return null;
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

        LOGGER.info("HELLO from server starting");
    }
}
