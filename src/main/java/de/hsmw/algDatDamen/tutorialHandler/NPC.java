package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class NPC {
    private Location location;
    private Villager villager;

    /**
     * Creates a new NPC object at the given location.
     * 
     * @param location the originCorner of the chessboard
     */
    public NPC(Location startLocation) {
        this.location = startLocation.clone();
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
        villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setCollidable(false);
        villager.setInvulnerable(true);
        villager.setProfession(Villager.Profession.NITWIT);
    }

    /**
     * @param track der Track der abgespielt wird
     */
    public void playTrack(NPCTrack track) {
        location.getNearbyPlayers(50).forEach(p -> p.sendMessage(track.getText()));
        location.getWorld().playSound(location, track.getSound(), 1, 1);
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
}