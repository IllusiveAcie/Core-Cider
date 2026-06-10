package net.lxshh.modtemplate;

import net.lxshh.modtemplate.client.ClientEvents;
import net.lxshh.modtemplate.config.ClientConfig;
import net.lxshh.modtemplate.config.CommonConfig;
import net.lxshh.modtemplate.config.ServerConfig;
import net.lxshh.modtemplate.registry.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(ExampleMod.MOD_ID)
public class ExampleMod {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus modEventBus, ModContainer container) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModComponents.COMPONENT.register(modEventBus);

        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientEvents.init(container, modEventBus);
        }
    }

    public static ResourceLocation loc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

}
