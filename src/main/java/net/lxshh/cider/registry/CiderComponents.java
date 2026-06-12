package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.lxshh.cider.common.components.AttachedItemsComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENT = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Cider.MOD_ID);

    public static final Supplier<DataComponentType<AttachedItemsComponent>> ITEM_ATTACHED = COMPONENT.register("item_attached", () ->
            DataComponentType.<AttachedItemsComponent>builder()
                    .persistent(AttachedItemsComponent.CODEC)
                    .networkSynchronized(AttachedItemsComponent.STREAM_CODEC)
                    .build()
    );
}
