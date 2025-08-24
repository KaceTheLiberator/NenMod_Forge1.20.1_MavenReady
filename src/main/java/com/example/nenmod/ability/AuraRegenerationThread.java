package com.example.nenmod;

public class AuraRegenerationThread extends Thread {
    private PlayerData playerData;

    public AuraRegenerationThread(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (playerData.getCurrentAura() < playerData.getMaxAura()) {
                    // Aura regeneration rate scales with player level (example: 1 aura per 1 level every 2 seconds)
                    double regenAmount = 1 + (playerData.getNenLevel() / 10.0);  // Increase regeneration based on level
                    playerData.addAura(regenAmount);
                }
                Thread.sleep(2000);  // Regenerate every 2 seconds
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
