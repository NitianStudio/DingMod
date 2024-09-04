package io.github.nitiaonstudio.ding.data.resources.util;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.experimental.ExtensionMethod;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
@ExtensionMethod({Utils.class})
public class PickaxeBase extends AbstractBase {
    public PickaxeBase(ConcurrentHashMap<ResourceLocation, RBI> cmp) {
        super(cmp);
    }

    @Override
    public void factory(ResourceLocation id, Color... colors) {
        cmp.addResources(id, 18, 15, img -> {
            img
                    .color(colors[0])
                    .rec(0, 0, 1, 4)
                    .rec(1, 1, 1, 1)
                    .rec(3, 10, 2, 1)
                    .rec(16, 0, 2, 1)
                    .rec(16, 2, 1, 2)
                    .rec(17, 1, 1, 3)
                    .color(colors[1])
                    .rec(0, 4, 1, 6)
                    .rec(0, 13, 1, 2)
                    .rec(1, 4, 1, 4)
                    .rec(1, 10, 1, 2)
                    .rec(2, 10, 1, 2)
                    .rec(5, 4, 1, 4)
                    .rec(6, 4, 1, 4)
                    .rec(7, 4, 1, 2)
                    .rec(7, 9, 1, 2)
                    .rec(9, 8, 1, 2)
                    .color(colors[2])
                    .rec(0, 10, 1, 1)
                    .rec(0, 12, 1, 1)
                    .rec(2, 4, 1, 4)
                    .rec(4, 4, 1, 4)
                    .rec(7, 6, 1, 1)
                    .rec(7, 8, 1, 1)
                    .color(colors[3])
                    .rec(0, 11, 1, 1)
                    .rec(3, 4, 1, 4)
                    .rec(7, 7, 1, 1)
                    .color(colors[4])
                    .rec(1, 0, 3, 1)
                    .rec(1, 2, 1, 2)
                    .rec(2, 1, 1, 3)
                    .rec(3, 1, 1, 3)
                    .rec(5, 1, 1, 1)
                    .rec(6, 0, 1, 1)
                    .rec(6, 2, 1, 2)
                    .rec(11, 1, 1, 1)
                    .rec(12, 0, 1, 1)
                    .rec(12, 2, 1, 2)
                    .rec(14, 0, 1, 4)
                    .rec(15, 0, 1, 4)
                    .rec(16, 1, 1, 1)
                    .color(colors[5])
                    .rec(1, 8, 2, 1)
                    .rec(2, 9, 4, 1)
                    .rec(5, 8, 1, 3)
                    .rec(6, 8, 1, 1)
                    .rec(8, 4, 1, 2)
                    .rec(8, 7, 1, 2)
                    .rec(9, 5, 1, 2)
                    .rec(10, 5, 1, 1)
                    .color(colors[6])
                    .rec(1, 9, 1, 1)
                    .rec(3, 8, 2, 1)
                    .rec(6, 9, 1, 2)
                    .rec(8, 6, 1, 1)
                    .rec(8, 9, 1, 1)
                    .rec(9, 4, 2, 1)
                    .rec(9, 7, 1, 1)
                    .color(colors[7])
                    .rec(4, 0, 1, 4)
                    .rec(7, 0, 1, 4)
                    .rec(8, 0, 1, 4)
                    .rec(9, 0, 1, 4)
                    .rec(10, 0, 1, 4)
                    .rec(13, 0, 1, 4)
                    .color(colors[8])
                    .rec(5, 0, 1, 1)
                    .rec(5, 2, 1, 2)
                    .rec(6, 1, 1, 1)
                    .rec(11, 0, 1, 1)
                    .rec(11, 2, 1, 2)
                    .rec(12, 1, 1, 1)
                    .close();
        }, 0, 58, false, true);
    }
}
