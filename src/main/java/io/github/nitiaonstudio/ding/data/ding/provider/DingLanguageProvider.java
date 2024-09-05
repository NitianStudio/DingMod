package io.github.nitiaonstudio.ding.data.ding.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.nitiaonstudio.ding.data.lang.Languages;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("UnusedReturnValue")
@Getter
@Setter

public final class DingLanguageProvider implements DataProvider {
    public static final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
    public final ConcurrentHashMap<Languages, ConcurrentHashMap<String, String>> languages = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, String> targetSelect;
    private final PackOutput output;
    private final String modid;

    public DingLanguageProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }

    public DingLanguageProvider languageSelect(Languages lang) {
        targetSelect = new ConcurrentHashMap<>();
        languages.put(lang, targetSelect);

        return this;
    }

    public DingLanguageProvider add(Item item, String translate) {
        targetSelect.put(item.getDescriptionId(), translate);
        return this;
    }

    public DingLanguageProvider add(Block block, String translate) {
        targetSelect.put(block.getDescriptionId(), translate);
        return this;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        CompletableFuture<?>[] futures = new CompletableFuture[languages.size()];
        int i = 0;
        for (Map.Entry<Languages, ConcurrentHashMap<String, String>> entry : languages.entrySet()) {

            futures[i] = save(output, entry.getKey(), entry.getValue());
            i++;
        }
        return CompletableFuture.allOf(futures);
    }

    private CompletableFuture<?> save(CachedOutput output, Languages lang, ConcurrentHashMap<String, String> language) {

        return CompletableFuture.runAsync(() -> {
            var target = this.output
                    .getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(modid)
                    .resolve("lang")
                    .resolve(lang.name() + ".json");
            String json = gson.toJson(language);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            try(val out = new ByteArrayOutputStream();
                val hash = new HashingOutputStream(Hashing.sha256(), out)
            ) {
                hash.write(bytes);
                output.writeIfNeeded(target, bytes, hash.hash());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }



    @Override
    public @NotNull String getName() {
        return "Language Generation " + modid;
    }

}
