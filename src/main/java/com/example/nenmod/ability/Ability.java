package com.example.nenmod.ability;

import com.example.nenmod.PlayerData;

public class Ability {
    public enum Affinity { NONE, WIND, WATER, FIRE }

    public String id;
    public String name;
    public double baseAuraCost;
    public Affinity requiredAffinity;

    // Calculate the actual aura cost based on player's affinity and strain
    public double calculateAuraCost(PlayerData playerData) {
        double auraCost = baseAuraCost;

        // If the player is using an ability outside their affinity, increase the cost
        if (playerData.getNenAffinity() != requiredAffinity) {
            auraCost *= 1.5;  // 50% more aura cost for non-affinity abilities
        }

        // Increase aura cost based on strain
        auraCost *= (1 + (playerData.getStrain() / 100.0));  // Increase aura cost by strain percentage

        return auraCost;
    }

    // Use the ability and drain aura
    public boolean useAbility(PlayerData playerData) {
        double auraCost = calculateAuraCost(playerData);

        // Check if the player has enough aura to use the ability
        if (playerData.getCurrentAura() >= auraCost) {
            playerData.subtractAura(auraCost);
            playerData.increaseStrain(10);  // Increase strain by 10% each time an ability is used
            return true;  // Ability used successfully
        } else {
            return false;  // Not enough aura to use the ability
        }
    }
}
