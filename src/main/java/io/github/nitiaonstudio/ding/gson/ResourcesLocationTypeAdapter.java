package io.github.nitiaonstudio.ding.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

public class ResourcesLocationTypeAdapter extends TypeAdapter<ResourceLocation> {
    @Override
    public void write(JsonWriter out, ResourceLocation value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public ResourceLocation read(JsonReader in) throws IOException {
        String[] split = in.nextString().split(":", 2);
        return ResourceLocation.fromNamespaceAndPath(split[0], split[1]);
    }
}
