public class NenXPManager {
    private static final int MAX_XP = 100;

    // XP storage (should be initialized with the player)
    private static final Map<Player, Map<String, Integer>> xpData = new HashMap<>();

    // Gain Nen XP based on technique usage
    public static void gainNenXP(Player player, String technique) {
        int currentXP = getXP(player, technique);

        // If the technique is mastered, stop XP gain
        if (currentXP >= MAX_XP) {
            player.sendMessage(technique + " is fully mastered! No more XP can be gained.");
            return;
        }

        // Otherwise, increase XP for the technique
        currentXP += 10;  // Increment XP by 10 for each use, for example
        setXP(player, technique, currentXP);
    }

    // Retrieve the current XP for a given technique
    private static int getXP(Player player, String technique) {
        return xpData.getOrDefault(player, new HashMap<>()).getOrDefault(technique, 0);
    }

    // Set XP for a given technique
    private static void setXP(Player player, String technique, int xp) {
        xpData.get(player).put(technique, xp);
    }

    // Check if a technique is mastered
    public static boolean isMastered(Player player, String technique) {
        return getXP(player, technique) >= MAX_XP;
    }
}
