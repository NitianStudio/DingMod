package io.github.nitiaonstudio.ding.data.ding.provider;

import io.github.nitiaonstudio.ding.Ding;
import io.github.nitiaonstudio.ding.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;
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
                .unlockedBy("has" + getPath(forgeHammer) + "_" + getPath(A), has(A))
                .unlockedBy("has" + getPath(forgeHammer) + "_" + getPath(B), has(B))
                .save(recipeOutput);


    }

    public static String getPath(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        hammerGen(ItemRegistry.forge_hammer.asItem(), Items.IRON_INGOT, Items.STICK, recipeOutput);
        hammerGen(ItemRegistry.forge_hammer_gold.asItem(), Items.GOLD_INGOT, Items.STICK, recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.COBBLESTONE)
                .define('A', Items.COBBLESTONE)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy("has" + getPath(Items.COBBLESTONE) + "merge", has(Items.COBBLESTONE))
                .save(recipeOutput, Ding.id("minecraft/merge/", getPath(Items.COBBLESTONE)));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, new ItemStack(Items.COBBLESTONE, 9))
                .define('A', Items.COBBLESTONE)
                .pattern("A")
                .unlockedBy("has" + getPath(Items.COBBLESTONE), has(Items.COBBLESTONE))
                .save(recipeOutput, Ding.id("minecraft/", getPath(Items.COBBLESTONE)));
    }
}
