package net.lxshh.cider.registry;

import com.mojang.serialization.MapCodec;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifiers;
import net.lxshh.cider.Cider;
import net.lxshh.cider.common.recipe.outputs.AttachItemModifier;
import net.lxshh.cider.common.recipe.outputs.UnwrapAttachedModifier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderItemStackModifiers {
    public static final DeferredRegister<ItemStackModifierType<?>> MODIFIER_TYPES = DeferredRegister.create(ItemStackModifiers.KEY, Cider.MOD_ID);

    public static final Supplier<ItemStackModifierType<AttachItemModifier>> ATTACH_ITEM = register("attach_items", AttachItemModifier.CODEC, AttachItemModifier.STREAM_CODEC);
    public static final Supplier<ItemStackModifierType<UnwrapAttachedModifier>> UNWRAP_ITEM = register("unwrap_items", UnwrapAttachedModifier.INSTANCE);

    private static <T extends ItemStackModifier> Supplier<ItemStackModifierType<T>> register(String name, T singleInstance) {
        return MODIFIER_TYPES.register(name, () -> new ItemStackModifierType<>(MapCodec.unit(singleInstance), StreamCodec.unit(singleInstance)));
    }

    private static <T extends ItemStackModifier> Supplier<ItemStackModifierType<T>> register(String name, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return MODIFIER_TYPES.register(name, () -> new ItemStackModifierType<>(codec, streamCodec.cast()));
    }


}
