package com.example.nenmod.content;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.example.nenmod.NenMod;

@Mod.EventBusSubscriber(modid="nenmod")
public class NenCapabilities {
    public static final ResourceLocation KEY = new ResourceLocation("nenmod", "player_nen");

    public static void init() { }

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Object> e) {
        if (e.getObject() instanceof Player p) {
            e.addCapability(KEY, new PlayerNenProvider());
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone e) {
        e.getOriginal().reviveCaps();
        e.getOriginal().getCapability(PlayerNenProvider.CAP).ifPresent(oldCap -> {
            e.getEntity().getCapability(PlayerNenProvider.CAP).ifPresent(newCap -> {
                newCap.copyFrom(oldCap);
            });
        });
        e.getOriginal().invalidateCaps();
    }
}
