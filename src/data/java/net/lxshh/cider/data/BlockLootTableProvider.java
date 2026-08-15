package net.lxshh.cider.data;

import net.lxshh.cider.registry.CiderBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class BlockLootTableProvider extends BlockLootSubProvider {
    protected BlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CiderBlocks.BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .collect(Collectors.toSet());
    }
}
