package net.lxshh.cider.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.recipes.INoopInputRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.network.StreamCodecs;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import net.dries007.tfc.world.Codecs;
import net.lxshh.cider.registry.CiderRecipeSerializers;
import net.lxshh.cider.registry.CiderRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockApplicationRecipe implements INoopInputRecipe {

    public static final IndirectHashCollection<Block, BlockApplicationRecipe> CACHE = IndirectHashCollection.createForRecipe(r -> r.blockIngredient.blocks(), CiderRecipeTypes.BLOCK_APPLICATION);

    public static void doRecipe(Player player, Level level, BlockPos pos, BlockApplicationRecipe recipe) {
        @Nullable BlockState newState = computeResult(player, recipe, true);
        if (newState == null) {
            return;
        }

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, newState);
        }
        player.playSound(newState.getSoundType(level, pos, player).getPlaceSound(), 1.0F, 1.0F);
    }

    @Nullable
    public static BlockState computeResult(Player player, BlockApplicationRecipe recipe, boolean informWhy) {
        final ItemStack held = player.getMainHandItem();

        if (recipe == null || recipe.isFake()) return null;

        if (!player.level().isClientSide()) {
            if (recipe.shouldConsumeItem()) {
                held.shrink(1);
            } else if (recipe.shouldDamageIngredient()) {
                held.hurtAndBreak(1, player, Player.getSlotForHand(player.getUsedItemHand()));
            }
        }

        return recipe.output;
    }

    public static BlockApplicationRecipe getRecipe(BlockState state, ItemStack held) {
        for (BlockApplicationRecipe recipe : CACHE.getAll(state.getBlock())) {
            if (recipe.matches(state, held)) {
                return recipe;
            }
        }
        return null;
    }

    private static void complain(Player player, String message) {
        player.displayClientMessage(Component.translatable("cider.block_application." + message), true);
    }

    private final BlockIngredient blockIngredient;
    private final BlockState output;
    private final Ingredient itemIngredient;
    private final Boolean isFake;
    private final boolean consumeItem;
    private final boolean damageIngredient;

    public BlockApplicationRecipe(BlockIngredient blockIngredient, BlockState output, Ingredient itemIngredient, Boolean isFake, boolean consumeItem, boolean damageIngredient) {
        this.blockIngredient = blockIngredient;
        this.output = output;
        this.itemIngredient = itemIngredient;
        this.isFake = isFake;
        this.consumeItem = consumeItem;
        this.damageIngredient = damageIngredient;
    }

    public boolean matches(BlockState state, ItemStack ingredient) {
        return blockIngredient.test(state) && itemIngredient.test(ingredient);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(output.getBlock());
    }

    public Ingredient getItemIngredient() {
        return itemIngredient;
    }

    public BlockIngredient getBlockIngredient() {
        return blockIngredient;
    }

    public Boolean isFake() {
        return isFake;
    }

    public boolean shouldConsumeItem() {
        return consumeItem;
    }

    public boolean shouldDamageIngredient() {
        return damageIngredient;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CiderRecipeSerializers.BLOCK_APPLICATION.get();
    }

    @Override
    public RecipeType<?> getType() {
        return CiderRecipeTypes.BLOCK_APPLICATION.get();
    }

    public static class Serializer implements RecipeSerializer<BlockApplicationRecipe> {
        public static final MapCodec<BlockApplicationRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                BlockIngredient.CODEC.fieldOf("block_ingredient").forGetter(r -> r.blockIngredient),
                Codecs.BLOCK_STATE.fieldOf("result_block").forGetter(r -> r.output),
                Ingredient.CODEC.fieldOf("item_ingredient").forGetter(r -> r.itemIngredient),
                Codec.BOOL.optionalFieldOf("is_fake", false).forGetter(r -> r.isFake),
                Codec.BOOL.optionalFieldOf("consume_item", true).forGetter(r -> r.consumeItem),
                Codec.BOOL.optionalFieldOf("damage_ingredient", true).forGetter(r -> r.damageIngredient)
        ).apply(i, BlockApplicationRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BlockApplicationRecipe> STREAM_CODEC = StreamCodec.composite(
                BlockIngredient.STREAM_CODEC, r -> r.blockIngredient,
                StreamCodecs.BLOCK_STATE, r -> r.output,
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.itemIngredient,
                ByteBufCodecs.BOOL, r -> r.isFake,
                ByteBufCodecs.BOOL, r -> r.consumeItem,
                ByteBufCodecs.BOOL, r -> r.damageIngredient,
                BlockApplicationRecipe::new
        );

        @Override
        public MapCodec<BlockApplicationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlockApplicationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
