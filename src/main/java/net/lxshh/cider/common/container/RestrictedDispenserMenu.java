package net.lxshh.cider.common.container;

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

public class RestrictedDispenserMenu extends AbstractContainerMenu {
    private final Container dispenser;

    public RestrictedDispenserMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf) {
        this(containerId, playerInv, new SimpleContainer(9));
    }

    public RestrictedDispenserMenu(int containerId, Inventory playerInv, Container container) {
        super(CiderMenuTypes.DISPENSER.get(), containerId);
        this.dispenser = container;
        checkContainerSize(container, 9);
        container.startOpen(playerInv.player);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new DispenserRestrictedSlot(container, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
        }

    }
    public boolean stillValid(Player player) {
        return this.dispenser.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack copyStack = slot.getItem();
            itemstack = copyStack.copy();
            if (index < 9) {
                if (!this.moveItemStackTo(copyStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(copyStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (copyStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (copyStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, copyStack);
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.dispenser.stopOpen(player);
    }

    public static class DispenserRestrictedSlot extends Slot {
        public DispenserRestrictedSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return super.mayPlace(stack) && ItemSizeManager.get(stack).getSize(stack).isEqualOrSmallerThan(ServerConfig.dispenserMaximumItemSize.get());
        }
    }
}
