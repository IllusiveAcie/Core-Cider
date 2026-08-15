package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Cider.MOD_ID);


    private static <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name) {
        return RECIPE_TYPES.register(name, () -> new RecipeType<T>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
