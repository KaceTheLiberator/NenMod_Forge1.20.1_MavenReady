package com.nenmod.hatsu;

import com.nenmod.nen.PlayerNenData;
import net.minecraft.world.entity.player.Player;

public class AffinityPenaltySystem {

    public static double applyDamageModifier(Player player, String hatsuAffinity, double baseDamage) {
        PlayerNenData data = PlayerNenData.get(player);
        String playerAffinity = data.getAffinity();

        if (playerAffinity.equals("Specialist") && !hatsuAffinity.equals("Specialist")) {
            return baseDamage; // Specialist can still use others normally
        }

        if (playerAffinity.equals(hatsuAffinity)) {
            return baseDamage; // same affinity → no penalty
        }

        // Cross-affinity penalty
        return baseDamage * 0.7; // -30%
    }

    public static int applyCooldownModifier(Player player, String hatsuAffinity, int baseCooldown) {
        PlayerNenData data = PlayerNenData.get(player);
        String playerAffinity = data.getAffinity();

        if (playerAffinity.equals("Specialist") && !hatsuAffinity.equals("Specialist")) {
            return baseCooldown;
        }

        if (playerAffinity.equals(hatsuAffinity)) {
            return baseCooldown;
        }

        return (int) (baseCooldown * 1.5); // +50%
    }

    public static int applyDurationModifier(Player player, String hatsuAffinity, int baseDuration) {
        PlayerNenData data = PlayerNenData.get(player);
        String playerAffinity = data.getAffinity();

        if (playerAffinity.equals("Specialist") && !hatsuAffinity.equals("Specialist")) {
            return baseDuration;
        }

        if (playerAffinity.equals(hatsuAffinity)) {
            return baseDuration;
        }

        return (int) (baseDuration * 0.75); // -25%
    }
}
