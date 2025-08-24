package com.example.nenmod.aura;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Unified AuraManager class that merges functionality of AuraManager + AuraSystem.
 * - Tracks per-player aura pool (max/current)
 * - Handles aura costs, regen, and penalties
 * - Integrates Nen Technique mastery system
 */
public class AuraManager {

    private static final float DEFAULT_MAX_AURA = 100.0f;
    private static final Map<Player, Float> playerAuras = new HashMap<>();
    private static final Map<Player, Float> playerMaxAura = new HashMap<>();

    // ===================== Basic Aura Pool Management ======================

    public static float getCurrentAura(Player player) {
        return playerAuras.getOrDefault(player, DEFAULT_MAX_AURA);
    }

    public static float getMaxAura(Player player) {
        return playerMaxAura.getOrDefault(player, DEFAULT_MAX_AURA);
    }

    public static void setMaxAura(Player player, float max) {
        playerMaxAura.put(player, max);
    }

    public static void decreaseAura(Player player, float amount) {
        float currentAura = getCurrentAura(player);
        float newAura = Math.max(currentAura - amount, 0);
        playerAuras.put(player, newAura);

        if (newAura == 0) {
            // Apply penalty when aura reaches 0
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
            player.sendMessage("Your aura is depleted! You are fatigued.");
        }
    }

    public static void recoverAura(Player player, float amount) {
        float currentAura = getCurrentAura(player);
        float maxAura = getMaxAura(player);
        playerAuras.put(player, Math.min(currentAura + amount, maxAura));
    }

    // ===================== Aura States (Zetsu, Ren, Gyo) ======================

    public static void setRegeneration(Player player, float rate) {
        // This can hook into a regen thread or scheduler
        player.sendMessage("Zetsu activated: Aura regeneration stopped.");
    }

    public static void resetRegeneration(Player player) {
        player.sendMessage("Aura regeneration restored.");
    }

    public static void increaseAuraStrength(Player player, float multiplier) {
        player.sendMessage("Aura strength increased (Ren).");
    }

    public static void resetAuraStrength(Player player) {
        player.sendMessage("Aura strength reset.");
    }

    public static void increaseFocus(Player player, float multiplier) {
        player.sendMessage("Focus increased (Gyo).");
    }

    public static void resetFocus(Player player) {
        player.sendMessage("Focus reset.");
    }

    // ===================== Nen Technique Integration ======================

    public static void useTen(Player player, float auraCost) {
        if (getCurrentAura(player) >= auraCost) {
            decreaseAura(player, auraCost);
            NenTechniqueManager.addMastery(player, "ten", auraCost * 0.05f);
            player.sendMessage("You used Ten. Aura cost: " + auraCost);
        } else {
            player.sendMessage("Not enough aura to use Ten.");
        }
    }

    public static void useRen(Player player, float auraCost) {
        if (getCurrentAura(player) >= auraCost && NenTechniqueManager.isUnlocked(player, "ren")) {
            decreaseAura(player, auraCost);
            NenTechniqueManager.addMastery(player, "ren", auraCost * 0.05f);
            player.sendMessage("You used Ren. Aura cost: " + auraCost);
        } else {
            player.sendMessage("Ren is locked or insufficient aura.");
        }
    }

    public static void useZetsu(Player player, float auraCost) {
        if (getCurrentAura(player) >= auraCost && NenTechniqueManager.isUnlocked(player, "zetsu")) {
            decreaseAura(player, auraCost);
            NenTechniqueManager.addMastery(player, "zetsu", auraCost * 0.05f);
            player.sendMessage("You used Zetsu. Aura suppressed.");
            setRegeneration(player, 0); // disable aura regen
        } else {
            player.sendMessage("Zetsu is locked or insufficient aura.");
        }
    }

    public static void useGyo(Player player, float auraCost) {
        if (getCurrentAura(player) >= auraCost && NenTechniqueManager.isUnlocked(player, "gyo")) {
            decreaseAura(player, auraCost);
            NenTechniqueManager.addMastery(player, "gyo", auraCost * 0.05f);
            player.sendMessage("You used Gyo. Aura focused.");
            increaseFocus(player, 1.5f);
        } else {
            player.sendMessage("Gyo is locked or insufficient aura.");
        }
    }
}
