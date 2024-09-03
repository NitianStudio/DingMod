package io.github.nitiaonstudio.ding.data.resources;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.RLS;
import io.github.nitiaonstudio.ding.data.XY;
import io.github.nitiaonstudio.ding.data.XyWh;
import lombok.Getter;
import lombok.val;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TexturesProvider implements DataProvider {

    private final PackOutput output;
    private final String modid;

    private final LinkedHashMap<Path, RBI> cmp = new LinkedHashMap<>();
    @Getter
    private final LinkedHashMap<Path, BIMG> images = new LinkedHashMap<>();
    private final LinkedList<ResourceLocation> forge_anvil_kind = new LinkedList<>();
    private final LinkedList<ResourceLocation> lozenge_kind = new LinkedList<>();
    private final LinkedList<ResourceLocation> gemstone_kind = new LinkedList<>();
    private final LinkedList<ResourceLocation> ingot_kind = new LinkedList<>();

    public TexturesProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }


    public final void add(String prefix, String suffix, RLS rls, Map<ResourceLocation, Color[]> locations) {
        for (Map.Entry<ResourceLocation, Color[]> entry : locations.entrySet()) {
            ResourceLocation first = entry.getKey();
            rls.factory(first.withPrefix(prefix.replace("${namespace}", first.getNamespace())).withSuffix(suffix), entry.getValue());
        }
    }

    public void apply() {
        preGeneration("/assets/ding/textures/block/forge_anvil_block/minecraft/emerald_get.png");
        add("generation/block/forge_anvil_block/", ".png", this::colorForgeAnvilBlockBase, Map.of(
                ResourceLocation.fromNamespaceAndPath(ResourceLocation.DEFAULT_NAMESPACE, "base"), new Color[]{
                        new Color(4, 4, 5, 255),
                        new Color(14, 12, 15, 255),
                        new Color(25, 23, 27, 255),
                        new Color(47, 44, 50, 255),
                        new Color(62, 59, 65, 255),
                        new Color(17, 16, 19, 255),
                        new Color(76, 75, 81, 255),
                        new Color(28, 26, 30, 255),
                        new Color(40, 38, 42, 255),
                        new Color(55, 53, 58, 255),
                        new Color(27, 25, 29, 255),
                }));

        add("generation/block/forge_anvil_block/${namespace}/", ".png", this::colorIngotBase, Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_INGOT), new Color[] {
                        new Color(197, 197, 197, 255),
                        new Color(136, 136, 136, 255),
                        new Color(48, 49, 54, 255),
                        new Color(154, 154, 154, 255),
                        new Color(59, 60, 63, 255),
                        new Color(161, 161, 161, 255),
                        new Color(87, 87, 87, 255),
                        new Color(28, 27, 32, 255),
                        new Color(107, 104, 104, 255),
                },
                BuiltInRegistries.ITEM.getKey(Items.COPPER_INGOT), new Color[]{
                        new Color(254, 248, 214, 255),
                        new Color(237, 181, 94, 255),
                        new Color(82, 27, 9, 255),
                        new Color(255, 221, 129, 255),
                        new Color(144, 59, 32, 255),
                        new Color(255, 221, 129, 255),
                        new Color(186, 102, 53, 255),
                        new Color(28, 27, 32, 255),
                        new Color(219, 126, 73, 255),
                }
        ));
        add("generation/block/forge_anvil_block/${namespace}/", ".png", this::colorLozengeBase, Map.of(
                BuiltInRegistries.ITEM.getKey(Items.DIAMOND), new Color[] {
                        new Color(20, 55, 105, 255),
                        new Color(49, 95, 161, 255),
                        new Color(161, 228, 250, 255),
                        new Color(255, 255, 255, 255),
                        new Color(198, 241, 255, 255),
                        new Color(106, 167, 209, 255),
                        new Color(225, 248, 255, 255),
                        new Color(234, 250, 255, 255),
                }
        ));
        add("generation/block/forge_anvil_block/${namespace}/", ".png", this::colorGemstoneBase, Map.of(
                BuiltInRegistries.ITEM.getKey(Items.EMERALD), new Color[]{
                        new Color(11, 38, 30, 255),
                        new Color(255, 255, 255, 255),
                        new Color(117, 186, 133, 255),
                        new Color(171, 239, 187, 255),
                        new Color(17, 50, 33, 255),
                        new Color(222, 255, 230, 255),
                        new Color(29, 85, 42, 255),
                        new Color(88, 149, 115, 255),
                        new Color(84, 156, 107, 255),
                        new Color(38, 99, 53, 255),
                        new Color(40, 38, 42, 255),
                        new Color(27, 25, 29, 255),
                }
        ));
        for (ResourceLocation resourceLocation : forge_anvil_kind) {
            String[] split = resourceLocation.getPath().split("/");
            String s = split[split.length - 1];
            add("block/forge_anvil_block/%s".formatted(s), 128, 128, img -> {
                sameCode(resourceLocation, img)
                        .close();
            }, null);
            addSubImage(s, resourceLocation, ingot_kind, 0, 43);
            addSubImage(s, resourceLocation,lozenge_kind, 30, 43);
            addSubImage(s, resourceLocation, gemstone_kind, 44, 43);
        }
    }

    public void addSubImage(String s,ResourceLocation resourceLocation, LinkedList<ResourceLocation> list, int insertX, int insertY) {
        for (ResourceLocation location : list) {
            var split = location.getPath().split("/");
            var s1 = split[split.length - 1];
            add("block/forge_anvil_block/%s/%s_%s".formatted(location.getNamespace(), s.replace(".png", ""), s1), 128, 128, img -> {
                sameCode(resourceLocation, img)
                        .insertImage(getImages().get(get(location)), insertX, insertY)
                        .close();
            }, null);
        }
    }

    private BIMG sameCode(ResourceLocation location, BIMG img) {
        return img.insertImage(getImages().get(get(location)), 0, 0);
    }

    public void colorGemstoneBase(ResourceLocation path, Color... colors) {
        addResources(path, 14, 13, img ->
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
                        .close(), gemstone_kind);
    }

    public void colorLozengeBase(ResourceLocation path, Color... colors) {
        addResources(path, 12, 12, img -> img
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
                .close(), lozenge_kind);
    }

    public void colorIngotBase(ResourceLocation path, Color... colors) {
        addResources(path, 13, 11, img -> img
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
                .close(), ingot_kind);
    }

    public void colorForgeAnvilBlockBase(ResourceLocation path, Color... colors) {
        addResources(path, 39, 31, img -> img
            .color(colors[0])
            .rec(0, 2, 1, 2)
            .rec(1, 2, 1, 3)
            .rec(2, 0, 1, 5)
            .rec(3, 0, 1, 4)
            .rec(3, 5, 1, 1)
            .rec(4, 0, 1, 5)
            .rec(4, 6, 1, 1)
            .rec(5, 2, 1, 2)
            .rec(6, 2, 1, 3)
            .rec(7, 2, 1, 2)
            .rec(7, 5, 1, 1)
            .rec(8, 2, 1, 3)
            .rec(9, 2, 1, 4)
            .rec(11, 30, 8, 1)
            .rec(14, 23, 1, 2)
            .rec(15, 23, 1, 3)
            .rec(16, 25, 2, 1)
            .rec(20, 17, 1, 2)
            .rec(21, 17, 1, 2)
            .rec(22, 10, 1, 1)
            .rec(22, 17, 1, 2)
            .rec(23, 17, 1, 2)
            .rec(26, 23, 1, 2)
            .rec(27, 23, 1, 2)
            .rec(28, 10, 1, 1)
            .rec(28, 23, 1, 2)
            .rec(29, 23, 1, 2)
            .color(colors[1])
            .rec(0, 4, 1, 3)
            .rec(1, 5, 2, 1)
            .rec(1, 7, 1, 1)
            .rec(2, 23, 5, 1)
            .rec(2, 25, 5, 1)
            .rec(3, 4, 1, 1)
            .rec(3, 6, 1, 1)
            .rec(3, 22, 1, 2)
            .rec(4, 5, 3, 1)
            .rec(4, 7, 1, 1)
            .rec(4, 22, 1, 2)
            .rec(5, 4, 1, 2)
            .rec(5, 22, 1, 2)
            .rec(6, 6, 2, 1)
            .rec(6, 22, 1, 2)
            .rec(7, 4, 1, 1)
            .rec(7, 22, 2, 1)
            .rec(8, 5, 1, 1)
            .rec(9, 6, 1, 1)
            .rec(11, 29, 8, 1)
            .rec(16, 23, 2, 1)
            .rec(20, 19, 2, 1)
            .rec(20, 21, 1, 1)
            .rec(21, 10, 1, 1)
            .rec(21, 20, 1, 1)
            .rec(24, 23, 2, 1)
            .rec(29, 10, 1, 1)
            .color(colors[2])
            .rec(0, 7, 1, 1)
            .rec(0, 23, 1, 3)
            .rec(1, 6, 2, 1)
            .rec(1, 23, 1, 3)
            .rec(2, 7, 2, 1)
            .rec(2, 24, 10, 1)
            .rec(3, 20, 1, 2)
            .rec(4, 20, 1, 2)
            .rec(5, 0, 1, 2)
            .rec(5, 6, 1, 2)
            .rec(5, 20, 1, 2)
            .rec(6, 0, 1, 2)
            .rec(6, 7, 4, 1)
            .rec(6, 20, 1, 2)
            .rec(7, 0, 1, 2)
            .rec(7, 14, 2, 1)
            .rec(7, 16, 1, 1)
            .rec(7, 20, 1, 2)
            .rec(7, 23, 1, 3)
            .rec(8, 6, 1, 2)
            .rec(8, 15, 1, 1)
            .rec(8, 20, 1, 2)
            .rec(8, 23, 1, 3)
            .rec(9, 12, 3, 1)
            .rec(9, 23, 1, 3)
            .rec(10, 23, 1, 3)
            .rec(11, 14, 1, 2)
            .rec(11, 23, 1, 3)
            .rec(12, 1, 2, 1)
            .rec(12, 3, 1, 1)
            .rec(12, 14, 1, 1)
            .rec(13, 2, 1, 1)
            .rec(14, 12, 3, 1)
            .rec(15, 9, 1, 2)
            .rec(18, 9, 1, 2)
            .rec(19, 10, 2, 1)
            .rec(25, 4, 1, 1)
            .rec(25, 7, 1, 1)
            .rec(27, 4, 3, 1)
            .rec(27, 7, 3, 1)
            .rec(30, 10, 2, 1)
            .color(colors[3])
            .rec(0, 11, 1, 1)
            .rec(0, 19, 1, 1)
            .rec(5, 19, 2, 1)
            .rec(6, 13, 1, 1)
            .rec(6, 18, 1, 2)
            .rec(9, 13, 2, 1)
            .rec(9, 18, 1, 2)
            .rec(10, 18, 1, 2)
            .rec(11, 0, 1, 1)
            .rec(13, 13, 1, 1)
            .rec(13, 18, 1, 1)
            .rec(14, 0, 1, 1)
            .rec(15, 19, 2, 1)
            .rec(19, 19, 1, 1)
            .rec(23, 9, 2, 1)
            .rec(24, 8, 1, 2)
            .rec(25, 11, 2, 1)
            .rec(26, 8, 1, 2)
            .rec(27, 9, 1, 1)
            .rec(29, 11, 1, 1)
            .color(colors[4])
            .rec(0, 12, 1, 1)
            .rec(1, 11, 1, 1)
            .rec(1, 19, 1, 1)
            .rec(4, 19, 1, 1)
            .rec(6, 14, 1, 1)
            .rec(6, 17, 1, 1)
            .rec(7, 13, 2, 1)
            .rec(7, 18, 1, 2)
            .rec(8, 18, 1, 2)
            .rec(9, 14, 2, 1)
            .rec(9, 17, 2, 1)
            .rec(11, 1, 1, 1)
            .rec(11, 13, 2, 1)
            .rec(11, 18, 1, 2)
            .rec(12, 0, 2, 1)
            .rec(12, 18, 1, 1)
            .rec(13, 14, 1, 1)
            .rec(13, 17, 1, 1)
            .rec(14, 1, 1, 1)
            .rec(14, 19, 1, 1)
            .rec(16, 24, 2, 1)
            .rec(17, 19, 2, 1)
            .rec(19, 9, 1, 1)
            .rec(21, 9, 2, 1)
            .rec(22, 19, 1, 2)
            .rec(23, 10, 5, 1)
            .rec(23, 19, 1, 2)
            .rec(24, 4, 1, 1)
            .rec(24, 6, 1, 2)
            .rec(24, 11, 1, 1)
            .rec(24, 20, 1, 2)
            .rec(24, 24, 2, 1)
            .rec(25, 8, 1, 3)
            .rec(25, 12, 2, 1)
            .rec(25, 20, 1, 1)
            .rec(26, 4, 1, 1)
            .rec(26, 6, 1, 2)
            .rec(27, 8, 3, 1)
            .rec(27, 11, 2, 1)
            .rec(27, 17, 1, 2)
            .rec(28, 9, 2, 1)
            .rec(28, 17, 1, 2)
            .rec(29, 12, 1, 1)
            .rec(30, 20, 4, 1)
            .rec(31, 9, 1, 1)
            .rec(31, 21, 3, 1)
            .color(colors[5])
            .rec(1, 12, 1, 1)
            .rec(23, 12, 1, 1)
            .color(colors[6])
            .rec(2, 11, 10, 1)
            .rec(2, 19, 2, 1)
            .rec(6, 15, 1, 2)
            .rec(9, 15, 1, 2)
            .rec(10, 15, 1, 2)
            .rec(11, 2, 1, 10)
            .rec(12, 19, 2, 1)
            .rec(13, 15, 1, 2)
            .rec(14, 2, 1, 10)
            .rec(15, 11, 9, 1)
            .rec(18, 24, 6, 1)
            .rec(20, 9, 1, 1)
            .rec(22, 21, 1, 2)
            .rec(23, 21, 1, 2)
            .rec(24, 5, 1, 1)
            .rec(25, 21, 6, 1)
            .rec(26, 5, 1, 1)
            .rec(26, 20, 1, 2)
            .rec(27, 19, 1, 3)
            .rec(28, 19, 1, 3)
            .rec(29, 18, 1, 4)
            .rec(30, 9, 1, 1)
            .rec(30, 18, 1, 2)
            .color(colors[7])
            .rec(2, 12, 1, 1)
            .rec(4, 12, 1, 1)
            .rec(8, 12, 1, 1)
            .rec(11, 27, 1, 2)
            .rec(12, 27, 1, 2)
            .rec(13, 27, 1, 2)
            .rec(14, 27, 1, 2)
            .rec(15, 27, 1, 2)
            .rec(16, 27, 1, 2)
            .rec(17, 12, 1, 1)
            .rec(17, 27, 1, 2)
            .rec(18, 23, 1, 1)
            .rec(18, 27, 1, 2)
            .rec(20, 12, 1, 1)
            .rec(20, 20, 1, 1)
            .rec(21, 21, 1, 1)
            .rec(23, 23, 1, 1)
            .rec(24, 12, 1, 1)
            .color(colors[8])
            .rec(3, 12, 1, 1)
            .rec(5, 12, 3, 1)
            .rec(7, 15, 1, 1)
            .rec(7, 17, 2, 1)
            .rec(8, 16, 1, 2)
            .rec(11, 16, 1, 2)
            .rec(11, 26, 8, 1)
            .rec(12, 2, 1, 1)
            .rec(12, 5, 1, 1)
            .rec(12, 7, 1, 2)
            .rec(12, 10, 1, 3)
            .rec(12, 15, 1, 3)
            .rec(12, 25, 1, 2)
            .rec(13, 3, 1, 2)
            .rec(13, 6, 1, 1)
            .rec(13, 8, 1, 5)
            .rec(13, 25, 1, 2)
            .rec(14, 15, 1, 1)
            .rec(14, 25, 1, 2)
            .rec(15, 0, 1, 9)
            .rec(15, 16, 2, 1)
            .rec(16, 0, 1, 11)
            .rec(16, 13, 1, 1)
            .rec(17, 0, 1, 11)
            .rec(17, 14, 1, 2)
            .rec(18, 0, 1, 9)
            .rec(18, 12, 2, 1)
            .rec(18, 16, 1, 1)
            .rec(19, 2, 1, 1)
            .rec(19, 23, 4, 1)
            .rec(20, 3, 3, 1)
            .rec(20, 13, 1, 1)
            .rec(20, 22, 1, 2)
            .rec(21, 0, 2, 1)
            .rec(21, 12, 2, 1)
            .rec(21, 14, 1, 3)
            .rec(21, 22, 1, 2)
            .rec(22, 1, 1, 3)
            .rec(22, 13, 1, 4)
            .rec(23, 1, 1, 2)
            .rec(23, 13, 1, 3)
            .rec(24, 3, 1, 1)
            .rec(24, 14, 1, 2)
            .rec(25, 5, 1, 2)
            .rec(25, 13, 1, 1)
            .rec(26, 3, 2, 1)
            .rec(26, 14, 1, 1)
            .rec(27, 0, 1, 4)
            .rec(27, 5, 1, 1)
            .rec(27, 12, 2, 1)
            .rec(27, 15, 1, 1)
            .rec(28, 1, 1, 2)
            .rec(28, 6, 1, 1)
            .rec(28, 16, 1, 1)
            .rec(29, 1, 1, 2)
            .rec(29, 5, 1, 1)
            .rec(29, 14, 1, 2)
            .rec(29, 17, 2, 1)
            .rec(30, 0, 1, 1)
            .rec(30, 3, 2, 1)
            .rec(30, 13, 1, 2)
            .rec(31, 2, 1, 2)
            .rec(31, 16, 2, 1)
            .rec(32, 2, 1, 1)
            .rec(32, 9, 1, 2)
            .rec(32, 15, 1, 2)
            .rec(33, 3, 1, 1)
            .rec(33, 9, 1, 2)
            .rec(33, 15, 1, 1)
            .rec(34, 9, 1, 2)
            .rec(35, 0, 1, 2)
            .rec(36, 0, 1, 1)
            .rec(36, 3, 2, 1)
            .rec(37, 2, 1, 2)
            .rec(38, 2, 1, 1)
            .color(colors[9])
            .rec(12, 4, 1, 1)
            .rec(12, 6, 1, 1)
            .rec(12, 9, 1, 1)
            .rec(13, 5, 1, 1)
            .rec(13, 7, 1, 1)
            .rec(17, 13, 3, 1)
            .rec(18, 14, 1, 2)
            .rec(19, 14, 1, 3)
            .rec(20, 14, 1, 3)
            .rec(21, 13, 1, 1)
            .rec(23, 0, 4, 1)
            .rec(23, 3, 1, 1)
            .rec(24, 1, 1, 2)
            .rec(25, 1, 1, 3)
            .rec(25, 14, 1, 1)
            .rec(26, 1, 1, 2)
            .rec(26, 13, 4, 1)
            .rec(27, 6, 1, 1)
            .rec(27, 14, 2, 1)
            .rec(28, 5, 1, 1)
            .rec(28, 15, 1, 1)
            .rec(29, 6, 1, 1)
            .rec(29, 16, 2, 1)
            .rec(30, 1, 5, 1)
            .rec(30, 15, 1, 2)
            .rec(31, 0, 1, 2)
            .rec(31, 15, 1, 1)
            .rec(32, 0, 1, 2)
            .rec(32, 3, 1, 1)
            .rec(33, 0, 1, 3)
            .rec(34, 0, 1, 4)
            .rec(35, 2, 1, 2)
            .rec(36, 2, 1, 1)
            .color(colors[10])
            .rec(14, 16, 1, 1)
            .rec(15, 15, 2, 1)
            .rec(16, 14, 1, 2)
            .rec(17, 16, 1, 1)
            .rec(19, 3, 1, 1)
            .rec(20, 2, 2, 1)
            .rec(21, 1, 1, 2)
            .rec(23, 16, 5, 1)
            .rec(24, 13, 1, 1)
            .rec(25, 15, 1, 2)
            .rec(26, 15, 1, 2)
            .rec(28, 0, 2, 1)
            .rec(28, 3, 2, 1)
            .rec(30, 2, 1, 1)
            .rec(31, 13, 1, 2)
            .rec(33, 16, 1, 1)
            .rec(36, 1, 1, 1)
            .rec(38, 3, 1, 1)
            .close(), forge_anvil_kind);
    }

    public Path get(ResourceLocation id) {
        return output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(id.getNamespace())
                .resolve("textures")
                .resolve(id.getPath());
    }

    public Path get(String path) {
        return get(ResourceLocation.fromNamespaceAndPath(modid, path));
    }



    public void add(String path,int width, int height, Consumer<BIMG> consumer, LinkedList<ResourceLocation> list) {
        addResources(ResourceLocation.fromNamespaceAndPath(modid, path), width, height, consumer, list);
    }

    public void addResources(ResourceLocation id, int width, int height, Consumer<BIMG> consumer, LinkedList<ResourceLocation> list) {
        cmp.put(
                get(id)
                , new RBI(width, height, consumer)
        );
        if (list != null) list.add(id);

    }

    @SuppressWarnings("UnstableApiUsage")
    private void save(CachedOutput output, RBI rbi, Path path) {
        BIMG image = new BIMG(rbi.width(), rbi.height(), BufferedImage.TYPE_INT_ARGB);
        rbi.consumer().accept(image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {}
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();
            HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha256(), out)
            ) {
            ImageIO.write(image, "PNG", hashingoutputstream);


            output.writeIfNeeded(path, out.toByteArray(), hashingoutputstream.hash());


        } catch (IOException e) {
            Ding.LOGGER.error(e.getMessage());
        }
        images.put(path, image);
    }
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        apply();

        cmp.forEach((path, rbi) -> {
            save(output, rbi, path);

        });
        return CompletableFuture.allOf();
    }

    @Override
    public @NotNull String getName() {
        return modid + "TexturesGeneration";
    }

    public void preGeneration(String path) {
        List<Color> list = new LinkedList<>();
        Map<Color, List<XY>> cxy = new LinkedHashMap<>();
        Map<Color, List<XyWh>> cXyWhs = new LinkedHashMap<>();
        try(
                BufferedWriter bw = Files.newBufferedWriter(Path.of(System.getProperty("user.dir"), "colors.txt"));
                BufferedWriter bw1 = Files.newBufferedWriter(Path.of(System.getProperty("user.dir"), "code.txt"))
        ) {
            try {
                BufferedImage read = ImageIO.read(Objects.requireNonNull(Ding.class.getResourceAsStream(path)));

                int[][] data = new int[read.getWidth()][read.getHeight()];
                for (int i = 0; i < read.getWidth(); i++) {
                    for (int j = 0; j < read.getHeight(); j++) {
                        data[i][j] = read.getRGB(i, j);
                        var t = data[i][j];
//                        if (t == 0) continue;
                        Color color = new Color(((t & 0x00ff0000) >> 16), ((t & 0x0000ff00) >> 8), (t & 0x000000ff), ((t & 0xff000000) >>> 24));
                        if (color.getAlpha() == 0) continue;
                        List<XY> xys = cxy.getOrDefault(color, new LinkedList<>());
                        xys.add(XY.builder().x(i).y(j).build());
                        cxy.put(color, xys);
                        if (!list.contains(color)) {
                            list.add(color);
                            bw.write("new Color(%d, %d, %d, %d),\n".formatted(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                        }

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Map.Entry<Color, List<XY>> entry : cxy.entrySet()) {
                Color color = entry.getKey();
                LOGGER.info(color.toString());
                List<XY> xys = entry.getValue();
                List<Integer> sought = new LinkedList<>();
                for (int i = 0; i < xys.size(); i++) {
                    if (sought.contains(i)) continue;
                    XY xy = xys.get(i);
                    int x = xy.getX();
                    int y = xy.getY();
                    int j = 1, k = 1;
                    boolean isX = true;
                    boolean isY = true;
                    while (isX) {
                        XY tXY = XY.builder().x(x).y(y + j).build();
                        if (!xys.contains(tXY)) {
                            isX = false;
                            continue;
                        }
                        sought.add(xys.indexOf(tXY));
                        j++;
                    }
                    if (j == 1) {
                        while (isY) {
                            XY tXY = XY.builder().x(x + k).y(y).build();
                            if (!xys.contains(tXY)) {
                                isY = false;
                                continue;
                            }
                            sought.add(xys.indexOf(tXY));
                            k++;
                        }
                    }
                    List<XyWh> listXyWh = cXyWhs.getOrDefault(color, new LinkedList<>());
                    listXyWh.add(XyWh.builder().x(x).y(y).w(k).h(j).build());
                    cXyWhs.put(color, listXyWh);
                    sought.add(i);
                }
            }
            for (Map.Entry<Color, List<XyWh>> entry : cXyWhs.entrySet()) {
                Color color = entry.getKey();
                bw1.write(".color(colors[%d])\n".formatted(list.indexOf(color)));
                for (XyWh xyWh : entry.getValue()) {
                    bw1.write(".rec(%d, %d, %d, %d)\n".formatted(xyWh.x, xyWh.y, xyWh.w, xyWh.h));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
