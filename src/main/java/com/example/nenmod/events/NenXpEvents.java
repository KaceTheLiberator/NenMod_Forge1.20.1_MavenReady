package com.example.nenmod.events;

import com.example.nenmod.system.NenXpManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nenmod")
public class NenXpEvents {

    /**
     * Mob kill → XP reward
     */
    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getEntity() instanceof LivingEntity mob)) return;

        NenXpManager.onMobKill(player, mob);
    }

    /**
     * Keybind action → XP reward
     * (Hook into your TaskManager/Keybind handler)
     */
    public static void onPhysicalTask(ServerPlayer player, String task) {
        NenXpManager.onPhysicalTask(player, task);
    }

    /**
     * Technique use → XP reward
     * (Hook into Technique activation system)
     */
    public static void onTechniqueUse(ServerPlayer player, String techniqueId) {
        NenXpManager.onTechniqueUse(player, techniqueId);
    }
}
