package io.github.nitiaonstudio.ding.data;

import net.minecraft.resources.ResourceLocation;

import java.awt.*;

@FunctionalInterface
public interface RLS {
    void factory(ResourceLocation id, Color... colors);
}
