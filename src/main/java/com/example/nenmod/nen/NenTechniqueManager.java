import java.util.HashMap;
import java.util.Map;

public class NenTechniqueManager {
    private static final Map<String, Float> mastery = new HashMap<>();
    private static final Map<String, Boolean> unlocked = new HashMap<>();

    static {
        mastery.put("ten", 0f);
        mastery.put("ren", 0f);
        mastery.put("zetsu", 0f);
        mastery.put("gyo", 0f);
        mastery.put("hatsu", 0f);
        mastery.put("en", 0f);

        unlocked.put("ten", false);
        unlocked.put("ren", false);
        unlocked.put("zetsu", false);
        unlocked.put("gyo", false);
        unlocked.put("hatsu", false);
        unlocked.put("en", false);
    }

    // Unlock technique (called after training or progression)
    public static void unlockTechnique(String technique) {
        if (!unlocked.get(technique)) {
            unlocked.put(technique, true);
            // TODO: send a message to the player like "You unlocked [technique]!"
            System.out.println("Unlocked: " + technique);
        }
    }

    // Add mastery progress (called by Aura system when aura is used)
    public static void addMastery(String technique, float amount) {
        if (unlocked.get(technique)) {
            float current = mastery.get(technique);
            float newProgress = Math.min(100f, current + amount);
            mastery.put(technique, newProgress);

            checkForUnlocks(technique, newProgress);
        }
    }

    private static void checkForUnlocks(String technique, float progress) {
        if (progress >= 10f) { // 10% mastery unlock condition
            switch (technique) {
                case "ten":
                    if (!unlocked.get("ren")) unlockTechnique("ren");
                    break;
                case "ren":
                    if (!unlocked.get("zetsu")) unlockTechnique("zetsu");
                    break;
                case "zetsu":
                    if (!unlocked.get("gyo")) unlockTechnique("gyo");
                    break;
                case "gyo":
                    if (!unlocked.get("hatsu")) unlockTechnique("hatsu");
                    break;
                case "hatsu":
                    if (!unlocked.get("en")) unlockTechnique("en");
                    break;
            }
        }
    }

    // Getters
    public static float getMastery(String technique) {
        return mastery.getOrDefault(technique, 0f);
    }

    public static boolean isUnlocked(String technique) {
        return unlocked.getOrDefault(technique, false);
    }
}
