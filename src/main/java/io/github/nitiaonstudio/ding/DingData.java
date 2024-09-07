package io.github.nitiaonstudio.ding;

import io.github.nitiaonstudio.ding.data.ding.provider.DingLanguageProvider;
import io.github.nitiaonstudio.ding.data.ding.provider.ModelProvider;
import io.github.nitiaonstudio.ding.data.lang.Languages;
import io.github.nitiaonstudio.ding.data.ding.provider.TexturesProvider;
import io.github.nitiaonstudio.ding.data.tag.BlockTagGeneration;
import io.github.nitiaonstudio.ding.data.tag.ItemTagGeneration;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
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

        generator.addProvider(event.includeClient(), new DingLanguageProvider(packOutput, MODID)
                .languageSelect(Languages.en_us)
                .add(BlockRegistry.forge_anvil_block.get(), "forge anvil block")

                .languageSelect(Languages.zh_cn)
                .add(BlockRegistry.forge_anvil_block.get(), "锻造砧"));
        generator.addProvider(event.includeClient(), new ModelProvider(packOutput, MODID)
                .addGeckolibBlockModel(BlockRegistry.forge_anvil_block.get(), 256, 256));
//        generator.addProvider(event.includeClient(), new EnglishUnitedStatesOfAmerica(packOutput));
//        generator.addProvider(event.includeClient(), new SimpleChinese(packOutput));
        final var blocks = new BlockTagGeneration(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new ItemTagGeneration(packOutput, lookupProvider, existingFileHelper, blocks));


    }
}
