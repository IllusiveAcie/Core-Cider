package net.lxshh.cider.data.recipe;

import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.lxshh.cider.common.recipe.BlockInteractionRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockInteractionRecipeBuilder {
    private final BlockIngredient ingredient;
    private final BlockState outputState;
    private final Ingredient itemIngredient;
    private boolean consumeItem;
    private boolean fake;

    public BlockInteractionRecipeBuilder(BlockIngredient ingredient, BlockState outputState, Ingredient itemIngredient) {
        this.ingredient = ingredient;
        this.outputState = outputState;
        this.itemIngredient = itemIngredient;
        this.consumeItem = false;
        this.fake = false;
    }

    public static BlockInteractionRecipeBuilder interact(BlockIngredient ingredient, BlockState outputState, Ingredient itemIngredient) {
        return new BlockInteractionRecipeBuilder(ingredient, outputState, itemIngredient);
    }

    public static BlockInteractionRecipeBuilder interact(Block ingredient, Block outputBlock, Ingredient itemIngredient) {
        return new BlockInteractionRecipeBuilder(BlockIngredient.of(ingredient), outputBlock.defaultBlockState(), itemIngredient);
    }

    public void consumesItem() {
        this.consumeItem = true;
    }

    public void markAsFake() {
        this.fake = true;
    }

    protected Recipe<?> recipe() {
        return new BlockInteractionRecipe(ingredient, outputState, itemIngredient, consumeItem, fake);
    }

    public void save(RecipeOutput output, ResourceLocation recipeId) {
        ensureValid(recipeId);
        output.accept(recipeId, recipe(), null);
    }

    private void ensureValid(ResourceLocation recipeId) {
        if (ingredient == null) {
            throw new IllegalStateException(recipeId + " doesn't have an ingredient assigned");
        }
        if (outputState == null) {
            throw new IllegalStateException(recipeId + " doesn't have a output assigned");
        }
        if (itemIngredient == null) {
            throw new IllegalStateException(recipeId + " doesn't have an item ingredient assigned");
        }
    }
}
