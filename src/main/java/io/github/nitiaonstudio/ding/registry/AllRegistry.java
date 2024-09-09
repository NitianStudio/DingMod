package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllRegistry {
    protected static final DeferredRegister.Items items = DeferredRegister.createItems(Ding.MODID);
    protected static final DeferredRegister.Blocks blocks = DeferredRegister.createBlocks(Ding.MODID);
    protected static final DeferredRegister<BlockEntityType<?>> blockEntities = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ding.MODID);
    protected static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Ding.MODID);

    public static void registerAll(IEventBus modEventBus) {
        BlockRegistry.registry();
        SoundRegistry.registry();
        sounds.register(modEventBus);
        blocks.register(modEventBus);
        blockEntities.register(modEventBus);
        items.register(modEventBus);
    }
}
