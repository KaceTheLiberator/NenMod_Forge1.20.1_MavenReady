
package com.example.nenmod.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;

public class GonAbility extends Ability {

    private static final int STRENGTH_BOOST_DURATION = 200;  // Duration in ticks (10 seconds)
    private static final int COOLDOWN = 600;  // Cooldown in ticks (30 seconds)

    private boolean isCooldownActive = false;

    public GonAbility() {
        super("Gon", 0);  // Ability name and some identifier (ID)
    }

    @Override
    public void activateAbility(PlayerEntity player) {
        if (isCooldownActive) {
            // If ability is in cooldown, do nothing
            return;
        }

        // Apply a strength boost to the player
        player.addPotionEffect(new EffectInstance(Effects.STRENGTH, STRENGTH_BOOST_DURATION, 1));

        // Set cooldown
        isCooldownActive = true;
        startCooldown();
    }

    private void startCooldown() {
        // Logic to handle the cooldown (e.g., count down the cooldown)
        Minecraft.getInstance().getTimer().schedule(new Runnable() {
            @Override
            public void run() {
                isCooldownActive = false;
            }
        }, COOLDOWN);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        // Check if player has sufficient resources or conditions to use the ability
        return !isCooldownActive;
    }
}
