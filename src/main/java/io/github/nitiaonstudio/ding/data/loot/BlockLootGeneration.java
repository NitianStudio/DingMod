package io.github.nitiaonstudio.ding.data.loot;

import io.github.nitiaonstudio.ding.registry.AllRegistry;
import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootGeneration extends BlockLootSubProvider {


    public BlockLootGeneration(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return AllRegistry.getBlocks().getEntries().stream().map(e -> (Block)e.value()).toList();
    }

    @Override
    protected void generate() {
        add(BlockRegistry.forge_anvil_block.get(), createSingleItemTable(Items.ANVIL));
    }
}
