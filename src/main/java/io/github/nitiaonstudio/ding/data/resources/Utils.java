package io.github.nitiaonstudio.ding.data.resources;

import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.RLS;
import lombok.experimental.ExtensionMethod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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

    public static void add(RLSs rls, ConcurrentHashMap<ResourceLocation, RBI> cmp, String prefix, String suffix, Map<ResourceLocation, Color[]> locations) {
        for (Map.Entry<ResourceLocation, Color[]> entry : locations.entrySet()) {
            ResourceLocation first = entry.getKey();
            cmp.getRLS(rls).factory(first.withPrefix(prefix.replace("${namespace}", first.getNamespace())).withSuffix(suffix), entry.getValue());
        }
    }

    public static void addForgeAnvilBlockGeneration(RLSs rls, ConcurrentHashMap<ResourceLocation, RBI> cmp, Map<ResourceLocation, Color[]> locations) {
        add(rls, cmp, "generation/block/forge_anvil_block/${namespace}/", ".png", locations);
    }

    public static void addForgeAnvilBlockBaseGeneration(RLSs rls, ConcurrentHashMap<ResourceLocation, RBI> cmp, Map<ResourceLocation, Color[]> locations) {
        add(rls, cmp, "generation/block/forge_anvil_block/", ".png", locations);
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
