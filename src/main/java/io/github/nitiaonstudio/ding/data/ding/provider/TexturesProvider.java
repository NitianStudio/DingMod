package io.github.nitiaonstudio.ding.data.ding.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.BIMG;
import io.github.nitiaonstudio.ding.data.resources.RLSs;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.nitiaonstudio.ding.data.resources.RLSs.*;


@ExtensionMethod({ Utils.class })
public class TexturesProvider implements DataProvider {

    private final PackOutput output;
    private final String modid;

    private final ConcurrentHashMap<ResourceLocation, RBI> cmp = new ConcurrentHashMap<>();
    @Getter
    private final ConcurrentHashMap<ResourceLocation, BIMG> images = new ConcurrentHashMap<>();

    public TexturesProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }




    public void apply() {
        RLSs.setCmp(cmp);// init cmp to rls s
        forge_anvil_block.addForgeAnvilBlockBaseGeneration(Map.of(
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

        ingot.addForgeAnvilBlockGeneration(Map.of(
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
        lozenge.addForgeAnvilBlockGeneration(Map.of(
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
        gemstone.addForgeAnvilBlockGeneration(Map.of(
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

        axe.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_AXE), new Color[]{
                        new Color(66, 33, 30, 255),
                        new Color(136, 136, 136, 255),
                        new Color(107, 104, 104, 255),
                        new Color(113, 67, 57, 255),
                        new Color(136, 96, 67, 255),
                        new Color(197, 197, 197, 255),
                        new Color(167, 134, 109, 255),
                        new Color(234, 234, 234, 255),
                }
        ));

        pickaxe.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_PICKAXE), new Color[]{
                        new Color(66, 33, 30, 255),
                        new Color(136, 136, 136, 255),
                        new Color(107, 104, 104, 255),
                        new Color(87, 87, 87, 255),
                        new Color(113, 67, 57, 255),
                        new Color(154, 154, 154, 255),
                        new Color(197, 197, 197, 255),
                        new Color(136, 96, 67, 255),
                        new Color(167, 134, 109, 255),
                }
        ));

        sword.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_SWORD), new Color[]{
                        new Color(82, 80, 86, 255),
                        new Color(197, 197, 197, 255),
                        new Color(136, 136, 136, 255),
                        new Color(136, 96, 67, 255),
                        new Color(163, 135, 112, 255),
                        new Color(95, 54, 45, 255),
                        new Color(107, 104, 104, 255),
                        new Color(154, 154, 154, 255),
                        new Color(113, 67, 57, 255),
                }
        ));

        hoe.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_HOE), new Color[]{
                        new Color(66, 33, 30, 255),
                        new Color(82, 80, 86, 255),
                        new Color(107, 104, 104, 255),
                        new Color(136, 136, 136, 255),
                        new Color(113, 67, 57, 255),
                        new Color(154, 154, 154, 255),
                        new Color(197, 197, 197, 255),
                        new Color(136, 96, 67, 255),
                        new Color(167, 134, 109, 255),
                }
        ));

        chestplate.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_CHESTPLATE), new Color[]{
                        new Color(245, 245, 245, 255),
                        new Color(197, 197, 197, 255),
                        new Color(91, 92, 108, 255),
                        new Color(165, 167, 173, 255),
                        new Color(194, 197, 200, 255),
                        new Color(221, 221, 221, 255),
                        new Color(160, 166, 176, 255),
                        new Color(115, 120, 139, 255),
                        new Color(64, 69, 86, 255),
                        new Color(124, 128, 137, 255),
                }
        ));

        helmet.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_HELMET), new Color[]{
                        new Color(221, 221, 221, 255),
                        new Color(238, 238, 238, 255),
                        new Color(64, 69, 86, 255),
                        new Color(115, 120, 139, 255),
                        new Color(160, 166, 176, 255),
                        new Color(197, 197, 197, 255),
                }
        ));

        leggings.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_LEGGINGS), new Color[]{
                        new Color(129, 133, 141, 255),
                        new Color(165, 167, 173, 255),
                        new Color(173, 178, 188, 255),
                        new Color(245, 245, 245, 255),
                        new Color(197, 197, 197, 255),
                        new Color(160, 166, 176, 255),
                        new Color(115, 120, 139, 255),
                        new Color(64, 69, 86, 255),
                        new Color(194, 197, 200, 255),
                        new Color(91, 92, 108, 255),
                }
        ));

        boots.addForgeAnvilBlockGeneration(Map.of(
                BuiltInRegistries.ITEM.getKey(Items.IRON_BOOTS), new Color[]{
                        new Color(58, 59, 69, 255),
                        new Color(94, 98, 107, 255),
                        new Color(129, 133, 141, 255),
                        new Color(245, 245, 245, 255),
                        new Color(160, 166, 176, 255),
                        new Color(115, 120, 139, 255),
                        new Color(64, 69, 86, 255),
                        new Color(197, 197, 197, 255),
                        new Color(194, 197, 200, 255),

                }
        ));

