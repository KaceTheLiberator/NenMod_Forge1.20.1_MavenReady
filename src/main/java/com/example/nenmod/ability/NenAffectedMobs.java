
public class NenAffectedMobs {

    public void manipulateMobWithNen(Entity mob, Player player) {
        if (player.getActivePotionEffect(PotionEffectType.LEVITATION) != null) {
            mob.setVelocity(new Vector(0, 1, 0));
        }
    }

    public void affectMobWithBungeeGum(Entity mob, Player player) {
        mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
    }
}
