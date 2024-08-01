package io.github.nitianstudio.ding.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.github.nitianstudio.ding.Ding.MODID;

public class AllRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> GROUPS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(MODID);
    public static void registry(IEventBus bus) {
        RegistryBlock.registry();
        RegistryBlockTile.registry();
        RegistryItem.registry();
        RegistryGroup.registry();
        RegistrySound.registry();
        RegistryComponents.register();

        BLOCKS.register(bus);
        TILES.register(bus);
        ITEMS.register(bus);
        GROUPS.register(bus);
        SOUNDS.register(bus);
        COMPONENTS.register(bus);
    }

}
