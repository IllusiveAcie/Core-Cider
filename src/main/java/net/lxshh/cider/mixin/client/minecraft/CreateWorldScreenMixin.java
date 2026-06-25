/**
 * Derived from TerraFirmaGreg-Modern Core
 */
package net.lxshh.cider.mixin.client.minecraft;

import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen$WorldTab")
public class CreateWorldScreenMixin {

    /**
     * Restrict world types to TFC and SuperFlat
     */
    @Redirect(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/CycleButton$Builder;withValues(Lnet/minecraft/client/gui/components/CycleButton$ValueListSupplier;)Lnet/minecraft/client/gui/components/CycleButton$Builder;")
    )
    private CycleButton.Builder<WorldCreationUiState.WorldTypeEntry> cider$init$builder$withValues(CycleButton.Builder<WorldCreationUiState.WorldTypeEntry> instance, CycleButton.ValueListSupplier<WorldCreationUiState.WorldTypeEntry> values) {
        var allowedWorldPresets = values.getDefaultList().stream().filter(s -> {
            var preset = s.preset();
            if (preset == null)
                return false;
            return preset.is(TerraFirmaCraft.PRESET) || preset.is(WorldPresets.FLAT);
        }).toList();

        return instance.withValues(CycleButton.ValueListSupplier.create(allowedWorldPresets));
    }
}
