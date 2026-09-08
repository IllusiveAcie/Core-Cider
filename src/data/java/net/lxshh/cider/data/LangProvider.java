package net.lxshh.cider.data;

import net.lxshh.cider.Cider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output) {
        super(output, Cider.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("cider.component.item_damaged", "%s took %d damage.");
    }
}
