package io.github.nitianstudio.ding.data.resources;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import io.github.nitianstudio.ding.Const;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TexturesGeneration implements DataProvider {

    private final PackOutput output;
    private final String modid;

    private final LinkedHashMap<Path, Consumer<BufferedImage>> cmp = new LinkedHashMap<>();

    public TexturesGeneration(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;

    }

    public void apply() {
        add("block/test.png", img -> {
            Graphics2D graphics = img.createGraphics();
            graphics.setColor(Color.RED);
            graphics.drawRect(0, 0, 128, 128);
        });
    }

    public void add(ResourceLocation id, Consumer<BufferedImage> consumer) {
        cmp.put(
                output
                        .getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                        .resolve(id.getNamespace())
                        .resolve("textures")
                        .resolve(id.getPath())
                , consumer
        );
    }

    public void add(String path, Consumer<BufferedImage> consumer) {

        cmp.put(
                output
                        .getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                        .resolve(modid)
                        .resolve("textures")
                        .resolve(path + ".png")
                , consumer
        );
    }

    private void save(CachedOutput output, Consumer<BufferedImage> consumer, Path path) {
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        consumer.accept(image);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();
            HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha256(), out)) {

            ImageIO.write(image, "png", out);
            output.writeIfNeeded(path, out.toByteArray(), hashingoutputstream.hash());
        } catch (IOException e) {
            Const.LOGGER.error(e.getMessage());
        }
    }
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        apply();
        cmp.forEach((path, consumer) -> save(output, consumer, path));
        return CompletableFuture.allOf();
    }

    @Override
    public @NotNull String getName() {
        return "TexturesGeneration";
    }
}
