package net.lxshh.cider.mixin.common.minecraft;

import net.dries007.tfc.common.blockentities.TFCChestBlockEntity;
import net.lxshh.cider.common.container.RestrictedHopperMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    /**
     * Restrict what items can be picked by the hopper based on size
     */
    @Inject(
            method = "addItem(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/item/ItemEntity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cider$addItem(Container container, ItemEntity item, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = item.getItem();
        if (!TFCChestBlockEntity.isValid(stack)) {
            cir.setReturnValue(false);
        }
    }

    /**
     * Override the menu to restrict what items can be placed in the slots
     */
    @Inject(
            method = "createMenu",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cider$UseRestrictedContainer(int id, Inventory player, CallbackInfoReturnable<AbstractContainerMenu> cir) {
        HopperBlockEntity self = (HopperBlockEntity) (Object) this;
        cir.setReturnValue(new RestrictedHopperMenu(id, player, self));
    }
}
