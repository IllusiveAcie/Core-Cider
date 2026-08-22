package net.lxshh.cider.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.lxshh.cider.registry.CiderRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ShapelessStackableCraftingRecipe extends CustomRecipe {
    private final Ingredient primaryIngredient;
    private final Ingredient stackableIngredient;
    private final int min;
    private final int max;
    private final ItemStackProvider result;

    public ShapelessStackableCraftingRecipe(Ingredient primaryIngredient, Ingredient stackableIngredient, int min, int max, ItemStackProvider result) {
        super(CraftingBookCategory.MISC);
        this.primaryIngredient = primaryIngredient;
        this.stackableIngredient = stackableIngredient;
        this.min = min;
        this.max = max;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean hasPrimary = false;
        int count = 0;

        for (int i = 0; i < input.height(); i++) {
            for (int j = 0; j < input.width(); j++) {
                ItemStack stack = input.getItem(j, i);

                if (!stack.isEmpty()) {
                    if (primaryIngredient.test(stack)) {
                        if (hasPrimary) {
                            return false;
                        }
                        hasPrimary = true;
                    }

                    if (stackableIngredient.test(stack)) {
                        count++;
                    }
                }

            }
        }

        return hasPrimary && count >= min && count <= max ;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
        int count = 0;

        for (int i = 0; i < input.height(); i++) {
            for (int j = 0; j < input.width(); j++) {
                ItemStack stack = input.getItem(j, i);
                if (stackableIngredient.test(stack)) {
                    count++;
                }
            }
        }

        ItemStack newResult = result.stack().copy();
        newResult.setCount(count);
        return newResult;
    }

    public Ingredient getPrimaryIngredient() {
        return primaryIngredient;
    }

    public Ingredient getStackableIngredient() {
        return stackableIngredient;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public ItemStackProvider getResult() {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CiderRecipeSerializers.SHAPELESS_STACKABLE.get();
    }

    public static class Serializer implements RecipeSerializer<ShapelessStackableCraftingRecipe> {

        public static final MapCodec<ShapelessStackableCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("primary_ingredient").forGetter(ShapelessStackableCraftingRecipe::getPrimaryIngredient),
                Ingredient.CODEC.fieldOf("secondary_ingredient").forGetter(ShapelessStackableCraftingRecipe::getStackableIngredient),
                Codec.INT.optionalFieldOf("min_amount", 1).forGetter(ShapelessStackableCraftingRecipe::getMin),
                Codec.INT.optionalFieldOf("max_amount", 1).forGetter(ShapelessStackableCraftingRecipe::getMax),
                ItemStackProvider.CODEC.fieldOf("result").forGetter(ShapelessStackableCraftingRecipe::getResult)
        ).apply(i, ShapelessStackableCraftingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessStackableCraftingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, ShapelessStackableCraftingRecipe::getPrimaryIngredient,
                Ingredient.CONTENTS_STREAM_CODEC, ShapelessStackableCraftingRecipe::getStackableIngredient,
                ByteBufCodecs.INT, ShapelessStackableCraftingRecipe::getMin,
                ByteBufCodecs.INT, ShapelessStackableCraftingRecipe::getMax,
                ItemStackProvider.STREAM_CODEC, ShapelessStackableCraftingRecipe::getResult,
                ShapelessStackableCraftingRecipe::new
        );

        @Override
        public MapCodec<ShapelessStackableCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessStackableCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
