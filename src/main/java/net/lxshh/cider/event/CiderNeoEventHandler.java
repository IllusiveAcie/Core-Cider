package net.lxshh.cider.event;

import net.dries007.tfc.common.effect.TFCEffects;
import net.dries007.tfc.common.items.TFCShieldItem;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.lxshh.cider.common.data.CiderDataManagers;
import net.lxshh.cider.common.data.nutrition.NutritionEffect;
import net.lxshh.cider.common.recipe.BlockApplicationRecipe;
import net.lxshh.cider.network.CiderDataManagerSyncPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class CiderNeoEventHandler {

    public static void init() {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(CiderNeoEventHandler::onPlayerTickPost);
        bus.addListener(CiderNeoEventHandler::addReloadListeners);
        bus.addListener(CiderNeoEventHandler::onDataPackSync);
        bus.addListener(EventPriority.HIGHEST, CiderNeoEventHandler::onShieldBlock);
        bus.addListener(CiderNeoEventHandler::onPlayerClone);
        bus.addListener(CiderNeoEventHandler::onRightClick);
    }

    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        NutritionEffect.nutritionEffectTick(event.getEntity());
    }

    public static void addReloadListeners(AddReloadListenerEvent event) {
        CiderDataManagers.REGISTRY.forEach(event::addListener);
    }

    public static void onDataPackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            PacketDistributor.sendToAllPlayers(new CiderDataManagerSyncPacket());
        } else {
            PacketDistributor.sendToPlayer(event.getPlayer(), new CiderDataManagerSyncPacket());
        }
    }

    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack held = player.getMainHandItem();

        BlockApplicationRecipe recipe = BlockApplicationRecipe.getRecipe(state, held);
        if (recipe == null || recipe.isFake()) return;

        BlockApplicationRecipe.doRecipe(player, level, pos, recipe);
        event.setCanceled(true);
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
