package io.github.nitiaonstudio.ding.data.ding.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nitiaonstudio.ding.data.lang.Languages;
import io.github.nitiaonstudio.ding.data.resources.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import net.minecraft.core.Holder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
@Getter
@Setter
@ExtensionMethod({Utils.class})
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

    public <R> DingLanguageProvider add(Supplier<R> holder, String translate) {
        R value = holder.get();
        return switch (value) {
            case SoundEvent sound -> add( "sound.${namespace}.${path}", sound.getLocation(), translate);
            case Item item -> add(item.getDescriptionId(), translate);
            case Block block -> add(block.getDescriptionId(), translate);
            default -> this;
        };
    }

    public DingLanguageProvider add(String key, String translate) {
        targetSelect.put(key, translate);
        return this;
    }

    public DingLanguageProvider add(String format, ResourceLocation key, String translate) {
        return add(format.replace("${namespace}", key.getNamespace()).replace("${path}",key.getPath()), translate);
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

    @SuppressWarnings("UnstableApiUsage")
    private CompletableFuture<?> save(CachedOutput output, Languages lang, ConcurrentHashMap<String, String> language) {

        return CompletableFuture.runAsync(() -> {
            var target = this.output
                    .getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(modid)
                    .resolve("lang")
                    .resolve(lang.name() + ".json");
            output.saveJsonToPath(language, target, gson);

        });
    }



    @Override
    public @NotNull String getName() {
        return "Language Generation " + modid;
    }

}
