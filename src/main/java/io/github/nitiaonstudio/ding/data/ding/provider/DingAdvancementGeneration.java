package io.github.nitiaonstudio.ding.data.ding.provider;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.advancements.CraftedComponentGetTrigger;
import io.github.nitiaonstudio.ding.registry.ComponentRegistry;
import io.github.nitiaonstudio.ding.registry.TranslateKeyRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.github.nitiaonstudio.ding.data.ding.provider.DingRecipeProvider.getPath;

public class DingAdvancementGeneration extends AdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     */
    public DingAdvancementGeneration(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of((r, s, e) -> {
            Advancement.Builder
                    .advancement()
                    .addCriterion("inventory", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().hasComponents(DataComponentPredicate.builder().build()).build()
                    )).save(s, Ding.id("minecraft/amaze"), e);
//            Advancement.Builder
//                    .advancement()
//                    .addCriterion("craft",
//                            CraftedComponentGetTrigger.TriggerInstance.craftedItem(
//                                    Ding.id("minecraft/merge/", getPath(Items.COBBLESTONE)),
//                                    ComponentRegistry.forgeAnvilValue.get()
//                            ))
//                    .display(Items.COBBLESTONE,
//                            TranslateKeyRegistry.mergeCobbleStone,
//                            TranslateKeyRegistry.mergeCobbleStoneDesc,
//                            null,
//                            AdvancementType.GOAL,
//                            true,
//                            true,
//                            false
//                            )
//                    .save(s, Ding.id("minecraft/merge/" + getPath(Items.COBBLESTONE)), e);

        }));
    }


}
