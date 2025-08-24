
public class CatastropheManager {

    private List<CatastropheZone> activeCatastrophes = new ArrayList<>();

    public void spawnCatastrophe(Player player) {
        CatastropheZone newZone = new CatastropheZone(player.getLocation());
        activeCatastrophes.add(newZone);
        newZone.activateCatastrophe(player);
    }

    public void updateCatastrophes() {
        for (CatastropheZone zone : activeCatastrophes) {
            zone.updateStage();
        }
    }

    public void collapseAllCatastrophes() {
        for (CatastropheZone zone : activeCatastrophes) {
            zone.collapseCatastrophe();
        }
        activeCatastrophes.clear();
    }
}

public class CatastropheZone {

    private Location location;
    private boolean isActive = false;
    private int stage = 0;
    private long lastCollapseTime;

    public CatastropheZone(Location location) {
        this.location = location;
    }

    public void activateCatastrophe(Player player) {
        isActive = true;
        lastCollapseTime = System.currentTimeMillis();
        increaseStage();
        applyCatastropheEffects(player);
    }

    private void increaseStage() {
        long timeElapsed = System.currentTimeMillis() - lastCollapseTime;
        if (timeElapsed > 600000) { 
            stage = 1;
        }
        if (timeElapsed > 1200000) {
            stage = 2;
        }
    }

    public void applyCatastropheEffects(Player player) {
        switch (stage) {
            case 0: 
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
                break;
            case 1: 
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
                break;
            case 2: 
                player.damage(10);
                break;
            default:
                break;
        }
    }

    public void collapseCatastrophe() {
        spawnLoot();
        causeDestruction();
    }

    private void spawnLoot() {
        ItemStack rareItem = new ItemStack(Material.DIAMOND_SWORD);
        player.getWorld().dropItem(player.getLocation(), rareItem);
    }

    private void causeDestruction() {
        player.getWorld().createExplosion(player.getLocation(), 4.0F);
    }
}
