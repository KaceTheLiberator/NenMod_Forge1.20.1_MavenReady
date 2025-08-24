
package com.yourmodname.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Particle;

public class AuraEffectHandler {

    private static final float AURA_FLICKER_SPEED = 0.5f;

    // Function to apply visual effect for aura leakage
    public void applyAuraLeakVisuals(Player player, int scarSeverity) {
        if (scarSeverity > 0) {
            isAuraLeaking = true;
            spawnAuraLeakParticles(player, scarSeverity);
        }
    }

    // Function to spawn aura leak particles based on scar severity
    private void spawnAuraLeakParticles(Player player, int scarSeverity) {
        switch (scarSeverity) {
            case 1: 
                player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation(), 5, 0.5, 0.5, 0.5, 0.1);
                break;
            case 2: 
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 10, 1.0, 1.0, 1.0, 0.2);
                break;
            case 3: 
                player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 15, 1.5, 1.5, 1.5, 0.3);
                break;
            default:
                break;
        }
    }
}
