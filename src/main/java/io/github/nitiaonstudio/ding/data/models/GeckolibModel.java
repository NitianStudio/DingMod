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

    @SerializedName("thirdperson_righthand")
    public Display thirdperson_righthand;

    @SerializedName("thirdperson_lefthand")
    public Display thirdperson_lefthand;

    @SerializedName("firstperson_righthand")
    public Display firstperson_righthand;

    @SerializedName("firstperson_lefthand")
    public Display firstperson_lefthand;

    @SerializedName("gui")
    public Display gui;

    @SerializedName("fixed")
    public Display fixed;
}
