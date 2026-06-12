package net.lxshh.cider.common.recipe.outputs;

import com.mojang.serialization.MapCodec;
import net.dries007.tfc.common.component.food.FoodCapability;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.lxshh.cider.common.components.AttachedItemsComponent;
import net.lxshh.cider.registry.CiderComponents;
import net.lxshh.cider.registry.CiderItemStackModifiers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record AttachItemModifier(ItemStack stack) implements ItemStackModifier {
    public static final MapCodec<AttachItemModifier> CODEC = ItemStack.CODEC.fieldOf("stack").xmap(AttachItemModifier::of, AttachItemModifier::stack);
    public static final StreamCodec<RegistryFriendlyByteBuf, AttachItemModifier> STREAM_CODEC = ItemStack.STREAM_CODEC.map(AttachItemModifier::of, AttachItemModifier::stack);

    public static AttachItemModifier of(ItemStack stack) {
        return new AttachItemModifier(FoodCapability.setTransientNonDecaying(stack));
    }

    @Override
    public ItemStack apply(ItemStack itemStack, ItemStack input, Context context) {
        AttachedItemsComponent existing = itemStack.get(CiderComponents.ITEM_ATTACHED);
        if (existing != null) {
            itemStack.set(CiderComponents.ITEM_ATTACHED, AttachedItemsComponent.add(existing, stack.copy()));
        } else {
            itemStack.set(CiderComponents.ITEM_ATTACHED, AttachedItemsComponent.of(List.of(stack.copy())));
        }
        return itemStack;
    }

    @Override
    public ItemStackModifierType<?> type() {
        return CiderItemStackModifiers.ATTACH_ITEM.get();
    }
}
