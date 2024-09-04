package io.github.nitiaonstudio.ding.data.resources.util;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
@ExtensionMethod({ Utils.class })
public class LozengeBase extends AbstractBase {
    public LozengeBase(ConcurrentHashMap<ResourceLocation, RBI> cmp) {
        super(cmp);
    }

    @Override
    public void factory(ResourceLocation id, Color... colors) {
        cmp.addResources(id, 12, 12, img -> {
            img
                    .color(colors[0])
                    .rec(0, 0, 1, 3)
                    .rec(0, 4, 1, 3)
                    .rec(1, 0, 2, 1)
                    .rec(1, 4, 2, 1)
                    .rec(8, 0, 1, 2)
                    .rec(9, 0, 1, 4)
                    .rec(10, 0, 1, 4)
                    .rec(11, 2, 1, 2)
                    .color(colors[1])
                    .rec(0, 3, 1, 1)
                    .rec(0, 7, 1, 3)
                    .rec(3, 0, 1, 1)
                    .rec(3, 4, 1, 1)
                    .rec(7, 8, 1, 2)
                    .rec(8, 2, 1, 2)
                    .rec(11, 0, 1, 2)
                    .color(colors[2])
                    .rec(0, 10, 1, 2)
                    .rec(1, 2, 1, 1)
                    .rec(1, 6, 1, 1)
                    .rec(1, 10, 1, 2)
                    .rec(2, 1, 1, 1)
                    .rec(2, 5, 1, 1)
                    .rec(2, 10, 1, 2)
                    .rec(3, 10, 1, 2)
                    .rec(4, 0, 1, 8)
                    .rec(5, 0, 1, 8)
                    .rec(6, 0, 1, 3)
                    .rec(6, 4, 1, 4)
                    .rec(7, 0, 1, 8)
                    .rec(8, 4, 1, 6)
                    .rec(9, 4, 1, 6)
                    .rec(10, 4, 2, 1)
                    .rec(10, 6, 1, 4)
                    .rec(11, 5, 1, 5)
                    .color(colors[3])
                    .rec(1, 1, 1, 1)
                    .rec(1, 5, 1, 1)
                    .color(colors[4])
                    .rec(1, 3, 2, 1)
                    .rec(1, 7, 1, 3)
                    .rec(2, 7, 1, 3)
                    .rec(3, 1, 1, 2)
                    .rec(3, 5, 1, 3)
                    .rec(5, 8, 1, 2)
                    .rec(6, 8, 1, 2)
                    .color(colors[5])
                    .rec(2, 2, 1, 1)
                    .rec(2, 6, 1, 1)
                    .color(colors[6])
                    .rec(3, 3, 1, 1)
                    .rec(3, 8, 1, 2)
                    .rec(4, 8, 1, 2)
                    .color(colors[7])
                    .rec(6, 3, 1, 1)
                    .rec(10, 5, 1, 1)
                    .close();
        }, 30, 43, false, true);
    }
}
