package io.github.nitiaonstudio.ding.data.resources.util;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

@ExtensionMethod({ Utils.class })
public class SwordBase extends AbstractBase {
    public SwordBase(ConcurrentHashMap<ResourceLocation, RBI> cmp) {
        super(cmp);
    }

    @Override
    public void factory(ResourceLocation id, Color... colors) {
        cmp.addResources(id, 19, 15, img -> {
            img
                    .color(colors[0])
                    .rec(0, 0, 1, 5)
                    .rec(1, 0, 1, 5)
                    .rec(2, 0, 1, 5)
                    .rec(4, 10, 2, 1)
                    .rec(16, 5, 3, 1)
                    .color(colors[1])
                    .rec(0, 5, 3, 1)
                    .rec(0, 11, 1, 3)
                    .rec(1, 6, 1, 2)
                    .rec(1, 11, 1, 3)
                    .rec(2, 6, 1, 2)
                    .rec(3, 6, 1, 2)
                    .rec(6, 6, 1, 2)
                    .rec(6, 10, 2, 1)
                    .rec(7, 6, 1, 2)
                    .rec(8, 6, 1, 2)
                    .rec(16, 0, 1, 5)
                    .rec(17, 0, 1, 5)
                    .rec(18, 0, 1, 5)
                    .color(colors[2])
                    .rec(0, 6, 1, 2)
                    .rec(0, 10, 4, 1)
                    .rec(0, 14, 2, 1)
                    .rec(2, 11, 7, 1)
                    .rec(4, 6, 1, 2)
                    .rec(5, 6, 1, 2)
                    .rec(6, 0, 1, 5)
                    .rec(7, 0, 1, 6)
                    .rec(8, 0, 1, 6)
                    .rec(8, 10, 1, 2)
                    .rec(9, 0, 1, 8)
                    .rec(9, 10, 2, 1)
                    .rec(10, 0, 1, 6)
                    .rec(10, 7, 1, 4)
                    .rec(11, 0, 1, 8)
                    .rec(12, 5, 1, 1)
                    .color(colors[3])
                    .rec(0, 8, 1, 2)
                    .rec(3, 8, 1, 2)
                    .rec(5, 9, 1, 1)
                    .rec(6, 8, 1, 1)
                    .rec(8, 9, 1, 1)
                    .rec(9, 8, 1, 1)
                    .rec(10, 6, 1, 1)
                    .color(colors[4])
                    .rec(1, 8, 1, 2)
                    .rec(6, 9, 1, 1)
                    .rec(8, 8, 1, 1)
                    .color(colors[5])
                    .rec(2, 8, 1, 2)
                    .rec(7, 8, 1, 2)
                    .color(colors[6])
                    .rec(3, 0, 1, 5)
                    .rec(4, 0, 1, 5)
                    .rec(5, 0, 1, 5)
                    .rec(13, 5, 3, 1)
                    .color(colors[7])
                    .rec(3, 5, 4, 1)
                    .rec(12, 0, 1, 5)
                    .rec(13, 0, 1, 5)
                    .rec(14, 0, 1, 5)
                    .rec(15, 0, 1, 5)
                    .color(colors[8])
                    .rec(4, 8, 1, 2)
                    .rec(5, 8, 1, 1)
                    .rec(9, 9, 1, 1)
                    .close();
        }, 60, 58, false, true);
    }
}
