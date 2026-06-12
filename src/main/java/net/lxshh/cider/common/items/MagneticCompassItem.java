package net.lxshh.cider.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagneticCompassItem extends Item {
    public MagneticCompassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.displayClientMessage(Component.translatable("tooltip.cider.player_pos",
                player.getBlockX(),
                player.getBlockZ()
        ).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_GREEN)), true);

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    public static GlobalPos getNorthPos(Level level) {
        return level.dimensionType().natural() ? GlobalPos.of(level.dimension(), new BlockPos(0, 0, -10000000)) : null;
    }
}
