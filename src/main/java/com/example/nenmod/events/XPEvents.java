package com.example.nenmod.events;

import com.example.nenmod.nen.NenDataProvider;
import com.example.nenmod.nen.NenData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nenmod")
public class XPEvents {

    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        NenData nen = NenDataProvider.get(player);
        if (nen.hasUnlockedNen()) {
            nen.addXP(10); // 💥 10 XP per mob kill
        }
    }
}
