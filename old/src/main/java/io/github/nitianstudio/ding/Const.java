package io.github.nitianstudio.ding;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public interface Const {
    String MODID = "ding";
    Logger LOGGER = LogUtils.getLogger();

    static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
