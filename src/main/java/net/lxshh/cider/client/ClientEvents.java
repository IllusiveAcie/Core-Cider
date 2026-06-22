package net.lxshh.cider.client;

import net.lxshh.cider.Cider;
import net.lxshh.cider.client.screen.RestrictedHopperScreen;
import net.lxshh.cider.common.items.MagneticCompassItem;
import net.lxshh.cider.registry.CiderItems;
import net.lxshh.cider.registry.CiderMenuTypes;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientEvents {
    public static void init (ModContainer container, IEventBus modEventBus) {
        modEventBus.addListener(ClientEvents::onClientSetup);
        modEventBus.addListener(ClientEvents::onRegisterMenuScreen);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(CiderItems.MAGNETIC_COMPASS.get(), Cider.loc("angle"),
                new CompassItemPropertyFunction((clientLevel, itemStack, entity) -> MagneticCompassItem.getNorthPos(clientLevel))
        );
    }

    public static void onRegisterMenuScreen(RegisterMenuScreensEvent event) {
        event.register(CiderMenuTypes.HOPPER.get(), RestrictedHopperScreen::new);
    }
}
