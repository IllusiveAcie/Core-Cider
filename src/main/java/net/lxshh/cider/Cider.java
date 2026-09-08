package net.lxshh.cider;

import com.mojang.logging.LogUtils;
import net.lxshh.cider.config.ServerConfig;
import net.lxshh.cider.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Cider.MOD_ID)
public class Cider {
    public static final String MOD_ID = "cider";
    public static final String MOD_NAME = "CiderCore";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Cider(IEventBus modEventBus, ModContainer container) {
        CiderBlocks.BLOCKS.register(modEventBus);
        CiderBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        CiderComponents.COMPONENTS.register(modEventBus);
        CiderItems.ITEMS.register(modEventBus);
        CiderMenuTypes.MENU_TYPES.register(modEventBus);
        CiderItemStackModifiers.MODIFIER_TYPES.register(modEventBus);
        CiderRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        CiderRecipeTypes.RECIPE_TYPES.register(modEventBus);
        CiderDataManagers.DATA_MANAGERS.register(modEventBus);
        CiderAttachments.ATTACHMENTS.register(modEventBus);

        // container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
         container.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        // container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

}
