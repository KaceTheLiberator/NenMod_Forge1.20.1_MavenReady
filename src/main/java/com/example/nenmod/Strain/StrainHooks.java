package com.yourname.nenmod.strain;

import net.minecraft.world.entity.player.Player;

public class StrainHooks {

    /**
     * Call this when a technique is used to add strain.
     * @param player Player
     * @param baseCost base strain per use (e.g., 6 for Ten, 10 for Ren, 14 for Gyo, etc.)
     * @param intensityMultiplier scale by ability intensity (1.0 = normal)
     */
    public static void addTechniqueStrain(Player player, int baseCost, float intensityMultiplier) {
        if (player == null) return;
        player.getCapability(StrainProvider.STRAIN).ifPresent(cap -> {
            int cost = Math.max(0, Math.round(baseCost * Math.max(0.1f, intensityMultiplier)));
            cap.addStrain(cost);
        });
    }

    /** Convenience helpers */
    public static void tenUsed(Player p)    { addTechniqueStrain(p, 6, 1.0f); }
    public static void renUsed(Player p)    { addTechniqueStrain(p, 10, 1.0f); }
    public static void gyoUsed(Player p)    { addTechniqueStrain(p, 14, 1.0f); }
    public static void zetsuUsed(Player p)  { addTechniqueStrain(p, 4, 1.0f); }
    public static void hatsuUsed(Player p, float intensity) { addTechniqueStrain(p, 16, intensity); }
}
