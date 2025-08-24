
package com.yourmodname.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if (timeElapsed > 600000) { // 10 minutes
            stage = 1;
        }
        if (timeElapsed > 1200000) { // 20 minutes
            stage = 2;
        }
    }

    public void applyCatastropheEffects(Player player) {
        switch (stage) {
            case 0: // Minor instability
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
                break;
            case 1: // Severe instability
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
                break;
            case 2: // Critical collapse
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
