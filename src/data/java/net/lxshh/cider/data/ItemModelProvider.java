package net.lxshh.cider.data;

import net.lxshh.cider.Cider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Cider.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
