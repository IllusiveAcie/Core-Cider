package net.lxshh.cider.common.data;

import net.dries007.tfc.util.data.DataManager;
import net.lxshh.cider.Cider;
import net.lxshh.cider.common.data.nutrition.NutritionEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class CiderDataManagers {
    public static final ResourceKey<Registry<DataManager<?>>> KEY = ResourceKey.createRegistryKey(Cider.loc("data_manager"));
    public static final Registry<DataManager<?>> REGISTRY = new RegistryBuilder<>(KEY).sync(true).create();

    public static final DeferredRegister<DataManager<?>> DATA_MANAGERS = DeferredRegister.create(KEY, Cider.MOD_ID);

    static {
        register(NutritionEffect.MANAGER);
    }

    private static void register(DataManager<?> manager) {
        DATA_MANAGERS.register(manager.getName(), () -> manager);
    }

}
