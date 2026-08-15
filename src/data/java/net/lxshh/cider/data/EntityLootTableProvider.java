package net.lxshh.cider.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;

public class EntityLootTableProvider extends EntityLootSubProvider {
    protected EntityLootTableProvider(HolderLookup.Provider provider) {
        super(FeatureFlagSet.of(), provider);
    }

    @Override
    public void generate() {

    }
}