//        "/assets/ding/textures/block/forge_anvil_block/minecraft/iron_boots_get.png".preGeneration();

        var tmp = new ConcurrentHashMap<>(cmp);
        var tmp1 = new ConcurrentHashMap<>(cmp);

        for (Map.Entry<ResourceLocation, RBI> entry : tmp.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            String[] split = resourceLocation.getPath().split("/");
            String s = split[split.length - 1];
            RBI rbi = entry.getValue();
            if (rbi.isBase() && rbi.isGeneration()) {
                cmp.add(modid, "block/forge_anvil_block/%s".formatted(s), 128, 128, img -> {
                    img.sameCode(cmp, images, resourceLocation)
                            .close();
                }, 0, 0, false, false);
                for (Map.Entry<ResourceLocation, RBI> e : tmp1.entrySet()) {
                    ResourceLocation location = e.getKey();
                    RBI rbi1 = e.getValue();
                    if (rbi1.isGeneration() && !rbi1.isBase()) {
                        String[] split1 = location.getPath().split("/");
                        cmp.add(modid, "block/forge_anvil_block/%s/%s_%s".formatted(resourceLocation.getNamespace(), s.replace(".png", ""), split1[split1.length - 1]), 128, 128, img -> {
                            img
                                    .sameCode(cmp, getImages(), resourceLocation)
                                    .sameCode(cmp, getImages(), location)
                                    .close();
                        }, 0, 0, false, false);
                    }
                }
            }
        }


    }

    @SuppressWarnings("UnstableApiUsage")
    private void save(CachedOutput output, RBI rbi, ResourceLocation resourceLocation) {
        Path path = this.output.get(resourceLocation);

        BIMG image = new BIMG(rbi.width(), rbi.height(), BufferedImage.TYPE_INT_ARGB);
        rbi.consumer().accept(image);

        try(ByteArrayOutputStream out = new ByteArrayOutputStream();
            HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha256(), out)
            ) {
            ImageIO.write(image, "PNG", hashingoutputstream);
            if (!rbi.isGeneration()) {
                output.writeIfNeeded(path, out.toByteArray(), hashingoutputstream.hash());
            }



        } catch (IOException e) {
            Ding.LOGGER.error(e.getMessage());
        }
        images.put(resourceLocation, image);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        apply();
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<?>[] futures = new CompletableFuture[cmp.size()];
            int i = 0;
            for (Map.Entry<ResourceLocation, RBI> entry : cmp.entrySet()) {
                futures[i] = CompletableFuture.runAsync(() -> {
                    save(output, entry.getValue(), entry.getKey());
                }, executorService);
                i++;
            }
            return CompletableFuture.allOf(futures);
        }
    }

    @Override
    public @NotNull String getName() {
        return "Textures Generation " + modid;
    }

}
