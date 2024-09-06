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
import static net.minecraft.world.item.Items.*;


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



        cmp.copyResources(images,0, 32, false, true,
                        IRON_INGOT, COPPER_INGOT, GOLD_INGOT, NETHERITE_INGOT)
                .copyResources(images, 16, 32, false, true, DIAMOND)
                .copyResources(images, 32, 32, false, true, EMERALD)
                .copyResources(images, 48, 32, false, true,
                        IRON_AXE, WOODEN_AXE, GOLDEN_AXE, NETHERITE_AXE, DIAMOND_AXE, STONE_AXE)
                .copyResources(images, 64, 32, false, true,
                        IRON_PICKAXE, WOODEN_PICKAXE, GOLDEN_PICKAXE, NETHERITE_PICKAXE, DIAMOND_PICKAXE, STONE_PICKAXE)
                .copyResources(images, 48, 0, false, true,
                        IRON_SWORD, WOODEN_SWORD, GOLDEN_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, STONE_SWORD)
                .copyResources(images, 48, 16, false, true,
                        IRON_HOE, WOODEN_HOE, GOLDEN_HOE, NETHERITE_HOE, DIAMOND_HOE, STONE_HOE)
                .copyResources(images, 64, 0, false, true,
                        IRON_CHESTPLATE, GOLDEN_CHESTPLATE, NETHERITE_CHESTPLATE, DIAMOND_CHESTPLATE, CHAINMAIL_CHESTPLATE)
        ;

//        "/assets/minecraft/textures/item/netherite_ingot.png".preGeneration();


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



        var tmp = new ConcurrentHashMap<>(cmp);
        var tmp1 = new ConcurrentHashMap<>(cmp);

        for (Map.Entry<ResourceLocation, RBI> entry : tmp.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            String[] split = resourceLocation.getPath().split("/");
            String s = split[split.length - 1];
            RBI rbi = entry.getValue();
            if (rbi.isBase() && rbi.isGeneration()) {
                cmp.add(modid, "block/forge_anvil_block/%s".formatted(s), 256, 256, img -> {
                    img.sameCode(cmp, images, resourceLocation)
                            .close();
                }, 0, 0, false, false);
                for (Map.Entry<ResourceLocation, RBI> e : tmp1.entrySet()) {
                    ResourceLocation location = e.getKey();
                    RBI rbi1 = e.getValue();
                    if (rbi1.isGeneration() && !rbi1.isBase()) {
                        String[] split1 = location.getPath().split("/");
                        cmp.add(modid, "block/forge_anvil_block/%s/%s_%s".formatted(resourceLocation.getNamespace(), s.replace(".png", ""), split1[split1.length - 1]), 256, 256, img -> {
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
