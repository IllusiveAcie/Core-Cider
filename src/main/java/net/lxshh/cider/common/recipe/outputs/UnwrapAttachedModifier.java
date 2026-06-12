package net.lxshh.cider.common.recipe.outputs;

import net.dries007.tfc.common.recipes.RecipeHelpers;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.lxshh.cider.common.components.AttachedItemsComponent;
import net.lxshh.cider.registry.CiderComponents;
import net.lxshh.cider.registry.CiderItemStackModifiers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public enum UnwrapAttachedModifier implements ItemStackModifier {
    INSTANCE;

    @Override
    public ItemStack apply(ItemStack itemStack, ItemStack input, Context context) {
        AttachedItemsComponent component = input.get(CiderComponents.ITEM_ATTACHED);
        if (component != null) {
            Player player = RecipeHelpers.getCraftingPlayer();
            if (player != null && context == Context.DEFAULT) {
                component.items().forEach(stack ->
                    ItemHandlerHelper.giveItemToPlayer(player, stack.copy())
                );
            }
        }
        return itemStack;
    }

    @Override
    public ItemStackModifierType<?> type() {
        return CiderItemStackModifiers.UNWRAP_ITEM.get();
    }
}
