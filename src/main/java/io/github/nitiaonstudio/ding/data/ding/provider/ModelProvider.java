package io.github.nitiaonstudio.ding.data.ding.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nitiaonstudio.ding.data.models.GeckolibModel;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@ExtensionMethod({Utils.class})
@Getter
public final class ModelProvider implements DataProvider {
    public static final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private final String modid;
    private final PackOutput output;
    public final ConcurrentHashMap<ResourceLocation, Object> models = new ConcurrentHashMap<>();

    public ModelProvider(PackOutput output, String modid) {
        this.modid = modid;
        this.output = output;
    }

    public ModelProvider addGeckolibBlockModel(Block block, int width, int height) {
        models.put(BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/"), new GeckolibModel().setCredit("Data generation by baka4n").setParent("builtin/entity").setTexture_size(new int[] { width, height }));
        Item item = block.asItem();
        return item != Items.AIR ? addGeckolibItemModel(item, width, height) : this;
    }

    public ModelProvider addGeckolibItemModel(Item item, int width, int height) {


        models.put(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/"), new GeckolibModel().setCredit("Data generation by baka4n").setParent("builtin/entity").setTexture_size(new int[] { width, height }));
        return this;
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput output) {
        CompletableFuture<?>[] futures = new CompletableFuture[models.size()];
        int i = 0;
        for (Map.Entry<ResourceLocation, Object> entry : models.entrySet()) {
            futures[i] = save(output, entry.getKey(), entry.getValue());
            i++;
        }
        return CompletableFuture.allOf(futures);
    }

    private CompletableFuture<?> save(CachedOutput output, ResourceLocation key, Object value) {
        return CompletableFuture.runAsync(() -> {
            var target = this.output
                    .getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(key.getNamespace())
                    .resolve("models")
                    .resolve(key.getPath() + ".json");
            output.saveJsonToPath(value, target, gson);
        });
    }



    @Override
    public @NotNull String getName() {
        return "Model Generation " + modid;
    }
}
