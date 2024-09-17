package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.Ding;
import lombok.Getter;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllRegistry {
    protected static final DeferredRegister.Items items = DeferredRegister.createItems(Ding.MODID);
    protected static final DeferredRegister<CriterionTrigger<?>> triggers = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Ding.MODID);
    @Getter
    protected static final DeferredRegister.Blocks blocks = DeferredRegister.createBlocks(Ding.MODID);
    protected static final DeferredRegister<BlockEntityType<?>> blockEntities = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ding.MODID);
    protected static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Ding.MODID);
    protected static final DeferredRegister.DataComponents components = DeferredRegister.createDataComponents(Ding.MODID);
    protected static final DeferredRegister<CreativeModeTab> groups = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Ding.MODID);
    public static void registerAll(IEventBus modEventBus) {
        ComponentRegistry.registry();
        TriggerRegistry.registry();
        BlockRegistry.registry();
        ItemRegistry.registry();
        SoundRegistry.registry();
        ItemGroupRegistry.registry();
        components.register(modEventBus);
        triggers.register(modEventBus);
        sounds.register(modEventBus);
        blocks.register(modEventBus);
        blockEntities.register(modEventBus);
        items.register(modEventBus);
        groups.register(modEventBus);

    }
}
