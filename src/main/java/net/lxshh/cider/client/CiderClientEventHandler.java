package net.lxshh.cider.client;

import net.lxshh.cider.Cider;
import net.lxshh.cider.client.screen.RestrictedDispenserScreen;
import net.lxshh.cider.client.screen.RestrictedHopperScreen;
import net.lxshh.cider.registry.CiderMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Cider.MOD_ID, value = Dist.CLIENT)
public class CiderClientEventHandler {

    @SubscribeEvent
    public static void onRegisterMenuScreen(RegisterMenuScreensEvent event) {
        event.register(CiderMenuTypes.HOPPER.get(), RestrictedHopperScreen::new);
        event.register(CiderMenuTypes.DISPENSER.get(), RestrictedDispenserScreen::new);
    }
}
