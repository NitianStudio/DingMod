package io.github.nitiaonstudio.ding.data.lang;

import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static io.github.nitiaonstudio.ding.Ding.MODID;

public class SimpleChinese extends LanguageProvider {
    public SimpleChinese(PackOutput output) {
        super(output, MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(BlockRegistry.forge_anvil_block.get(), "锻造砧");
//        add(RegistryGroup.ding.get().getDisplayName().getString(), "打铁匠人");
    }
}
