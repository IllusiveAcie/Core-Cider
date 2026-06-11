package net.lxshh.cider.event;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

import java.util.List;

public class CiderEventHandler {
    public static void init(IEventBus bus) {
        bus.addListener(CiderEventHandler::modifyDefaultComponents);
    }

    // Make Colored steel items fire-resistant
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        List<Metal> fireResistantMetals = List.of(Metal.BLACK_STEEL, Metal.BLUE_STEEL, Metal.RED_STEEL);

        fireResistantMetals.forEach(metal -> {
            TFCItems.METAL_ITEMS.get(metal).forEach((itemType, itemId) ->
                    event.modify(itemId.get(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE))
            );

            TFCBlocks.METALS.get(metal).forEach((blockType, blockId) ->
                    event.modify(blockId.get().asItem(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE))
            );
        });

        event.modify(TFCItems.METAL_ITEMS.get(Metal.WEAK_BLUE_STEEL).get(Metal.ItemType.INGOT).get(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE));
        event.modify(TFCItems.METAL_ITEMS.get(Metal.WEAK_RED_STEEL).get(Metal.ItemType.INGOT).get(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE));
        event.modify(TFCItems.METAL_ITEMS.get(Metal.HIGH_CARBON_BLACK_STEEL).get(Metal.ItemType.INGOT).get(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE));
        event.modify(TFCItems.METAL_ITEMS.get(Metal.HIGH_CARBON_BLUE_STEEL).get(Metal.ItemType.INGOT).get(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE));
        event.modify(TFCItems.METAL_ITEMS.get(Metal.HIGH_CARBON_RED_STEEL).get(Metal.ItemType.INGOT).get(), builder -> builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE));
    }
}
