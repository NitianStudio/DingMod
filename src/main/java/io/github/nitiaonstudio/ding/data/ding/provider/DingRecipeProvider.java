package io.github.nitiaonstudio.ding.data.ding.provider;

import io.github.nitiaonstudio.ding.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DingRecipeProvider extends RecipeProvider {
    public DingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public void hammerGen(ItemLike forgeHammer,ItemLike A, ItemLike B, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.TOOLS, forgeHammer)
                .define('A', A)
                .define('B', B)
                .pattern("BA ")
                .pattern("AB ")
                .pattern("  B")
                .unlockedBy("hasRecipe" + getPath(forgeHammer) + "_" + getPath(A), has(A))
                .unlockedBy("hasRecipe" + getPath(forgeHammer) + "_" + getPath(B), has(B))
                .save(recipeOutput);
    }

    public String getPath(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        hammerGen(ItemRegistry.forge_hammer.asItem(), Items.IRON_INGOT, Items.STICK, recipeOutput);
        hammerGen(ItemRegistry.forge_hammer_gold.asItem(), Items.GOLD_INGOT, Items.STICK, recipeOutput);
    }
}
