package net.lxshh.cider.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.recipes.BlockRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.network.StreamCodecs;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import net.dries007.tfc.world.Codecs;
import net.lxshh.cider.registry.CiderRecipeSerializers;
import net.lxshh.cider.registry.CiderRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class BlockInteractionRecipe extends BlockRecipe {
    public static final IndirectHashCollection<Block, BlockInteractionRecipe> CACHE = IndirectHashCollection.createForRecipe(r -> r.getBlockIngredient().blocks(), CiderRecipeTypes.BLOCK_INTERACTION);

    public static BlockInteractionRecipe getRecipe(BlockState state, ItemStack held) {
        for (BlockInteractionRecipe recipe : CACHE.getAll(state.getBlock())) {
            if (recipe.matches(state, held)) {
                return recipe;
            }
        }
        return null;
    }

    private final BlockState outputState;
    private final Ingredient itemIngredient;
    private final boolean consumeItem;
    private final boolean fake;

    public BlockInteractionRecipe(BlockIngredient ingredient, BlockState outputState, Ingredient itemIngredient, boolean consumeItem, boolean fake) {
        super(ingredient, Optional.of(outputState));
        this.outputState = outputState;
        this.itemIngredient = itemIngredient;
        this.consumeItem = consumeItem;
        this.fake = fake;
    }

    public boolean matches(BlockState state, ItemStack itemStack) {
        return ingredient.test(state) && itemIngredient.test(itemStack);
    }

    public BlockState getOutputState() {
        return outputState;
    }

    public Ingredient getItemIngredient() {
        return itemIngredient;
    }

    public boolean shouldConsumeItem() {
        return consumeItem;
    }

    public boolean isFake() {
        return fake;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CiderRecipeSerializers.BLOCK_INTERACTION.get();
    }

    @Override
    public RecipeType<?> getType() {
        return CiderRecipeTypes.BLOCK_INTERACTION.get();
    }

    public static class Serializer implements RecipeSerializer<BlockInteractionRecipe> {
        public static final MapCodec<BlockInteractionRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                BlockIngredient.CODEC.fieldOf("block_ingredient").forGetter(BlockInteractionRecipe::getBlockIngredient),
                Codecs.BLOCK_STATE.fieldOf("output_block").forGetter(BlockInteractionRecipe::getOutputState),
                Ingredient.CODEC.fieldOf("item_ingredient").forGetter(BlockInteractionRecipe::getItemIngredient),
                Codec.BOOL.optionalFieldOf("consume_item", false).forGetter(BlockInteractionRecipe::shouldConsumeItem),
                Codec.BOOL.optionalFieldOf("is_fake", false).forGetter(BlockInteractionRecipe::isFake)
        ).apply(i, BlockInteractionRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BlockInteractionRecipe> STREAM_CODEC = StreamCodec.composite(
                BlockIngredient.STREAM_CODEC, r -> r.ingredient,
                StreamCodecs.BLOCK_STATE, r -> r.outputState,
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.itemIngredient,
                ByteBufCodecs.BOOL, r -> r.consumeItem,
                ByteBufCodecs.BOOL, r -> r.fake,
                BlockInteractionRecipe::new
        );

        @Override
        public MapCodec<BlockInteractionRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlockInteractionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
