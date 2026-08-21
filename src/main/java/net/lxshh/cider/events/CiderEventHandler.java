package net.lxshh.cider.events;

import net.dries007.tfc.common.effect.TFCEffects;
import net.dries007.tfc.common.items.TFCShieldItem;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.lxshh.cider.Cider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Cider.MOD_ID)
public class CiderEventHandler {

    // Cancel TFC's shieldBlock event
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity().getUseItem().getItem() instanceof TFCShieldItem) {
            event.setCanceled(true);
        }
    }

    // Reset the player info back on respawn
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player original = event.getOriginal();
        Player respawned = event.getEntity();

        FoodData foodData = respawned.getFoodData();

        int foodLevel = original.getFoodData().getFoodLevel();
        float saturationLevel = original.getFoodData().getSaturationLevel();
        float thirst = IPlayerInfo.get(original).getThirst();

        if (foodLevel <= 2) {
            foodData.setFoodLevel(foodLevel + 2);
            respawned.addEffect(new MobEffectInstance(TFCEffects.EXHAUSTED.holder(), 100, 0, false, false));
        } else {
            foodData.setFoodLevel(foodLevel);
        }

        foodData.setSaturation(saturationLevel);
        IPlayerInfo.get(respawned).setThirst(thirst);
    }

    // No experience dropped on death by mobs
    @SubscribeEvent
    public static void onLivingEntityXP(LivingExperienceDropEvent event) {
        event.setDroppedExperience(0);
    }

    // Obliterate experience orbs since they are useless
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.loadedFromDisk()) {
            return;
        }
        if (event.getEntity() instanceof ExperienceOrb) {
            event.setCanceled(true);
        }
    }
}
