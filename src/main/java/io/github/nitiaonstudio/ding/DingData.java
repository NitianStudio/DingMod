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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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
                .add(ItemRegistry.forge_hammer_gold, "forge hammer[gold]")
                .add(ItemRegistry.forge_hammer_copper, "forge hammer[copper]")
                .add(TranslateKeyRegistry.forgeAnvilValue, "Forge Anvil Value: ")
                .add(TranslateKeyRegistry.mergeCobbleStone, "Surpassing the power of nature")
                .add(TranslateKeyRegistry.mergeCobbleStoneDesc, "Oh my God, this is not gonna happen")
                .add(TranslateKeyRegistry.destroyed, "[destroyed] ")
                .add("text.autoconfig.ding.option.linkage", "linkage")
                .add("text.autoconfig.ding.option.linkage.magnet_radius", "magnet radius factor")
                .add("text.autoconfig.ding.option.base", "base")
                .add("text.autoconfig.ding.option.base.fix_all_factor", "fix factor")
                .add("text.autoconfig.ding.option.base.mine_speed_factor", "mine speed factor")
                .add("text.autoconfig.ding.option.base.attack_factor", "attack factor")
                .add("text.autoconfig.ding.option.base.fix_in_forge_anvil_block_mod_factor", "fix in fab factor")
                .add("text.autoconfig.ding.option.base.min_fix_in_forge_anvil_block", "min fix in fab value")
                .add("text.autoconfig.ding.option.base.min_fix_self_in_forge_anvil_block", "min fix in fab random")
                .add("text.autoconfig.ding.option.base.thunderstorm_factor", "thunderstorm factor")
                .add("text.autoconfig.ding.option.base.max_forge_anvil_value", "max forge anvil value")

                .languageSelect(Languages.zh_cn)
                .add(BlockRegistry.forge_anvil_block, "锻造砧"))
                .add(SoundRegistry.ding, "叮~叮")
                .add(ItemRegistry.forge_hammer, "锻造锤")
                .add(ItemRegistry.forge_hammer_gold, "锻造锤【金】")
                .add(ItemRegistry.forge_hammer_copper, "锻造锤【铜】")
                .add(TranslateKeyRegistry.forgeAnvilValue, "锻造数：")
                .add(TranslateKeyRegistry.mergeCobbleStone, "超越自然的伟力")
                .add(TranslateKeyRegistry.mergeCobbleStoneDesc, "噢我的上帝，这不可能实现的")
                .add(TranslateKeyRegistry.destroyed, "【已损坏】 ")
                .add("text.autoconfig.ding.option.linkage", "联动")
                .add("text.autoconfig.ding.option.linkage.magnet_radius", "磁力增强半径系数")
                .add("text.autoconfig.ding.option.base", "基础")
                .add("text.autoconfig.ding.option.base.fix_all_factor", "修复系数")
                .add("text.autoconfig.ding.option.base.attack_factor", "攻击系数")
                .add("text.autoconfig.ding.option.base.mine_speed_factor", "挖掘系数")
                .add("text.autoconfig.ding.option.base.fix_in_forge_anvil_block_mod_factor", "锻造砧内修理系数")
                .add("text.autoconfig.ding.option.base.min_fix_in_forge_anvil_block", "锻造砧内最小修理值")
                .add("text.autoconfig.ding.option.base.min_fix_self_in_forge_anvil_block", "锻造时自我修复概率")
                .add("text.autoconfig.ding.option.base.thunderstorm_factor", "渡劫比例")
                .add("text.autoconfig.ding.option.base.max_forge_anvil_value", "最大锻造值")
        ;
        Display translation = new Display().rotation(55,0,0).translation(0, -5.25, -2);
        generator.addProvider(event.includeClient(), new ModelProvider(packOutput, MODID)
                .addGeckolibBlockModel(BlockRegistry.forge_anvil_block.get(), 256, 256, geckolibModel -> {})
                .addGeckolibItemModel(ItemRegistry.forge_hammer.get(), 32, 32, geckolibModel -> {}))
                .addGeckolibItemModel(ItemRegistry.forge_hammer_gold.get(), 32, 32, geckolibModel -> {});
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
        generator.addProvider(event.includeServer(), new DingAdvancementGeneration(packOutput, lookupProvider, existingFileHelper));
    }
}
