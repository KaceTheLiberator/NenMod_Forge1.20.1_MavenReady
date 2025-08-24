
package com.example.nenmod.client;

import com.example.nenmod.ability.Ability;
import com.example.nenmod.content.PlayerNenProvider;
import net.minecraft.world.entity.player.Player;

public class NenRender {
    public static boolean shouldRender(Ability ability, Player viewer, Player caster) {
        // If always visible:
        if (ability != null && "VISIBLE".equalsIgnoreCase(ability.visibility)) return true;

        // If semi-visible: show baseline, stronger with Gyo (not implemented here)
        if (ability != null && "SEMI_VISIBLE".equalsIgnoreCase(ability.visibility)) {
            // baseline allowed
            return true;
        }

        // INVISIBLE path: require Gyo
        final boolean[] gyo = {false};
        viewer.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> { if (cap.get().isGyoActive) gyo[0] = true; });
        if (!gyo[0]) return false;

        // If caster is using In and the ability can be concealed, do not render
        final boolean[] casterIn = {false};
        caster.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> { if (cap.get().isInActive) casterIn[0] = true; });
        if (casterIn[0] && ability != null && ability.canBeConcealed) return false;

        return true;
    }
}
