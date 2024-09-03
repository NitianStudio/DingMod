package io.github.nitiaonstudio.ding.data.lang;

import io.github.nitiaonstudio.ding.registry.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static io.github.nitiaonstudio.ding.Ding.MODID;

public class EnglishUnitedStatesOfAmerica extends LanguageProvider {
    public EnglishUnitedStatesOfAmerica(PackOutput output) {
        super(output, MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockRegistry.forge_anvil_block.get(), "forge anvil block");
//        add(RegistryGroup.ding.get().getDisplayName().getString(), "ding");
    }
}
