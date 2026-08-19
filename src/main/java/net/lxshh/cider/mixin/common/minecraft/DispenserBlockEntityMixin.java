package net.lxshh.cider.mixin.common.minecraft;

import net.lxshh.cider.common.container.RestrictedDispenserMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DispenserBlockEntity.class)
public class DispenserBlockEntityMixin {

    /**
     * Override the menu to restrict what items can be placed in the slots
     */
    @Inject(
            method = "createMenu",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cider$useRestrictedContainer(int id, Inventory player, CallbackInfoReturnable<AbstractContainerMenu> cir) {
        DispenserBlockEntity self = (DispenserBlockEntity) (Object) this;
        cir.setReturnValue(new RestrictedDispenserMenu(id, player, self));
    }
}
