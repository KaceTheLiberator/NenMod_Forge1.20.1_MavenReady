
package com.example.nenmod.skill;

import net.minecraft.entity.player.PlayerEntity;

public class GonSkillNode extends SkillNode {

    public GonSkillNode() {
        super("gon", 0, "Gon", "Unlock the Enhancer ability: Gon.");
    }

    @Override
    public void unlock(PlayerEntity player) {
        // Unlock Gon ability for the player
        player.addAbility(new GonAbility());
    }

    @Override
    public boolean canUnlock(PlayerEntity player) {
        // Add any prerequisites or conditions for unlocking Gon (e.g., level, other nodes)
        return player.hasAbility("enhancer");
    }
}
