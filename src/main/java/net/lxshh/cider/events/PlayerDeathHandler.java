package net.lxshh.cider.events;

import net.dries007.tfc.common.effect.TFCEffects;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.lxshh.cider.config.ServerConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class PlayerDeathHandler {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        if (!ServerConfig.resetHungerOnDeath.get()) {
            transferFoodInfo(oldPlayer, newPlayer);
        }
    }

    private static void transferFoodInfo(Player oldPlayer, Player newPlayer) {
        FoodData foodData = newPlayer.getFoodData();

        int foodLevel = oldPlayer.getFoodData().getFoodLevel();
        float saturationLevel = oldPlayer.getFoodData().getSaturationLevel();
        float thirst = IPlayerInfo.get(oldPlayer).getThirst();

        if (foodLevel <= 2) {
            foodData.setFoodLevel(foodLevel + 2);
            newPlayer.addEffect(new MobEffectInstance(TFCEffects.EXHAUSTED.holder(), 100, 0, false, false));
        } else {
            foodData.setFoodLevel(foodLevel);
        }

        foodData.setSaturation(saturationLevel);
        IPlayerInfo.get(newPlayer).setThirst(thirst);
    }
}
