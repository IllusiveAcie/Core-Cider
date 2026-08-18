package net.lxshh.cider.events;

import net.lxshh.cider.Cider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

@EventBusSubscriber(modid = Cider.MOD_ID)
public class CiderEventHandler {

    @SubscribeEvent
    public static void onLivingEntityXP(LivingExperienceDropEvent event) {
        event.setDroppedExperience(0);
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.loadedFromDisk()) {
            return;
        }

        final Level level = event.getLevel();
        Entity entity = event.getEntity();

        if (entity instanceof ExperienceOrb) {
            event.setCanceled(true);
        }
    }
}
