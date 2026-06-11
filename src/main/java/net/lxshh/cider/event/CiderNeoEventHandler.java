package net.lxshh.cider.event;

import net.dries007.tfc.common.effect.TFCEffects;
import net.dries007.tfc.common.items.TFCShieldItem;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CiderNeoEventHandler {

    public static void init() {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(EventPriority.HIGHEST, CiderNeoEventHandler::onShieldBlock);
        bus.addListener(CiderNeoEventHandler::onPlayerClone);
    }

    // Cancel TFC's shieldBlock event
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity().getUseItem().getItem() instanceof TFCShieldItem shieldItem) {
            event.setCanceled(true);
        }
    }

    // Restore players Hunger, Saturation and Thirst on respawn
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player original = event.getOriginal();
        Player respawned = event.getEntity();

        FoodData foodData = respawned.getFoodData();

        int foodLevel = original.getFoodData().getFoodLevel();
        float saturationLevel = original.getFoodData().getSaturationLevel();
        float thirst = IPlayerInfo.get(original).getThirst();

        if (foodLevel <= 2) {
            foodData.setFoodLevel(foodLevel + 3);
            respawned.addEffect(new MobEffectInstance(TFCEffects.EXHAUSTED.holder(), 100, 0, false, false));
        } else {
            foodData.setFoodLevel(foodLevel);
        }

        foodData.setSaturation(saturationLevel);
        IPlayerInfo.get(respawned).setThirst(thirst);
    }
}
