package io.github.nitiaonstudio.ding.data.resources.util;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
@ExtensionMethod({ Utils.class })
public class GemstoneBase extends AbstractBase {
    public GemstoneBase(ConcurrentHashMap<ResourceLocation, RBI> cmp) {
        super(cmp);
    }

    @Override
    public void factory(ResourceLocation id, Color... colors) {
        cmp.addResources(id, 14, 13, img -> {
            img
                    .color(colors[0])
                    .rec(0, 0, 1, 1)
                    .rec(4, 6, 1, 1)
                    .rec(7, 5, 1, 2)
                    .rec(10, 7, 1, 2)
                    .rec(11, 2, 1, 2)
                    .color(colors[1])
                    .rec(0, 1, 1, 1)
                    .rec(0, 5, 1, 1)
                    .color(colors[2])
                    .rec(0, 2, 2, 1)
                    .rec(0, 6, 2, 1)
                    .rec(1, 1, 1, 2)
                    .rec(1, 5, 1, 2)
                    .rec(8, 5, 1, 2)
                    .rec(9, 5, 1, 2)
                    .color(colors[3])
                    .rec(0, 3, 2, 1)
                    .rec(0, 7, 2, 1)
                    .rec(2, 8, 1, 2)
                    .rec(7, 3, 1, 2)
                    .rec(8, 3, 1, 2)
                    .rec(10, 5, 1, 2)
                    .color(colors[4])
                    .rec(0, 4, 3, 1)
                    .rec(1, 0, 2, 1)
                    .rec(4, 7, 1, 2)
                    .rec(5, 11, 1, 2)
                    .rec(7, 0, 1, 2)
                    .rec(8, 7, 1, 2)
                    .rec(9, 7, 1, 2)
                    .rec(12, 2, 1, 2)
                    .color(colors[5])
                    .rec(0, 8, 1, 2)
                    .rec(1, 8, 1, 2)
                    .rec(2, 3, 2, 1)
                    .rec(2, 7, 2, 1)
                    .rec(5, 1, 1, 2)
                    .rec(5, 4, 1, 2)
                    .rec(5, 9, 1, 2)
                    .rec(6, 1, 1, 2)
                    .rec(6, 4, 1, 2)
                    .rec(6, 9, 1, 2)
                    .rec(9, 3, 1, 2)
                    .rec(10, 0, 1, 2)
                    .rec(10, 3, 1, 2)
                    .rec(11, 0, 1, 2)
                    .color(colors[6])
                    .rec(0, 10, 1, 2)
                    .rec(1, 10, 1, 2)
                    .rec(2, 10, 1, 2)
                    .rec(3, 0, 4, 1)
                    .rec(3, 4, 2, 1)
                    .rec(3, 8, 1, 2)
                    .rec(4, 1, 1, 5)
                    .rec(4, 9, 1, 2)
                    .rec(5, 3, 2, 1)
                    .rec(7, 2, 1, 1)
                    .rec(7, 7, 1, 4)
                    .rec(8, 9, 1, 2)
                    .rec(9, 9, 1, 2)
                    .rec(10, 9, 1, 2)
                    .rec(12, 0, 1, 2)
                    .rec(13, 2, 1, 2)
                    .color(colors[7])
                    .rec(2, 1, 1, 2)
                    .rec(2, 5, 1, 2)
                    .rec(3, 1, 1, 2)
                    .rec(3, 5, 1, 2)
                    .color(colors[8])
                    .rec(3, 11, 1, 2)
                    .rec(4, 11, 1, 2)
                    .rec(5, 6, 1, 2)
                    .rec(6, 6, 1, 2)
                    .rec(8, 0, 1, 2)
                    .rec(9, 0, 1, 2)
                    .rec(12, 4, 1, 2)
                    .rec(13, 4, 1, 2)
                    .color(colors[9])
                    .rec(5, 8, 2, 1)
                    .rec(8, 2, 2, 1)
                    .rec(11, 4, 1, 2)
                    .rec(11, 9, 1, 2)
                    .rec(12, 9, 1, 2)
                    .color(colors[10])
                    .rec(37, 2, 1, 2)
                    .rec(38, 2, 1, 1)
                    .color(colors[11])
                    .rec(38, 3, 1, 1)
                    .close();
        }, 44, 43, false, true);
    }
}
