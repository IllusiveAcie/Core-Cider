package net.lxshh.cider.registry;

import net.lxshh.cider.Cider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CiderBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Cider.MOD_ID);

    private static <T extends Block> DeferredBlock<T> registerNoItem(String name, Supplier<T> blockFactory) {
        return BLOCKS.register(name, blockFactory);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> blockFactory) {
        DeferredBlock<T> block = BLOCKS.register(name, blockFactory);
        CiderItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}
