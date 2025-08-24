public class AuraSystem {
    private float currentAura;
    private float maxAura;

    // Example: using Ten
    public void useTen(float auraCost) {
        if (currentAura >= auraCost) {
            currentAura -= auraCost;
            // Add XP to Ten
            NenTechniqueManager.addMastery("ten", auraCost * 0.05f); 
            // scaling: bigger aura spend = faster mastery
        }
    }

    // Example: using Ren
    public void useRen(float auraCost) {
        if (currentAura >= auraCost && NenTechniqueManager.isUnlocked("ren")) {
            currentAura -= auraCost;
            NenTechniqueManager.addMastery("ren", auraCost * 0.05f);
        }
    }

    // Add similar methods for Zetsu, Gyo, etc.
}
