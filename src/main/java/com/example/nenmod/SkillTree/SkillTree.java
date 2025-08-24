package com.yourmod.skilltree;

import com.yourmod.player.NenData;
import net.minecraft.nbt.CompoundTag;
import java.util.HashMap;
import java.util.Map;

public class SkillTree {

    private final Map<String, SkillTreeNode> nodes = new HashMap<>();

    public void addNode(SkillTreeNode node) {
        nodes.put(node.getId(), node);
    }

    public SkillTreeNode getNode(String id) {
        return nodes.get(id);
    }

    public boolean unlockNode(String id, NenData data) {
        SkillTreeNode node = nodes.get(id);
        if (node != null && !node.isUnlocked()) {
            int cost = node.getCost();
            if (data.getUnlockManager().getSkillPoints() >= cost) {
                for (int i = 0; i < cost; i++) {
                    data.getUnlockManager().spendSkillPoint();
                }
                node.unlock();
                return true;
            }
        }
        return false;
    }

    // --- NBT Save/Load ---
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<String, SkillTreeNode> entry : nodes.entrySet()) {
            tag.put(entry.getKey(), entry.getValue().saveNBT());
        }
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            if (nodes.containsKey(key)) {
                nodes.get(key).loadNBT(tag.getCompound(key));
            }
        }
    }
}
