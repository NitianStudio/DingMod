package io.github.nitiaonstudio.ding.data.ding.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.data.blockstate.BlockStateGson;
import io.github.nitiaonstudio.ding.data.blockstate.BlockStateGson.Variant;
import io.github.nitiaonstudio.ding.data.models.GeckolibModel;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@ExtensionMethod({Utils.class})
@Getter
public final class BlockStatesProvider implements DataProvider {
    public static final Gson gson = new GsonBuilder().setLenient().disableHtmlEscaping().setPrettyPrinting().create();
    private final String modid;
    private final PackOutput output;
    public final ConcurrentHashMap<ResourceLocation, Object> blockstates = new ConcurrentHashMap<>();

    public BlockStatesProvider(PackOutput output, String modid) {
        this.modid = modid;
        this.output = output;
    }

    public BlockStatesProvider addBlockStates(Block block, BlockStateGson gson) {

        blockstates.put(BuiltInRegistries.BLOCK.getKey(block), gson);

        return  this;
    }


    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput output) {
        CompletableFuture<?>[] futures = new CompletableFuture[blockstates.size()];
        int i = 0;
        for (Map.Entry<ResourceLocation, Object> entry : blockstates.entrySet()) {
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
                    .resolve("blockstates")
                    .resolve(key.getPath() + ".json");
            output.saveJsonToPath(value, target, gson);
        });
    }



    @Override
    public @NotNull String getName() {
        return "Block states Generation " + modid;
    }
}
