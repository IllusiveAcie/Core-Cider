//package net.lxshh.cider.mixin.common.tfc;
//
//import net.dries007.tfc.util.calendar.CalendarEventHandler;
//import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(CalendarEventHandler.class)
//public class CalendarEventHandlerMixin {
//
//    @Inject(
//            method = "onPlayerStartSleeping",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private static void cider$skipSleepCheck(CanPlayerSleepEvent event, CallbackInfo ci) {
//        ci.cancel();
//    }
//
//}
