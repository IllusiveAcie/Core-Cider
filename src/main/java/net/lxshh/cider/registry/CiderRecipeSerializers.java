package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.lxshh.cider.common.recipe.AnvilRepairingRecipe;
import net.lxshh.cider.common.recipe.BlockApplicationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Cider.MOD_ID);

    public static final Supplier<RecipeSerializer<BlockApplicationRecipe>> BLOCK_APPLICATION = register("block_application", BlockApplicationRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<AnvilRepairingRecipe>> REPAIRING = register("anvil_repair", AnvilRepairingRecipe.Serializer::new);

    private static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> register(String name, Supplier<RecipeSerializer<T>> recipeFactory) {
        return RECIPE_SERIALIZERS.register(name, recipeFactory);
    }
}
