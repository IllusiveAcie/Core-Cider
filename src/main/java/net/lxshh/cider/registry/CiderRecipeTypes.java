package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.lxshh.cider.common.recipe.BlockApplicationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Cider.MOD_ID);

    public static final Supplier<RecipeType<BlockApplicationRecipe>> BLOCK_APPLICATION = RECIPE_TYPES.register("block_application",
            () -> new RecipeType<BlockApplicationRecipe>() {
                @Override
                public String toString() {
                    return "rack_drying";
                }
            });

}
