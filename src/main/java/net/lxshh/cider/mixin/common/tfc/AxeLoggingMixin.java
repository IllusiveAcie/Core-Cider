package net.lxshh.cider.mixin.common.tfc;

import net.dries007.tfc.util.AxeLoggingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeLoggingHelper.class)
public class AxeLoggingMixin {

    /**
     * Disable axe logging
     */
    @Inject(
            method = "shouldLog",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cider$disableLogging(LevelAccessor level, BlockPos pos, BlockState state, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

}
