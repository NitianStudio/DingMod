package io.github.nitiaonstudio.ding.data.blockstate;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.nitiaonstudio.ding.gson.ResourcesLocationTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
@Accessors(chain = true)
@Setter
@Getter
public class BlockStateGson {
    @SerializedName("variants")
    public Map<String, Variant> variants;

    public BlockStateGson() {
        variants = new HashMap<>();
    }

    public BlockStateGson add(String key, Variant variant) {
        variants.put(key, variant);
        return this;
    }

    @Accessors(chain = true)
    @Setter
    @Getter
    public static class Variant {
        @SerializedName("model")
        @JsonAdapter(ResourcesLocationTypeAdapter.class)
        public ResourceLocation model;

        @SerializedName("x")
        public Number x;
        @SerializedName("y")
        public Number y;
        @SerializedName("z")
        public Number z;

        public Variant copy() {
            return new Variant().setModel(model).setX(x).setY(y).setZ(z);
        }
    }
}
