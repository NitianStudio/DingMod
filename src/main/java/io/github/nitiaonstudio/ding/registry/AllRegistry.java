package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllRegistry {
    protected static final DeferredRegister.Items items = DeferredRegister.createItems(Ding.MODID);
    @Getter
    protected static final DeferredRegister.Blocks blocks = DeferredRegister.createBlocks(Ding.MODID);
    protected static final DeferredRegister<BlockEntityType<?>> blockEntities = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ding.MODID);
    protected static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Ding.MODID);
    protected static final DeferredRegister.DataComponents components = DeferredRegister.createDataComponents(Ding.MODID);
    public static void registerAll(IEventBus modEventBus) {
        ComponentRegistry.registry();
        BlockRegistry.registry();
        ItemRegistry.registry();
        SoundRegistry.registry();
        components.register(modEventBus);
        sounds.register(modEventBus);
        blocks.register(modEventBus);
        blockEntities.register(modEventBus);
        items.register(modEventBus);

    }
}
