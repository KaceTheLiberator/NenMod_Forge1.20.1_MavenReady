
public class GyoSystem {

    public boolean isUsingGyo(Player player) {
        return player.isPotionActive(PotionEffectType.GLOWING);
    }

    public void revealAuraWithGyo(Player player, int scarSeverity) {
        if (isUsingGyo(player)) {
            applyAuraLeakVisuals(player, scarSeverity);
        }
    }

    private void applyAuraLeakVisuals(Player player, int scarSeverity) {
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
