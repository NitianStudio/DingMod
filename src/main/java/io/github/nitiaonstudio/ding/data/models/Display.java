package io.github.nitiaonstudio.ding.data.models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Display {
    @SerializedName("translation")
    public double[] translation;
    @SerializedName("rotation")
    public double[] rotation;
    @SerializedName("scale")
    public double[] scale;

    public Display translation(double... translation) {
        this.translation = translation;
        return this;
    }
    public Display rotation(double... rotation) {
        this.rotation = rotation;
        return this;
    }
    public Display scale(double... scale) {
        this.scale = scale;
        return this;
    }


}
