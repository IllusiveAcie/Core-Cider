package net.lxshh.modtemplate.registry;

import net.lxshh.modtemplate.ExampleMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExampleMod.MOD_ID);

    private static <T extends Block> DeferredBlock<T> registerNoItem(String name, Supplier<T> blockFactory) {
        return BLOCKS.register(name, blockFactory);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> blockFactory) {
        DeferredBlock<T> block = BLOCKS.register(name, blockFactory);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}
