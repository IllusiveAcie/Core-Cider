package net.lxshh.cider.mixin.common.tfc;

import net.dries007.tfc.common.blockentities.PotBlockEntity;
import net.dries007.tfc.common.recipes.PotRecipe;
import net.dries007.tfc.common.recipes.SimplePotRecipe;
import net.dries007.tfc.common.recipes.SoupPotRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotBlockEntity.class)
public class PotBlockEntityMixin {

    @Shadow
    private @Nullable PotRecipe cachedRecipe;

    private final PotBlockEntity self = (PotBlockEntity) (Object) this;

    /**
     * Prioritize SimplePotRecipe over SoupPotRecipe.
     */
    @Inject(
            method = "updateCachedRecipe",
            at = @At("TAIL")
    )
    private void cider$prioritizeSimplePot(CallbackInfo ci) {
        if (!(cachedRecipe instanceof SoupPotRecipe)) return;
        assert self.getLevel() != null;

        cachedRecipe = self.getLevel().getRecipeManager().getAllRecipesFor(TFCRecipeTypes.POT.get())
                        .stream()
                        .filter(r -> r.value() instanceof SimplePotRecipe)
                        .filter(r -> r.value().matches(self.getInventory(), self.getLevel()))
                        .findFirst()
                        .map(RecipeHolder::value)
                        .orElse(null);
    }

}
