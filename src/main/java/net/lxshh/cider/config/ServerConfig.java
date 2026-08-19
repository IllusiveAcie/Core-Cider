package net.lxshh.cider.config;

import net.dries007.tfc.common.component.size.Size;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.EnumValue<Size> hopperMaximumItemSize;
    public static final ModConfigSpec.EnumValue<Size> dispenserMaximumItemSize;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        hopperMaximumItemSize = builder.comment("The largest (inclusive) size of an item that is allowed in a hopper.").defineEnum("chestMaximumItemSize", Size.LARGE);
        dispenserMaximumItemSize = builder.comment("The largest (inclusive) size of an item that is allowed in a dispenser.").defineEnum("dispenserMaximumItemSize", Size.LARGE);

        SPEC = builder.build();
    }
}
