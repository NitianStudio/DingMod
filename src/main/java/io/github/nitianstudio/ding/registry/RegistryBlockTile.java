package io.github.nitianstudio.ding.registry;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static io.github.nitianstudio.ding.registry.AllRegistry.TILES;

public enum RegistryBlockTile implements Supplier<BlockEntityType<? extends BlockEntity>> {
    forge_anvil(ForgeAnvilTileEntity::new, RegistryBlock.forge_anvil);
    public final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends BlockEntity>> tileType;
    @SafeVarargs
    RegistryBlockTile(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> factory, Supplier<Block>... blocks) {
        this(factory, DSL.remainderType(), blocks);
    }

    @SafeVarargs
    RegistryBlockTile(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> factory, Type<?> type, Supplier<Block>... blocks) {
        tileType = TILES.register(name().toLowerCase(Locale.ROOT), () -> {
            Block[] list = new Block[blocks.length];
            for (int i = 0; i < blocks.length; i++) {
                list[i] = blocks[i].get();
            }
            return BlockEntityType.Builder.of(factory, list).build(type);
        });
    }

    public static void registry() {

    }

    @Override
    public BlockEntityType<? extends BlockEntity> get() {
        return tileType.get();
    }
}
