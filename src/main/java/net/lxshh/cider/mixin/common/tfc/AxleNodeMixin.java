package net.lxshh.cider.mixin.common.tfc;

import net.dries007.tfc.util.rotation.AxleNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AxleNode.class)
public class AxleNodeMixin {

    /**
     * Extend TFC's axle length to 12 which is a reasonable length
     */
    @ModifyConstant(
            method = "update",
            constant = @Constant(intValue = 5)
    )
    private int cider$modifyAxleLength(int original) {
        return 12;
    }

}
