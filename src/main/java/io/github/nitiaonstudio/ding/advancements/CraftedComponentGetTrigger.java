package io.github.nitiaonstudio.ding.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.nitiaonstudio.ding.registry.TriggerRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CraftedComponentGetTrigger extends SimpleCriterionTrigger<CraftedComponentGetTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }


    public void trigger(@NotNull ServerPlayer player, ResourceLocation id, List<ItemStack> ingredients) {
        super.trigger(player, instance -> instance.matches(id, ingredients));
    }

    public record TriggerInstance(
            Optional<ContextAwarePredicate> player,
            ResourceLocation recipeId,
            List<ItemPredicate> ingredients,
            DataComponentType<?> component

    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<CraftedComponentGetTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CraftedComponentGetTrigger.TriggerInstance::player),
                                ResourceLocation.CODEC.fieldOf("recipe_id").forGetter(CraftedComponentGetTrigger.TriggerInstance::recipeId),
                                ItemPredicate.CODEC.listOf().optionalFieldOf("ingredients", List.of()).forGetter(CraftedComponentGetTrigger.TriggerInstance::ingredients),
                                DataComponentType.CODEC.fieldOf("components").forGetter(CraftedComponentGetTrigger.TriggerInstance::component)
                        )
                        .apply(builder, CraftedComponentGetTrigger.TriggerInstance::new)
        );

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return player;
        }

        public static Criterion<TriggerInstance> craftedItem(ResourceLocation recipeId, List<ItemPredicate.Builder> ingredients, DataComponentType<?> component) {
            return TriggerRegistry.component_geted
                    .get().createCriterion(
                            new TriggerInstance(Optional.empty(), recipeId, ingredients.stream().map(ItemPredicate.Builder::build).toList(), component)
                    );
        }
        public static Criterion<TriggerInstance> craftedItem(ResourceLocation recipeId, DataComponentType<?> component) {
            return TriggerRegistry.component_geted
                    .get().createCriterion(
                            new TriggerInstance(Optional.empty(), recipeId, List.of(), component)
                    );
        }

        public static Criterion<TriggerInstance> crafterRcipCraftedItem(ResourceLocation recipeId, DataComponentType<?> component) {
            return TriggerRegistry.component_geted_crafter
                    .get().createCriterion(
                            new TriggerInstance(Optional.empty(), recipeId, List.of(), component)
                    );
        }

        boolean matches(ResourceLocation recipeId, List<ItemStack> items) {
            if (!recipeId.equals(this.recipeId)) {
                return false;
            } else {
                List<ItemStack> list = new ArrayList<>(items);

                for (ItemPredicate itempredicate : this.ingredients) {
                    boolean flag = false;
                    Iterator<ItemStack> iterator = list.iterator();

                    while (iterator.hasNext()) {

                        ItemStack next = iterator.next();

                        if (!next.has(component)) {
                            break;
                        }
                        if (itempredicate.test(next)) {
                            iterator.remove();
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }

                return true;
            }
        }
    }
}
