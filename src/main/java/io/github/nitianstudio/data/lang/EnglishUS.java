package io.github.nitianstudio.data.lang;

import io.github.nitianstudio.ding.registry.RegistryBlock;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static io.github.nitianstudio.ding.Ding.MODID;

public class EnglishUS extends LanguageProvider {
    public EnglishUS(PackOutput output) {
        super(output, MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(RegistryBlock.forge_anvil.get(), "forge anvil block");
    }
}
