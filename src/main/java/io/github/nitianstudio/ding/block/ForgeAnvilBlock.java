package io.github.nitianstudio.ding.block;

import io.github.nitianstudio.ding.registry.RegistryBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForgeAnvilBlock extends Block implements EntityBlock {
    public ForgeAnvilBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return RegistryBlockTile.forge_anvil.get().create(pos, state);
    }
}
