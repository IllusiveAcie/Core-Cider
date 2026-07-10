package net.lxshh.cider.common.recipe.outputs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.lxshh.cider.registry.CiderItemStackModifiers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record RepairItemModifier(int repairAmount) implements ItemStackModifier {
    public static final MapCodec<RepairItemModifier> CODEC = Codec.INT.fieldOf("repair_amount").xmap(RepairItemModifier::of, RepairItemModifier::repairAmount);
    public static final StreamCodec<RegistryFriendlyByteBuf, RepairItemModifier> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(RepairItemModifier::of, RepairItemModifier::repairAmount).cast();

    public static RepairItemModifier of(int amount) {
        return new RepairItemModifier(amount);
    }

    @Override
    public ItemStack apply(ItemStack stack, ItemStack input, Context context) {
        if (stack.isDamageableItem() && stack.isDamaged()) {
            stack.setDamageValue(Math.max(0, stack.getDamageValue() - repairAmount));
        }
        return stack;
    }

    @Override
    public ItemStackModifierType<?> type() {
        return CiderItemStackModifiers.REPAIR_ITEM.get();
    }
}
