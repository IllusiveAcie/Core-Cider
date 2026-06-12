package net.lxshh.cider.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record AttachedItemsComponent(List<ItemStack> items) {

    public static final Codec<AttachedItemsComponent> CODEC = ItemStack.CODEC.listOf()
            .xmap(AttachedItemsComponent::new, AttachedItemsComponent::items);

    public static final StreamCodec<RegistryFriendlyByteBuf, AttachedItemsComponent> STREAM_CODEC = ItemStack.LIST_STREAM_CODEC
            .map(AttachedItemsComponent::new, AttachedItemsComponent::items);

    public static AttachedItemsComponent of(List<ItemStack> list) {
        return new AttachedItemsComponent(List.copyOf(list));
    }

    public static AttachedItemsComponent add(AttachedItemsComponent existing, ItemStack stack) {
        List<ItemStack> merged = new ArrayList<>(existing.items());
        merged.add(stack);
        return new AttachedItemsComponent(merged);
    }

    public static void addTooltipInfo(ItemStack stack, List<Component> tooltip) {
        // ToDo: with Tooltip Rework
    }
}