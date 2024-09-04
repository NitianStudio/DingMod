package io.github.nitiaonstudio.ding.data.resources.util;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.RLS;
import io.github.nitiaonstudio.ding.data.resources.BIMG;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;

@ExtensionMethod({Utils.class})
public abstract class AbstractBase implements RLS {
    public final ConcurrentHashMap<ResourceLocation, RBI> cmp;
    public AbstractBase(ConcurrentHashMap<ResourceLocation, RBI> cmp) {
        this.cmp = cmp;
    }
}
