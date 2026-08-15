package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Cider.MOD_ID);

    private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> itemFactory) {
        return ITEMS.register(name, itemFactory);
    }

    private static DeferredItem<Item> register(String name) {
        return ITEMS.registerSimpleItem(name);
    }
}
