package io.github.nitiaonstudio.ding.data.resources;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.RLS;
import io.github.nitiaonstudio.ding.data.XY;
import io.github.nitiaonstudio.ding.data.XyWh;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static java.awt.AlphaComposite.Clear;

/**
 * 模板
 */
@ExtensionMethod({ Utils.class, RLSs.class })
public class Utils {



    public static void add(
            ConcurrentHashMap<ResourceLocation, RBI> cmp,
            String modid,
            String path,
            int width,
            int height,
            Consumer<BIMG> consumer,
            int insertX,
            int insertY,
            boolean isBase,
            boolean isGeneration
    ) {
        addResources(cmp, ResourceLocation.fromNamespaceAndPath(modid, path), width, height, consumer, insertX, insertY, isBase, isGeneration);
    }

    public static void add(RLS rls, String prefix, String suffix, Map<ResourceLocation, Color[]> locations) {
        for (Map.Entry<ResourceLocation, Color[]> entry : locations.entrySet()) {
            ResourceLocation first = entry.getKey();
            rls.factory(first.withPrefix(prefix.replace("${namespace}", first.getNamespace())).withSuffix(suffix), entry.getValue());
        }
    }

    public static void add(RLSs rls, String prefix, String suffix, Map<ResourceLocation, Color[]> locations) {
        for (Map.Entry<ResourceLocation, Color[]> entry : locations.entrySet()) {
            ResourceLocation first = entry.getKey();
            rls.getRLS().factory(first.withPrefix(prefix.replace("${namespace}", first.getNamespace())).withSuffix(suffix), entry.getValue());
        }
    }

    public static void saveJsonToPath(CachedOutput output, Object value, Path target, Gson gson) {
        String json = gson.toJson(value);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        try(val out = new ByteArrayOutputStream();
            val hash = new HashingOutputStream(Hashing.sha256(), out)
        ) {
            hash.write(bytes);
            output.writeIfNeeded(target, bytes, hash.hash());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void addForgeAnvilBlockGeneration(RLSs rls, Map<ResourceLocation, Color[]> locations) {
        add(rls, "generation/block/forge_anvil_block/${namespace}/", ".png", locations);
    }

    public static void addForgeAnvilBlockBaseGeneration(RLSs rls, Map<ResourceLocation, Color[]> locations) {
        add(rls, "generation/block/forge_anvil_block/", ".png", locations);
    }

    @SuppressWarnings("unused")
    @Deprecated(since = "Using in Date Generation")
    public static void preGeneration(String path) {
        java.util.List<Color> list = new LinkedList<>();
        Map<Color, java.util.List<XY>> cxy = new LinkedHashMap<>();
        Map<Color, java.util.List<XyWh>> cXyWhs = new LinkedHashMap<>();
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
                        java.util.List<XY> xys = cxy.getOrDefault(color, new LinkedList<>());
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
            for (Map.Entry<Color, java.util.List<XY>> entry : cxy.entrySet()) {
                Color color = entry.getKey();
                java.util.List<XY> xys = entry.getValue();
                java.util.List<Integer> sought = new LinkedList<>();
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
                    java.util.List<XyWh> listXyWh = cXyWhs.getOrDefault(color, new LinkedList<>());
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

    @SuppressWarnings("UnusedReturnValue")
    public static ConcurrentHashMap<ResourceLocation, RBI> copyResources(
            ConcurrentHashMap<ResourceLocation, RBI> cmp,
            ConcurrentHashMap<ResourceLocation, BIMG> images,
            int insertX,
            int insertY,
            boolean isBase,
            boolean isGeneration,
            Item... items
    ) {
        for (Item item : items) {
            copyResources(cmp, images, item, insertX, insertY, isBase, isGeneration);
        }
        return cmp;
    }

    public static void copyResources(
            ConcurrentHashMap<ResourceLocation, RBI> cmp,
            ConcurrentHashMap<ResourceLocation, BIMG> images,
            Item item,
            int insertX,
            int insertY,
            boolean isBase,
            boolean isGeneration
    ) {
        // String path
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item).withSuffix(".png");
        try (InputStream resourceAsStream = Utils.class.getResourceAsStream("/assets/%s/textures/item/%s".formatted(key.getNamespace(), key.getPath()))) {
            Objects.requireNonNull(resourceAsStream);
            BufferedImage read = ImageIO.read(resourceAsStream);

            addResources(cmp, key, read.getWidth(), read.getHeight(), img -> {
                img
                        .create()
                        .composite(Clear)
                        .create()
                        .insertImage(read, 0, 0);
            }, insertX, insertY, isBase, isGeneration);
        } catch (IOException ignored) {

        }

    }

    public static void addResources(
            ConcurrentHashMap<ResourceLocation, RBI> cmp,
            ResourceLocation id,
            int width,
            int height,
            Consumer<BIMG> consumer,
            int insertX,
            int insertY,
            boolean isBase,
            boolean isGeneration) {
        cmp.put(
                id
                , new RBI(width, height, consumer, insertX, insertY, isBase, isGeneration)
        );
    }

    public static BIMG sameCode(BIMG img, ConcurrentHashMap<ResourceLocation, RBI> cmp, ConcurrentHashMap<ResourceLocation, BIMG> images, ResourceLocation location) {
        while (!cmp.containsKey(location) || !images.containsKey(location)) {
            try {
                Thread.sleep(Duration.of(2, ChronoUnit.SECONDS));//多线程循环锁死
            } catch (InterruptedException ignored) {}
        }
        RBI rbi = cmp.get(location);
        return img.insertImage(images.get(location), rbi.insertX(), rbi.insertY());
    }

    public static Path get(PackOutput output, ResourceLocation id) {
        return output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(id.getNamespace())
                .resolve("textures")
                .resolve(id.getPath());
    }

    public Path get(PackOutput output, String modid,String path) {
        return get(output ,ResourceLocation.fromNamespaceAndPath(modid, path));
    }
}
