package io.github.nitianstudio.ding.data;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.world.item.Item;

@FunctionalInterface
public interface FunctionIII
        <I1 extends IntrinsicTagAppender<Item>, I2 extends IntrinsicTagAppender<Item>, I3 extends Item
                > {
    @SuppressWarnings("UnusedReturnValue")
    I1 apply(I2 i2, I3 i3);
}
