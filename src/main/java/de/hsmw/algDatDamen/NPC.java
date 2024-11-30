package de.hsmw.algDatDamen;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class NPC {
    private int progress;
    private HashMap<String, Sound> data;
    private Location location;

    public NPC(Location location) {
        this.location = location;
        this.progress = 0;
        this.data = new HashMap<>();
    }

    /**
     * Adds a text to the list of texts.
     * 
     * @param key   the text.
     * @param value the sound to play.
     */
    public void addText(String key, Sound value) {
        data.put(key, value);
    }

    /**
     * Spawns the NPC at the location.
     */
    public void spawn() {
        location.getWorld().getNearbyEntities(location, 1, 1, 1).forEach(e -> {
            if (e instanceof Villager) {
                e.remove();
            }
        });
        Villager v = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        v.setAI(false);
        v.setInvulnerable(true);
        v.setProfession(Villager.Profession.NITWIT);
        // v.lookAt(location.getWorld().getPlayers().getFirst());
    }

    /**
     * Plays the next text in the list of texts.
     */
    public void playNext() {
        if (progress < data.size()) {
            Sound[] sounds = new Sound[data.size()];
            data.values().toArray(sounds);
            location.getNearbyPlayers(100).forEach(p -> p.sendMessage(data.keySet().toArray()[progress].toString()));
            location.getWorld().playSound(location, sounds[progress], 1, 1);
            progress++;
        }
    }

    /**
     * Removes the NPC from the world.
     */
    public void remove() {
        location.getWorld().getNearbyEntities(location, 1, 1, 1).forEach(e -> {
            if (e instanceof Villager) {
                e.remove();
            }
        });
    }

    /**
     * Resets the progress of the NPC to the first text.
     */
    public void resetProgress() {
        progress = 0;
    }
}