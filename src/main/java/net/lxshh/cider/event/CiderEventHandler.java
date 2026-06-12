package net.lxshh.cider.event;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.lxshh.cider.common.recipe.BlockApplicationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

public class CiderEventHandler {
    public static void init(IEventBus bus) {
        bus.addListener(CiderEventHandler::modifyDefaultComponents);
        bus.addListener(CiderEventHandler::onRightClick);
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

    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack held = player.getMainHandItem();

        BlockApplicationRecipe recipe = BlockApplicationRecipe.getRecipe(state, held);
        if (recipe == null || recipe.isFake()) return;

        BlockApplicationRecipe.doRecipe(player, level, pos, recipe);
        event.setCanceled(true);
    }
}
