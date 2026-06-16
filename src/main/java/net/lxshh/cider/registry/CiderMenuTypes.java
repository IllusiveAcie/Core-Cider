package net.lxshh.cider.registry;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.lxshh.cider.Cider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Cider.MOD_ID);

    private static <T extends InventoryBlockEntity<?>, C extends BlockEntityContainer<T>> Supplier<MenuType<C>> registerBlock(String name, Supplier<BlockEntityType<T>> type, BlockEntityContainer.Factory<T, C> factory) {
        return RegistrationHelpers.registerBlockEntityContainer(MENU_TYPES, name, type, factory);
    }
}
