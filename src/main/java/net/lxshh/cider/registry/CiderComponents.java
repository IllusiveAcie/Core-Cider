package net.lxshh.cider.registry;

import com.mojang.serialization.Codec;
import net.lxshh.cider.Cider;
import net.lxshh.cider.common.components.AttachedItemsComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Cider.MOD_ID);

    public static final Supplier<DataComponentType<AttachedItemsComponent>> ITEM_ATTACHED = register("item_attached", AttachedItemsComponent.CODEC, AttachedItemsComponent.STREAM_CODEC);

    public static <T> Supplier<DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return COMPONENTS.register(name, () -> new DataComponentType.Builder<T>()
                .persistent(codec)
                .networkSynchronized(streamCodec)
                .build()
        );
    }
}
