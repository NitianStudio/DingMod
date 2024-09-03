package io.github.nitianstudio.ding.registry;

import com.mojang.datafixers.DSL;
import io.github.nitianstudio.ding.block.tile.ForgeAnvilTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.nitianstudio.ding.registry.AllRegistry.TILES;

public class RegistryBlockTile {
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeAnvilTileEntity>> forge_anvil_block =
            TILES.register("forge_anvil_block", factory ->
                    BlockEntityType.Builder
                            .of(ForgeAnvilTileEntity::new,
                                    RegistryBlock.forge_anvil_block.get()).build(DSL.remainderType()));


    public static void registry() {

    }
}
