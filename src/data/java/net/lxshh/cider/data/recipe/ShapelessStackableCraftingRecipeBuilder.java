package net.lxshh.cider.data.recipe;

import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.lxshh.cider.common.recipe.ShapelessStackableCraftingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShapelessStackableCraftingRecipeBuilder implements RecipeBuilder {
    private final Ingredient primaryIngredient;
    private final Ingredient secondaryIngredient;
    private final ItemStackProvider result;
    private int min = 1;
    private int max = 8;

    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public ShapelessStackableCraftingRecipeBuilder(Ingredient primaryIngredient, Ingredient secondaryIngredient, ItemStackProvider result) {
        this.primaryIngredient = primaryIngredient;
        this.secondaryIngredient = secondaryIngredient;
        this.result = result;
    }

    public ShapelessStackableCraftingRecipeBuilder shapeless(Ingredient ingredient, Ingredient secondaryIngredient, ItemStackProvider result) {
        return new ShapelessStackableCraftingRecipeBuilder(ingredient, secondaryIngredient, result);
    }

    public ShapelessStackableCraftingRecipeBuilder shapeless(ItemLike ingredient, ItemLike secondaryIngredient, ItemLike result) {
        return new ShapelessStackableCraftingRecipeBuilder(Ingredient.of(ingredient), Ingredient.of(secondaryIngredient), ItemStackProvider.of(result));
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return result.stack().getItem();
    }

    protected Recipe<?> recipe() {
        return new ShapelessStackableCraftingRecipe(this.primaryIngredient, this.secondaryIngredient, min, max, result);
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation recipeId) {
        ensureValid(recipeId);

        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement::addCriterion);

        output.accept(recipeId, recipe(), advancement.build(recipeId.withPrefix("recipes/")));
    }

    protected void ensureValid(ResourceLocation recipeId) {
        if (criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
