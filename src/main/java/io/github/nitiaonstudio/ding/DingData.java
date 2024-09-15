package io.github.nitiaonstudio.ding;

import io.github.nitiaonstudio.ding.data.blockstate.BlockStateGson;
import io.github.nitiaonstudio.ding.data.ding.provider.*;
import io.github.nitiaonstudio.ding.data.lang.Languages;
import io.github.nitiaonstudio.ding.data.loot.BlockLootGeneration;
import io.github.nitiaonstudio.ding.data.models.Display;
import io.github.nitiaonstudio.ding.data.sounds.SoundGeneration;
import io.github.nitiaonstudio.ding.data.tag.BlockTagGeneration;
import io.github.nitiaonstudio.ding.data.tag.ItemTagGeneration;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import io.github.nitiaonstudio.ding.registry.ItemRegistry;
import io.github.nitiaonstudio.ding.registry.SoundRegistry;
import io.github.nitiaonstudio.ding.registry.TranslateKeyRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
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
                .add(BlockRegistry.forge_anvil_block, "forge anvil block")
                .add(SoundRegistry.ding, "ding~ding")
                .add(ItemRegistry.forge_hammer, "forge hammer")
                .add(TranslateKeyRegistry.forgeAnvilValue, "Forge Anvil Value: ")
                .add(TranslateKeyRegistry.destroyed, "[destroyed] ")

                .languageSelect(Languages.zh_cn)
                .add(BlockRegistry.forge_anvil_block, "锻造砧"))
                .add(SoundRegistry.ding, "叮~叮")
                .add(ItemRegistry.forge_hammer, "锻造锤")
                .add(TranslateKeyRegistry.forgeAnvilValue, "锻造数：")
                .add(TranslateKeyRegistry.destroyed, "【已损坏】 ")
        ;
        Display translation = new Display().rotation(55,0,0).translation(0, -5.25, -2);
        generator.addProvider(event.includeClient(), new ModelProvider(packOutput, MODID)
                .addGeckolibBlockModel(BlockRegistry.forge_anvil_block.get(), 256, 256, geckolibModel -> {})
                .addGeckolibItemModel(ItemRegistry.forge_hammer.get(), 32, 32, geckolibModel -> {
                    geckolibModel
                            .setThirdperson_righthand(translation)
                            .setFirstperson_lefthand(translation)
                            .setThirdperson_lefthand(translation)
                            .setFirstperson_righthand(translation)
                            .setGui(
                                    new Display()
                                            .translation(4.25, -5.75, 0)
                                            .scale(0.8, 0.8, 0.8)
                                            .rotation(0, 90, 0)
                            )
                            .setFixed(
                                    new Display()
                                            .rotation(0, -90, 0)
                                            .translation(-5.5, -6.75, 0)
                            );
                }));
        BlockStateGson.Variant variant = new BlockStateGson.Variant().setModel(Ding.id("block/forge_anvil_block"));

        generator.addProvider(event.includeClient(), new BlockStatesProvider(packOutput, MODID)
                .addBlockStates(BlockRegistry.forge_anvil_block.get(), new BlockStateGson()
                        .add("facing=east", variant.copy().setY(270))
                        .add("facing=south", variant.copy())
                        .add("facing=west", variant.copy().setY(90))
                        .add("facing=north", variant.copy().setY(180))));
        generator.addProvider(event.includeClient(), new SoundGeneration(packOutput, MODID, existingFileHelper));
        final var blocks = new BlockTagGeneration(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new DingRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ItemTagGeneration(packOutput, lookupProvider, existingFileHelper, blocks));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(BlockLootGeneration::new, LootContextParamSets.BLOCK)), lookupProvider));

    }
}
