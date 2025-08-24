package com.yourname.nenmod.training;

import com.yourname.nenmod.nen.NenData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TrainingEvents {

    private static final ResourceLocation KEY = new ResourceLocation("nenmod", "training");

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> e) {
        if (e.getObject() instanceof net.minecraft.world.entity.player.Player) {
            e.addCapability(KEY, new TrainingProvider());
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone e) {
        e.getOriginal().getCapability(TrainingProvider.TRAINING).ifPresent(oldCap -> {
            e.getEntity().getCapability(TrainingProvider.TRAINING).ifPresent(newCap -> {
                newCap.loadNBT(oldCap.saveNBT());
            });
        });
    }

    /** Server tick: track sprinting and award small XP chunks. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END || e.player.level().isClientSide) return;

        e.player.getCapability(TrainingProvider.TRAINING).ifPresent(train -> {
            // keep cooldown ticking (for manual actions)
            train.tick();

            // Running tracking
            if (e.player.isSprinting() && e.player.onGround()) {
                train.addSprintTicks(1);

                // Every 100 sprint ticks (~5s) grant 1 XP (Nen must be unlocked)
                if (train.getSprintTicks() % 100 == 0) {
                    var nen = NenData.get(e.player);
                    if (nen != null && nen.hasNenUnlocked()) {
                        nen.addXp(1);
                    }
                }
            }
        });
    }
}
