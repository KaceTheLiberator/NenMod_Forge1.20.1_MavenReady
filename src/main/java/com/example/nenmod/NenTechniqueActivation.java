public class NenTechniqueActivation {

    public static void activateTen(Player player) {
        // Aura regeneration activated for Ten
        AuraManager.recoverAura(player, 2.0f); // Example aura recovery rate
        player.sendMessage("Ten activated: Aura regenerating.");
    }

    public static void deactivateTen(Player player) {
        // Aura stops regenerating when Ten is deactivated
        player.sendMessage("Ten deactivated: Aura regeneration stopped.");
    }

    public static void activateRen(Player player) {
        // Increase aura output for Ren
        AuraManager.increaseAuraStrength(player, 2.0f);
        player.sendMessage("Ren activated: Boosting aura strength.");
    }

    public static void deactivateRen(Player player) {
        // Restore normal aura output when Ren is deactivated
        AuraManager.resetAuraStrength(player);
        player.sendMessage("Ren deactivated: Aura strength returned to normal.");
    }

    public static void activateZetsu(Player player) {
        // Stop aura regeneration for Zetsu
        AuraManager.setRegeneration(player, 0.0f); // Stops aura regeneration
        player.sendMessage("Zetsu activated: Stopping aura regeneration.");
    }

    public static void deactivateZetsu(Player player) {
        // Restore normal aura regeneration when Zetsu is deactivated
        AuraManager.resetRegeneration(player);
        player.sendMessage("Zetsu deactivated: Aura regeneration restored.");
    }

    public static void activateGyo(Player player) {
        // Focus aura for Gyo (e.g., increased damage or protection)
        AuraManager.increaseFocus(player, 1.5f);
        player.sendMessage("Gyo activated: Focusing aura on specific body parts.");
    }

    public static void deactivateGyo(Player player) {
        // Reset focus when Gyo is deactivated
        AuraManager.resetFocus(player);
        player.sendMessage("Gyo deactivated: Focus reset.");
    }
}
