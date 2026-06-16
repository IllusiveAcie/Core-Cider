package net.lxshh.cider;

import net.lxshh.cider.client.ClientEvents;
import net.lxshh.cider.config.ClientConfig;
import net.lxshh.cider.config.CommonConfig;
import net.lxshh.cider.config.ServerConfig;
import net.lxshh.cider.event.CiderEventHandler;
import net.lxshh.cider.event.CiderNeoEventHandler;
import net.lxshh.cider.registry.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(Cider.MOD_ID)
public class Cider {
    public static final String MOD_ID = "cider";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Cider(IEventBus modEventBus, ModContainer container) {
        CiderBlocks.BLOCKS.register(modEventBus);
        CiderBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        CiderItems.ITEMS.register(modEventBus);
        CiderComponents.COMPONENTS.register(modEventBus);
        CiderRecipeTypes.RECIPE_TYPES.register(modEventBus);
        CiderRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        CiderMenuTypes.MENU_TYPES.register(modEventBus);

        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientEvents.init(container, modEventBus);
        }

        CiderNeoEventHandler.init();
        CiderEventHandler.init(modEventBus);
    }

    public static ResourceLocation loc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

}
