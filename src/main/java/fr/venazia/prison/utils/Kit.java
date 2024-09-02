package fr.venazia.prison.utils;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Kit {
    private String name;
    private List<ItemStack> items;
    private long cooldown; // cooldown en millisecondes
    private Map<UUID, Long> lastUsed; // Map pour stocker la dernière utilisation du kit par joueur

    public Kit(String name, long cooldown, List<ItemStack> items) {
        this.name = name;
        this.items = items;
        this.cooldown = cooldown;
        this.lastUsed = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<ItemStack>  getItems() {
        return items;
    }

    public long getCooldown() {
        return cooldown;
    }

    @Override
    public String toString() {
        return "Kit{" +
                "name='" + name + '\'' +
                ", cooldown=" + cooldown +
                ", items=" + items +
                '}';
    }


    public boolean canUse(UUID playerUUID) {
        Long lastUseTime = lastUsed.get(playerUUID);
        return lastUseTime == null || (System.currentTimeMillis() - lastUseTime) >= cooldown;
    }

    public void setLastUsed(UUID playerUUID) {
        lastUsed.put(playerUUID, System.currentTimeMillis());
    }

    // Setters et autres méthodes utiles
}
