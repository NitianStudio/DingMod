package io.github.nitiaonstudio.ding;

import io.github.nitiaonstudio.ding.data.lang.EnglishUnitedStatesOfAmerica;
import io.github.nitiaonstudio.ding.data.lang.SimpleChinese;
import io.github.nitiaonstudio.ding.data.resources.TexturesProvider;
import io.github.nitiaonstudio.ding.data.tag.BlockTagGeneration;
import io.github.nitiaonstudio.ding.data.tag.ItemTagGeneration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static io.github.nitiaonstudio.ding.Ding.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class DingData {
    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new TexturesProvider(packOutput, MODID));
        generator.addProvider(event.includeClient(), new EnglishUnitedStatesOfAmerica(packOutput));
        generator.addProvider(event.includeClient(), new SimpleChinese(packOutput));
        final var blocks = new BlockTagGeneration(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new ItemTagGeneration(packOutput, lookupProvider, existingFileHelper, blocks));


    }
}
