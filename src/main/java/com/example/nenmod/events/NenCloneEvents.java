package com.nenmod.events;

import com.nenmod.nen.NenProvider;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nenmod")
public class NenCloneEvents {
    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone e) {
        e.getOriginal().reviveCaps();
        e.getOriginal().getCapability(NenProvider.NEN_CAP).ifPresent(oldCap ->
            e.getEntity().getCapability(NenProvider.NEN_CAP).ifPresent(newCap ->
                newCap.deserializeNBT(oldCap.serializeNBT())
            )
        );
        e.getOriginal().invalidateCaps();
    }
}
