package io.github.nitiaonstudio.ding.data.resources.util;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
@ExtensionMethod({Utils.class})
public class IngotBase extends AbstractBase {
    public IngotBase(ConcurrentHashMap<ResourceLocation, RBI> cmp) {
        super(cmp);
    }

    @Override
    public void factory(ResourceLocation id, Color... colors) {
        cmp.addResources(id, 13, 11, img -> {
            img
                    .color(colors[0])
                    .rec(0, 0, 1, 9)
                    .rec(1, 0, 1, 1)
                    .rec(1, 3, 1, 2)
                    .rec(1, 7, 1, 2)
                    .rec(4, 10, 4, 1)
                    .rec(7, 9, 1, 2)
                    .rec(8, 9, 1, 1)
                    .color(colors[1])
                    .rec(0, 9, 1, 3)
                    .rec(1, 9, 1, 3)
                    .rec(2, 9, 1, 3)
                    .rec(3, 9, 1, 3)
                    .rec(4, 11, 1, 1)
                    .rec(5, 0, 5, 1)
                    .rec(5, 3, 1, 2)
                    .rec(5, 7, 1, 2)
                    .rec(6, 3, 1, 2)
                    .rec(6, 7, 1, 2)
                    .rec(7, 3, 1, 2)
                    .rec(7, 7, 1, 2)
                    .rec(7, 11, 1, 1)
                    .rec(8, 3, 1, 2)
                    .rec(8, 7, 1, 2)
                    .rec(9, 2, 1, 1)
                    .rec(12, 0, 1, 1)
                    .rec(12, 2, 1, 1)
                    .color(colors[2])
                    .rec(1, 1, 1, 2)
                    .rec(1, 5, 1, 2)
                    .rec(7, 1, 1, 2)
                    .rec(7, 5, 1, 2)
                    .color(colors[3])
                    .rec(2, 0, 3, 1)
                    .color(colors[4])
                    .rec(2, 1, 1, 2)
                    .rec(2, 5, 1, 2)
                    .rec(3, 1, 1, 2)
                    .rec(3, 5, 1, 2)
                    .color(colors[5])
                    .rec(2, 3, 1, 2)
                    .rec(2, 7, 1, 2)
                    .rec(3, 3, 1, 2)
                    .rec(3, 7, 1, 2)
                    .rec(4, 3, 1, 2)
                    .rec(4, 7, 1, 3)
                    .rec(5, 9, 2, 1)
                    .color(colors[6])
                    .rec(4, 1, 1, 2)
                    .rec(4, 5, 1, 2)
                    .rec(5, 1, 1, 2)
                    .rec(5, 5, 1, 2)
                    .rec(6, 1, 1, 2)
                    .rec(6, 5, 1, 2)
                    .color(colors[7])
                    .rec(5, 11, 2, 1)
                    .rec(8, 1, 1, 2)
                    .rec(8, 5, 1, 2)
                    .rec(9, 4, 1, 4)
                    .rec(10, 0, 2, 1)
                    .rec(10, 2, 2, 1)
                    .rec(10, 4, 1, 4)
                    .color(colors[8])
                    .rec(9, 1, 4, 1)
                    .rec(9, 3, 4, 1)
                    .rec(9, 8, 1, 4)
                    .rec(10, 8, 1, 4)
                    .close();
        }, 0, 43, false, true);
    }
}
