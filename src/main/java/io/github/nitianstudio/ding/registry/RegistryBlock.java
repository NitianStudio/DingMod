package io.github.nitianstudio.ding.registry;

import io.github.nitianstudio.ding.block.ForgeAnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.registry.AllRegistry.BLOCKS;

public enum RegistryBlock implements Supplier<Block> {
    forge_anvil(ForgeAnvilBlock::new);
    public final DeferredBlock<Block> block;

    RegistryBlock(Function<BlockBehaviour.Properties, Block> function) {
        block = BLOCKS.registerBlock(name().toLowerCase(Locale.ROOT), function, BlockBehaviour.Properties.of());
    }

    public static void registry() {

    }


    @Override
    public Block get() {
        return block.get();
    }
}
