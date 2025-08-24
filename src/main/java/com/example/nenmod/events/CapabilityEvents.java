package com.nenmod.events;

import com.nenmod.nen.NenProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nenmod")
public class CapabilityEvents {
    private static final ResourceLocation ID = new ResourceLocation("nenmod", "nen");

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Object> e) {
        if (e.getObject() instanceof Player) {
            e.addCapability(ID, new NenProvider());
        }
    }
}
