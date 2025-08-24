package com.example.nenmod.system;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;

import java.util.Random;

public class NenXpManager {

    private static final Random random = new Random();

    /**
     * Called when a mob is killed
     */
    public static void onMobKill(ServerPlayer player, LivingEntity mob) {
        int baseXp = 5; // default XP
        int mobXp = (int) (baseXp + mob.getMaxHealth() * 0.25f);

        addNenXp(player, mobXp);
    }

    /**
     * Called when the player uses a nen technique (e.g. Ten, Ren, Gyo, etc.)
     */
    public static void onTechniqueUse(ServerPlayer player, String techniqueId) {
        int xp = switch (techniqueId) {
            case "ten" -> 2;
            case "ren" -> 3;
            case "gyo" -> 4;
            default -> 1;
        };

        addNenXp(player, xp);
    }

    /**
     * Called when the player performs a physical task (push-up, squat, sit-up, etc.)
     */
    public static void onPhysicalTask(ServerPlayer player, String task) {
        int xp = switch (task) {
            case "pushup" -> 1;
            case "situp" -> 1;
            case "squat" -> 1;
            case "run" -> 2;
            default -> 0;
        };

        addNenXp(player, xp);
    }

    /**
     * Apply XP and handle scaling difficulty
     */
    private static void addNenXp(ServerPlayer player, int amount) {
        int currentXp = NenDataManager.getXp(player);
        int currentLevel = NenDataManager.getLevel(player);

        int xpToNextLevel = getXpForNextLevel(currentLevel);

        int newXp = currentXp + amount;
        if (newXp >= xpToNextLevel) {
            // Level up
            NenDataManager.setLevel(player, currentLevel + 1);
            NenDataManager.setXp(player, newXp - xpToNextLevel);

            // Give skill tree points
            NenDataManager.addSkillPoint(player, 1);

            // Give hatsu unlock point every 10 levels
            if ((currentLevel + 1) % 10 == 0) {
                NenDataManager.addHatsuPoint(player, 1);
            }
        } else {
            NenDataManager.setXp(player, newXp);
        }
    }

    /**
     * Scaling formula for XP requirements
     */
    public static int getXpForNextLevel(int currentLevel) {
        // Simple quadratic growth
        return 50 + (currentLevel * currentLevel * 10);
    }
}
