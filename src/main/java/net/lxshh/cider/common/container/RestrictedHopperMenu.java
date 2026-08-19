package net.lxshh.cider.common.container;

import net.dries007.tfc.common.blockentities.TFCChestBlockEntity;
import net.dries007.tfc.common.component.size.ItemSizeDefinition;
import net.dries007.tfc.common.component.size.ItemSizeManager;
import net.lxshh.cider.config.ServerConfig;
import net.lxshh.cider.registry.CiderMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RestrictedHopperMenu extends AbstractContainerMenu {
    private final Container hopper;
    public RestrictedHopperMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf) {
        this(containerId, playerInv, new SimpleContainer(5));
    }

    public RestrictedHopperMenu(int containerId, Inventory playerInv, Container container) {
        super(CiderMenuTypes.HOPPER.get(), containerId);
        this.hopper = container;
        checkContainerSize(container, 5);
        container.startOpen(playerInv.player);

        for(int j = 0; j < 5; ++j) {
            this.addSlot(new HopperRestrictedSlot(container, j, 44 + j * 18, 20));
        }

        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 109));
        }
    }

    public boolean stillValid(Player player) {
        return this.hopper.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack copyStack = slot.getItem();
            itemstack = copyStack.copy();
            if (index < this.hopper.getContainerSize()) {
                if (!this.moveItemStackTo(copyStack, this.hopper.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(copyStack, 0, this.hopper.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (copyStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.hopper.stopOpen(player);
    }

    public static class HopperRestrictedSlot extends Slot {
        public HopperRestrictedSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return super.mayPlace(stack) && ItemSizeManager.get(stack).getSize(stack).isEqualOrSmallerThan(ServerConfig.hopperMaximumItemSize.get());
        }
    }
}