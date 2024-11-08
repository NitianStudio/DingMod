package io.github.nitiaonstudio.ding.data.ding.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.data.RBI;
import io.github.nitiaonstudio.ding.data.resources.BIMG;
import io.github.nitiaonstudio.ding.data.resources.RLSs;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import io.github.nitiaonstudio.ding.registry.ItemRegistry;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

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
//        forge_hammer.addForgeHammerGeneration(Map.of(
//               Ding.id("base"), new Color[]{
//                        new Color(167, 167, 167, 255),
//                        new Color(136, 139, 143, 255),
//                        new Color(78, 80, 83, 255),
//                        new Color(132, 135, 144, 255),
//                        new Color(55, 57, 59, 255),
//                        new Color(37, 39, 41, 255),
//                        new Color(222, 222, 222, 255),
//                        new Color(109, 111, 119, 255),
//                        new Color(105, 109, 120, 255),
//                        new Color(68, 66, 83, 255),
//                        new Color(50, 52, 59, 255),
//                        new Color(95, 98, 104, 255),
//                        new Color(66, 33, 30, 255),
//                        new Color(113, 67, 57, 255),
//                        new Color(82, 80, 86, 255),
//                        new Color(136, 96, 67, 255),
//                        new Color(167, 134, 109, 255),
//
//                }
//        ));


        cmp.copyResources(0, 32, false, true,
                        IRON_INGOT, COPPER_INGOT, GOLD_INGOT, NETHERITE_INGOT);
        cmp.copyResources( 16, 32, false, true, DIAMOND);
        cmp.copyResources( 32, 32, false, true, EMERALD);
        cmp.copyResources( 48, 32, false, true,
                        IRON_AXE, WOODEN_AXE, GOLDEN_AXE, NETHERITE_AXE, DIAMOND_AXE, STONE_AXE);
        cmp.copyResources( 64, 32, false, true,
                        IRON_PICKAXE, WOODEN_PICKAXE, GOLDEN_PICKAXE, NETHERITE_PICKAXE, DIAMOND_PICKAXE, STONE_PICKAXE);
        cmp.copyResources( 48, 0, false, true,
                        IRON_SWORD, WOODEN_SWORD, GOLDEN_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, STONE_SWORD);
        cmp.copyResources( 48, 16, false, true,
                        IRON_HOE, WOODEN_HOE, GOLDEN_HOE, NETHERITE_HOE, DIAMOND_HOE, STONE_HOE);
        cmp.copyResources(64, 0, false, true,
                        IRON_CHESTPLATE, GOLDEN_CHESTPLATE, NETHERITE_CHESTPLATE, DIAMOND_CHESTPLATE, CHAINMAIL_CHESTPLATE);
        cmp.copyResources(64, 16, false, true,
                        IRON_HELMET, GOLDEN_HELMET, NETHERITE_HELMET, DIAMOND_HELMET, CHAINMAIL_HELMET);
        cmp.copyResources(80, 0, false, true,
                        IRON_LEGGINGS, GOLDEN_LEGGINGS, NETHERITE_LEGGINGS, DIAMOND_LEGGINGS, CHAINMAIL_LEGGINGS);
        cmp.copyResources(80, 16, false, true,
                        IRON_BOOTS, GOLDEN_BOOTS, NETHERITE_BOOTS, DIAMOND_BOOTS, CHAINMAIL_BOOTS);
        cmp.copyResources(80, 32, false, true,
                        IRON_NUGGET, GOLD_NUGGET);
        cmp.copyResources(96, 0, false, true,
                        NETHER_STAR);
        cmp.copyResources(ItemRegistry.forge_hammer.get(),96, 16, false, true,
                Ding.id("forge_hammer/base.png"));
        cmp.copyResources(ItemRegistry.forge_hammer_gold.get(),96, 16, false, true,
                Ding.id("forge_hammer/gold.png"));
//        if (ModList.get().getModFileById("anvilcraft") != null) {
//            TextureData.apply(cmp);
//        }
//        Ding.loadRunLinkage("anvilcraft",
//                false,
//                null,
//                "io.github.nitiaonstudio.ding.anvilcraft.data.TextureData",
//                "apply",
//                cmp);


//        cmp.copyResources(new ResourceLocation[] {
//                Ding.id("forge_hammer/base")
//        },96, 16, false, true);

//        "/assets/ding/textures/item/forge_hammer/gold.png".preGeneration();


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
                        cmp.add(modid, "block/forge_anvil_block/%s/%s_%s".formatted(location.getNamespace(), s.replace(".png", ""), split1[split1.length - 1]), 256, 256, img -> {
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
