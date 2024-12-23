package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

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
        villager.setAI(false);
        System.out.println("Villager gespawnt bei: " + villager.getLocation().toString());
    }

    /**
     * @param track der Track der abgespielt wird
     */
    public void playTrack(NPCTrack track) {
        location.getNearbyPlayers(50).forEach(p -> p.sendMessage(track.getText()));
        location.getWorld().playSound(location, track.getSound(), 1, 1);
        if(console) System.out.println("Track abgespielt");
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

    public void moveVillagerWithPathfinding(Location target, double speed) {
        villager.setAI(true);
        // Get the NMS Villager entity
        net.minecraft.world.entity.npc.Villager nmsVillager = ((org.bukkit.craftbukkit.entity.CraftVillager) villager).getHandle();

        // Access the Villager's navigation system
        PathNavigation navigation = nmsVillager.getNavigation();

        // Generate a path to the target
        Path path = navigation.createPath(target.getX(), target.getY(), target.getZ(), 1);

        if (path != null) {
            System.out.println("Villager bewegt sich nach " + target.toString());
            while(navigation.moveTo(path, speed));
        } else {
            System.out.println("Kein Pfad gefunden. Teleportiere Villager");
            // Villager in die Nähe teleportieren
            villager.teleport(new Location(villager.getWorld(), target.getX(), target.getY(), target.getZ()));
        }
        villager.setAI(false);
    }
}