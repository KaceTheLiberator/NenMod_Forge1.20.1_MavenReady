// Combined Code from MainMod.java and NenMod.java


package com.yourmodname;

import com.yourmodname.effects.GyoSystem;
import com.yourmodname.effects.ScarSystem;
import com.yourmodname.events.CatastropheManager;
import com.yourmodname.abilities.NenAffectedMobs;

public class MainMod {

    private GyoSystem gyoSystem;
    private ScarSystem scarSystem;
    private CatastropheManager catastropheManager;
    private NenAffectedMobs nenAffectedMobs;

    public MainMod() {
        this.gyoSystem = new GyoSystem();
        this.scarSystem = new ScarSystem();
        this.catastropheManager = new CatastropheManager();
        this.nenAffectedMobs = new NenAffectedMobs();
    }

    // Main game loop integration
    public void onPlayerAction(Player player, int nenPoolUsed, int naturalLimit) {
        // Track overtraining and apply scar penalties
        scarSystem.trackOvertraining(nenPoolUsed, naturalLimit);
        scarSystem.applyScarPenalties();

        // Check if player is using Gyo
        gyoSystem.revealAuraWithGyo(player, scarSystem.getScarSeverity());

        // Manipulate mobs if Nen ability is used
        nenAffectedMobs.manipulateMobWithNen(mob, player);
    }

    public void updateCatastrophes() {
        catastropheManager.updateCatastrophes();
    }

    // Additional event triggers like catastrophe spawn
    public void onCatastropheTrigger(Player player) {
        catastropheManager.spawnCatastrophe(player);
    }

    public void onCatastropheCollapse() {
        catastropheManager.collapseAllCatastrophes();
    }
}


// End of MainMod.java

package com.example.nenmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import com.example.nenmod.ability.AbilityLoader;
import com.example.nenmod.skill.SkillTreeLoader;
import com.example.nenmod.content.NenCapabilities;
import com.example.nenmod.net.NenNetwork;

@Mod("nenmod")
public class NenMod {
    public NenMod() {
        MinecraftForge.EVENT_BUS.addListener(this::onReload);
        NenNetwork.init();
        NenCapabilities.init();
    }
    private void onReload(AddReloadListenerEvent event) {
        event.addListener(new AbilityLoader());
        event.addListener(new SkillTreeLoader());
    }
}


// End of NenMod.java