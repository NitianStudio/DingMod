package io.github.nitianstudio.ding;

import io.github.nitianstudio.ding.data.lang.EnglishUnitedStatesOfAmerica;
import io.github.nitianstudio.ding.data.lang.SimpleChinese;
import io.github.nitianstudio.ding.data.tag.BlockTagGeneration;
import io.github.nitianstudio.ding.data.tag.ItemTagGeneration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static io.github.nitianstudio.ding.Ding.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataDing {
    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new EnglishUnitedStatesOfAmerica(packOutput));
        generator.addProvider(event.includeClient(), new SimpleChinese(packOutput));
        final var blocks = new BlockTagGeneration(event);
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new ItemTagGeneration(event, blocks));
    }
}
