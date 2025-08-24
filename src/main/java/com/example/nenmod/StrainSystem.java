package com.example.nenmod;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrainSystem {

    // Apply strain from Advanced Ren
    public static void applyAdvancedRenStrain(Player player) {
        // Decrease health or apply hunger
        player.setHealth(Math.max(player.getHealth() - 2, 1));  // Drain health
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));  // Apply hunger
        player.sendMessage("The strain of Advanced Ren is draining your health and hunger!");
    }

    // Apply strain from Advanced Zetsu
    public static void applyAdvancedZetsuStrain(Player player) {
        // Apply aura depletion (you can simulate aura as health or other debuffs)
        AuraManager.decreaseAura(player, 10);  // Drains aura significantly
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1));  // Apply weakness
        player.sendMessage("The strain of Advanced Zetsu is leaving you weak!");
    }

    // Apply strain from Advanced Gyo
    public static void applyAdvancedGyoStrain(Player player) {
        // Apply temporary fatigue or slowness
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));  // Apply slowness
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));  // Apply blindness
        player.sendMessage("The strain of Advanced Gyo is making you feel fatigued!");
    }
}
