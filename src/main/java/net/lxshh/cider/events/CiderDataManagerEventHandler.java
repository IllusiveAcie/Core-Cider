package net.lxshh.cider.events;

import net.lxshh.cider.Cider;
import net.lxshh.cider.network.CiderDataManagerSyncPacket;
import net.lxshh.cider.registry.CiderDataManagers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = Cider.MOD_ID)
public class CiderDataManagerEventHandler {

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        event.register(CiderDataManagers.REGISTRY);
    }

    @SubscribeEvent
    public static void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Cider.MOD_ID);

        registrar.playToClient(CiderDataManagerSyncPacket.TYPE, CiderDataManagerSyncPacket.CODEC, (packet, context) -> context.enqueueWork(() -> packet.handle(context.connection().isMemoryConnection())));
    }

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        CiderDataManagers.REGISTRY.forEach(event::addListener);
    }

    @SubscribeEvent
    public static void onDataPackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            PacketDistributor.sendToAllPlayers(new CiderDataManagerSyncPacket());
        } else {
            PacketDistributor.sendToPlayer(event.getPlayer(), new CiderDataManagerSyncPacket());
        }
    }
}
