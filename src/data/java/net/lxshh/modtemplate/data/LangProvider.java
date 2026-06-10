package net.lxshh.modtemplate.data;

import net.lxshh.modtemplate.ExampleMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output) {
        super(output, ExampleMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }
}
