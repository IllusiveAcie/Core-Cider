package net.lxshh.modtemplate.registry;

import net.lxshh.modtemplate.ExampleMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENT = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ExampleMod.MOD_ID);
}
