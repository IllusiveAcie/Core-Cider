package net.lxshh.cider.mixin.common.tfc;

import net.dries007.tfc.util.rotation.AxleNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AxleNode.class)
public class AxleNodeMixin {

    /**
     * Extend the axle length from 5 to 12
     */
    @ModifyConstant(
            method = "update",
            constant = @Constant(intValue = 5)
    )
    private int cider$modifyMaxAxleLength(int original) {
        return 12;
    }
}
