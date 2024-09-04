package io.github.nitiaonstudio.ding.data;

import io.github.nitiaonstudio.ding.data.resources.BIMG;

import java.util.function.Consumer;

public record RBI(
        int width,
        int height,
        Consumer<BIMG> consumer,
        int insertX,
        int insertY,
        boolean isBase,
        boolean isGeneration
) {

}
