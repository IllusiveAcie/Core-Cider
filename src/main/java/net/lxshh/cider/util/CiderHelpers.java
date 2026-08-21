package net.lxshh.cider.util;

import net.lxshh.cider.common.recipe.BlockInteractionRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static net.lxshh.cider.Cider.MOD_ID;

public class CiderHelpers {

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ItemInteractionResult blockInteractionRecipe(Player player, Level level, BlockState stateIn, BlockPos pos) {
        if (!player.blockPosition().equals(pos)) {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            final BlockInteractionRecipe recipe = BlockInteractionRecipe.getRecipe(stateIn, stack);

            if (recipe == null || recipe.isFake()) {
                return ItemInteractionResult.FAIL;
            }

            final BlockState stateOut = recipe.getOutputState();
            if (!player.isCreative()) {
                if (recipe.shouldConsumeItem()) {
                    stack.shrink(1);
                } else if (stack.isDamageableItem()){
                    stack.setDamageValue(stack.getDamageValue() - 1);
                }
            }

            level.setBlockAndUpdate(pos, stateOut);
            level.playLocalSound(pos, stateOut.getSoundType(level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0F, false);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, stateIn));
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return ItemInteractionResult.FAIL;
    }
}
