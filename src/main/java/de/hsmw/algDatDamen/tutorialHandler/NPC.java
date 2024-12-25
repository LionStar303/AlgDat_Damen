package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class NPC {
    private Location location;
    private Villager villager;
    private boolean console;

    /**
     * Creates a new NPC object at the given location.
     * 
     * @param startLocation des Levels
     */
    public NPC(Location startLocation, boolean console) {
        this.location = startLocation.clone();
        this.villager = null;
        this.console = console;
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
        if(console) System.out.println("Track abgespielt");
    }
    
    /**
     * spielt einen zufälligen positiven Track ab
     * @return {@link NPCTrack}
     */
    public NPCTrack playTrackPositive() {
        int n = (int) Math.round((Math.random() * 6d));
        switch (n) {
            case 0: return NPCTrack.NPC_POSITIVE_1;
            case 1: return NPCTrack.NPC_POSITIVE_2;
            case 2: return NPCTrack.NPC_POSITIVE_3;
            case 3: return NPCTrack.NPC_POSITIVE_4;
            case 4: return NPCTrack.NPC_POSITIVE_5;
            default: return NPCTrack.NPC_POSITIVE_6;
        }
    }
    
    /**
     * spielt einen zufälligen negativen Track ab
     * @return {@link NPCTrack}
     */
    public NPCTrack playTrackNegative() {
        int n = (int) Math.round((Math.random() * 5d));
        switch (n) {
            case 0: return NPCTrack.NPC_NEGATIVE_1;
            case 1: return NPCTrack.NPC_NEGATIVE_2;
            case 2: return NPCTrack.NPC_NEGATIVE_3;
            case 3: return NPCTrack.NPC_NEGATIVE_4;
            default: return NPCTrack.NPC_NEGATIVE_5;
        }
    }

    public void stopSound() {
        location.getWorld().getNearbyPlayers(location, 50).forEach(p -> p.stopAllSounds());
        if(console) System.out.println("Sounds gestoppt");
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