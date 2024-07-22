package io.github.nitianstudio.data.lang;

import io.github.nitianstudio.ding.registry.RegistryBlock;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static io.github.nitianstudio.ding.Ding.MODID;

public class SimpleChinese extends LanguageProvider {
    public SimpleChinese(PackOutput output) {
        super(output, MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(RegistryBlock.forge_anvil.get(), "锻造砧");
    }
}
