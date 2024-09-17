package io.github.nitiaonstudio.ding.registry;

import io.github.nitiaonstudio.ding.advancements.CraftedComponentGetTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.nitiaonstudio.ding.registry.AllRegistry.triggers;

public class TriggerRegistry {
    public static final DeferredHolder<CriterionTrigger<?>, CraftedComponentGetTrigger>
            component_geted = triggers.register("component_geted", CraftedComponentGetTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, CraftedComponentGetTrigger>
            component_geted_crafter = triggers.register("component_get", CraftedComponentGetTrigger::new);

    public static void registry() {}
}
