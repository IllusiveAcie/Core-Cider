package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Cider.MOD_ID);


    private static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> register(String name, Supplier<RecipeSerializer<T>> recipeFactory) {
        return RECIPE_SERIALIZERS.register(name, recipeFactory);
    }
}
