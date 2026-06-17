package net.lxshh.cider.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.recipes.WeldingRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.lxshh.cider.registry.CiderRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public class AnvilRepairingRecipe extends WeldingRecipe {
    private final int repairAmount;

    public AnvilRepairingRecipe(Ingredient firstInput, Ingredient secondInput, int tier, int repairAmount) {
        super(firstInput, secondInput, tier, ItemStackProvider.of(), Behavior.IGNORE);
        this.repairAmount = repairAmount;
    }

    @Override
    public ItemStack assemble(Inventory input) {
        ItemStack repairItem = input.getMain().copy();

        if (!repairItem.isEmpty() && repairItem.isDamageableItem()) {
            int newDamage = Math.max(0, repairItem.getDamageValue() - repairAmount);
            repairItem.setDamageValue(newDamage);
        }

        return repairItem;
    }

    public int getRepairAmount() {
        return repairAmount;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return getFirstInput().getItems()[0] != null ? getFirstInput().getItems()[0] : ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CiderRecipeSerializers.REPAIRING.get();
    }

    public static class Serializer implements RecipeSerializer<AnvilRepairingRecipe> {
        public static final MapCodec<AnvilRepairingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("primary_item").forGetter(WeldingRecipe::getFirstInput),
                Ingredient.CODEC_NONEMPTY.fieldOf("repair_item").forGetter(WeldingRecipe::getSecondInput),
                Codec.INT.optionalFieldOf("tier", 1).forGetter(WeldingRecipe::getTier),
                Codec.INT.optionalFieldOf("repair_amount", 380).forGetter(AnvilRepairingRecipe::getRepairAmount)
        ).apply(i, AnvilRepairingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AnvilRepairingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, WeldingRecipe::getFirstInput,
                Ingredient.CONTENTS_STREAM_CODEC, WeldingRecipe::getSecondInput,
                ByteBufCodecs.INT, WeldingRecipe::getTier,
                ByteBufCodecs.INT, AnvilRepairingRecipe::getRepairAmount,
                AnvilRepairingRecipe::new
        );

        @Override
        public MapCodec<AnvilRepairingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AnvilRepairingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
