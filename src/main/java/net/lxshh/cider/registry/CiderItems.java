package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.lxshh.cider.common.items.MagneticCompassItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Cider.MOD_ID);

    public static final DeferredItem<Item> MAGNETIC_COMPASS = register("magnetic_compass", () -> new MagneticCompassItem(new Item.Properties().stacksTo(1)));

    private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> itemFactory) {
        return ITEMS.register(name, itemFactory);
    }

    private static DeferredItem<Item> register(String name) {
        return ITEMS.registerSimpleItem(name);
    }
}
