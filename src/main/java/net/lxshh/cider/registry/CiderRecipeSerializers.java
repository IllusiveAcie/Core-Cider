package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.lxshh.cider.common.recipe.BlockApplicationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Cider.MOD_ID);

    public static final Supplier<RecipeSerializer<BlockApplicationRecipe>> BLOCK_APPLICATION = RECIPE_SERIALIZERS.register("block_application", BlockApplicationRecipe.Serializer::new);
}
