package io.github.nitiaonstudio.ding.registry;


import com.mojang.datafixers.DSL;
import io.github.nitiaonstudio.ding.base.block.ForgeAnvilBlock;
import io.github.nitiaonstudio.ding.base.tile.ForgeAnvilTileEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import static io.github.nitiaonstudio.ding.registry.AllRegistry.*;

public class BlockRegistry {
    public static final DeferredBlock<ForgeAnvilBlock> forge_anvil_block = blocks.registerBlock("forge_anvil_block", ForgeAnvilBlock::new, BlockBehaviour.Properties.of());

    public static void registry() {
        ItemRegistry.registry();
        BlockEntityRegistry.registry();
    }

    public static class ItemRegistry {
        public static final DeferredItem<BlockItem> forge_anvil_block = items.registerSimpleBlockItem(BlockRegistry.forge_anvil_block);
        public static void registry() {
        }
    }

    public static class BlockEntityRegistry {
        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeAnvilTileEntity>> forge_anvil_block =
                blockEntities.register("forge_anvil_block", factory ->
                        BlockEntityType.Builder
                                .of(ForgeAnvilTileEntity::new,
                                        BlockRegistry.forge_anvil_block.get()).build(DSL.remainderType()));
        public static void registry() {
        }
    }
}
