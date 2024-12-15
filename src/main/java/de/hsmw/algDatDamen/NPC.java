package de.hsmw.algDatDamen;

import java.util.HashMap;
import org.bukkit.util.Vector;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NPC {
    private int progress;
    private HashMap<String, Sound> textData;
    private Location location;

    /**
     * Creates a new NPC object at the given location.
     * 
     * @param location the originCorner of the chessboard
     */
    public NPC(Location location) {
        this.location = location.clone().add(new Vector(-1, 1, -0.5));
        this.progress = 0;
        this.textData = new HashMap<>();
    }

    /**
     * Adds a text to the list of texts.
     * 
     * @param key   the text.
     * @param value the sound to play.
     */
    public void addText(String key, Sound value) {
        textData.put(key, value);
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
        v.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 100, false, false));
        v.setInvulnerable(true);
        v.setProfession(Villager.Profession.NITWIT);
    }

    /**
     * Plays the next text in the list of texts.
     */
    public void playNext() {
        if (progress < textData.size()) {
            Sound[] sounds = new Sound[textData.size()];
            textData.values().toArray(sounds);
            location.getNearbyPlayers(100)
                    .forEach(p -> p.sendMessage(textData.keySet().toArray()[progress].toString()));
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
