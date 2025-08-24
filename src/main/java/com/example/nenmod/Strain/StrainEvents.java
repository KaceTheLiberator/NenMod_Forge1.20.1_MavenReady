package com.yourname.nenmod.strain;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StrainEvents {

    /** Server tick: update strain decay/lockout and apply penalties while overstrained. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        event.player.getCapability(StrainProvider.STRAIN).ifPresent(strain -> {
            strain.tick();

            if (strain.isOverstrained()) {
                // Light penalties; adjust as needed
                // Mining fatigue + slowness while overstrained
                event.player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, 0, true, false, false));
                event.player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, true, false, false));

                // Optional: cancel sprinting to discourage spam
                event.player.setSprinting(false);
            }
        });
    }

    /** Carry strain over on death/respawn (customize if you prefer partial reset). */
    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(StrainProvider.STRAIN).ifPresent(oldCap -> {
            event.getEntity().getCapability(StrainProvider.STRAIN).ifPresent(newCap -> {
                newCap.loadNBT(oldCap.saveNBT());
                // Example: soften death penalty (halve strain on respawn)
                int half = Math.max(0, oldCap.getStrain() / 2);
                newCap.loadNBT(oldCap.saveNBT());
                // Overwrite only the strain value
                var tag = newCap.saveNBT();
                tag.putInt("Strain", half);
                newCap.loadNBT(tag);
            });
        });
    }
}
