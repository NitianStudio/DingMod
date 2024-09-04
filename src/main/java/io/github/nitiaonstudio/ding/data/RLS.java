package io.github.nitiaonstudio.ding.data;

import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

@FunctionalInterface
public interface RLS {
    void factory(ResourceLocation id, Color... colors);
}
