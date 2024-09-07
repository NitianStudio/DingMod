package io.github.nitiaonstudio.ding.data.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 {
 "credit": "Made with Blockbench",
 "parent": "builtin/entity",
 "texture_size": [
 128,
 128
 ]
 }
 */
@Accessors(chain = true)
@Setter
@Getter
public class GeckolibModel {
    @SerializedName("credit")
    public String credit;

    @SerializedName("parent")
    public String parent;

    @SerializedName("texture_size")
    public  int[] texture_size;
}
