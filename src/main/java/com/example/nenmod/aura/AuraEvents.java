package com.yourname.nenmod.aura;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AuraEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            event.player.getCapability(AuraProvider.AURA).ifPresent(aura -> {
                if (aura.isExhausted()) {
                    // Apply penalty when aura is at 0
                    event.player.setSprinting(false);
                    event.player.setHealth(Math.max(1.0F, event.player.getHealth() - 0.01F));
                } else {
                    aura.regen();
                }
            });
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(AuraProvider.AURA).ifPresent(oldCap -> {
            event.getEntity().getCapability(AuraProvider.AURA).ifPresent(newCap -> {
                newCap.loadNBT(oldCap.saveNBT());
            });
        });
    }
}
