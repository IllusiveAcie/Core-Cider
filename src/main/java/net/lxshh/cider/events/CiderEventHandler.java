package net.lxshh.cider.events;

import net.dries007.tfc.common.items.TFCShieldItem;
import net.lxshh.cider.Cider;
import net.lxshh.cider.util.CiderHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Cider.MOD_ID)
public class CiderEventHandler {

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            final MinecraftServer server = level.getServer();
            final GameRules rules = level.getGameRules();

            rules.getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, server);
            rules.getRule(GameRules.RULE_DOINSOMNIA).set(false, server);
            rules.getRule(GameRules.RULE_DO_PATROL_SPAWNING).set(false, server);
            rules.getRule(GameRules.RULE_DO_TRADER_SPAWNING).set(false, server);
            rules.getRule(GameRules.RULE_DO_WARDEN_SPAWNING).set(false, server);
            rules.getRule(GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY).set(false, server);
        }
    }

    @SubscribeEvent
    public static void onUseItemBlockEvent(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        ItemInteractionResult result = CiderHelpers.blockInteractionRecipe(Objects.requireNonNull(event.getPlayer()), level, state, pos);

        if (result != ItemInteractionResult.FAIL) {
            event.cancelWithResult(result);
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity().getUseItem().getItem() instanceof TFCShieldItem) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingEntityXP(LivingExperienceDropEvent event) {
        event.setDroppedExperience(0);
    }

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
