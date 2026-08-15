package net.lxshh.cider.network;

import net.dries007.tfc.util.data.DataManager;
import net.lxshh.cider.Cider;
import net.lxshh.cider.registry.CiderDataManagers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CiderDataManagerSyncPacket(List<Entry<?>> values) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CiderDataManagerSyncPacket> TYPE = new Type<>(Cider.identifier("data_managers"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CiderDataManagerSyncPacket> CODEC = ByteBufCodecs.registry(CiderDataManagers.KEY)
            .<Entry<?>>dispatch(Entry::manager, CiderDataManagerSyncPacket::streamCodec)
            .apply(ByteBufCodecs.list())
            .map(CiderDataManagerSyncPacket::new, CiderDataManagerSyncPacket::values);

    private static <T> StreamCodec<RegistryFriendlyByteBuf, Entry<T>> streamCodec(DataManager<T> manager) {
        return ByteBufCodecs.<RegistryFriendlyByteBuf, ResourceLocation, T, Map<ResourceLocation, T>>map(HashMap::new, ResourceLocation.STREAM_CODEC, manager.streamCodec())
                .map(e -> new Entry<>(manager, e), e -> e.values);
    }

    public CiderDataManagerSyncPacket() {
        this(CiderDataManagers.REGISTRY.stream().filter(DataManager::isSynced).<Entry<?>>map(Entry::new).toList());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(boolean isMemoryConnection) {
        if (isMemoryConnection) {
            Cider.LOGGER.info("Ignoring DataManager sync on logical server");
            return;
        }
        for (Entry<?> v : values) {
            v.handle();
        }
    }

    record Entry<T>(
            DataManager<T> manager,
            Map<ResourceLocation, T> values
    ) {
        Entry(DataManager<T> manager) {
            this(manager, manager.getElements());
        }

        void handle() {
            manager.bindValues(values);
        }
    }
}
