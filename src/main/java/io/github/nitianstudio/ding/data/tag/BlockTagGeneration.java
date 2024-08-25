package io.github.nitianstudio.ding.data.tag;

import io.github.nitianstudio.ding.Const;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public class BlockTagGeneration extends BlockTagsProvider implements Const {
    public BlockTagGeneration(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
