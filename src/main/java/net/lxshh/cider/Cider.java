package net.lxshh.cider;

import net.lxshh.cider.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(Cider.MOD_ID)
public class Cider {
    public static final String MOD_ID = "cider";
    public static final String MOD_NAME = "CiderCore";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Cider(IEventBus modEventBus, ModContainer container) {
        CiderBlocks.BLOCKS.register(modEventBus);
        CiderItems.ITEMS.register(modEventBus);

        // container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        // container.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        // container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
