package io.github.nitianstudio.ding.data.lang;

import io.github.nitianstudio.ding.Const;
import io.github.nitianstudio.ding.registry.RegistryBlock;
import io.github.nitianstudio.ding.registry.RegistryGroup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SimpleChinese extends LanguageProvider implements Const {
    public SimpleChinese(PackOutput output) {
        super(output, MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(RegistryBlock.forge_anvil_block.get(), "锻造砧");
        add(RegistryGroup.ding.get().getDisplayName().getString(), "打铁匠人");
    }
}
