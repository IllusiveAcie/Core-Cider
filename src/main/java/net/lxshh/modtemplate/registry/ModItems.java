package net.lxshh.modtemplate.registry;

import net.lxshh.modtemplate.ExampleMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MOD_ID);

    private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> itemFactory) {
        return ITEMS.register(name, itemFactory);
    }

    private static DeferredItem<Item> register(String name) {
        return ITEMS.registerSimpleItem(name);
    }
}
