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
public abstract class PotBlockEntityMixin {

    @Shadow
    private @Nullable PotRecipe cachedRecipe;

    /**
     * Prioritizes simple recipes over soup recipes where ingredients exist in both recipes
     */
    @Inject(
            method = "updateCachedRecipe",
            at = @At("TAIL")
    )
    private void cider$prioritizeSimplePotRecipes(CallbackInfo ci) {
        PotBlockEntity self = (PotBlockEntity) (Object) this;
        if (!(cachedRecipe instanceof SoupPotRecipe)) return;
        assert self.getLevel() != null;

        cachedRecipe = self.getLevel().getRecipeManager()
                .getRecipesFor(TFCRecipeTypes.POT.get(), self.getInventory(), self.getLevel())
                .stream()
                .filter(r -> r.value() instanceof SimplePotRecipe)
                .findFirst()
                .map(RecipeHolder::value)
                .orElse(null);
    }
}
