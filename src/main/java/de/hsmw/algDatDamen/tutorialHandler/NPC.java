package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import com.destroystokyo.paper.entity.Pathfinder;
import de.hsmw.algDatDamen.AlgDatDamen;

public class NPC implements Listener {
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

    public void setSlowness(boolean slowness) {
        if(slowness) {
            villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 9999, 9999, false, false));
        } else {
            villager.clearActivePotionEffects();
        }
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
        setSlowness(true);
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
        setSlowness(false);
        // Get the NMS Villager entity
        //net.minecraft.world.entity.npc.Villager nmsVillager = ((org.bukkit.craftbukkit.entity.CraftVillager) villager).getHandle();

        // Access the Villager's navigation system
        //PathNavigation navigation = nmsVillager.getNavigation();

        // Generate a path to the target
        //Path path = navigation.createPath(target.getX(), target.getY(), target.getZ(), 2);

        Pathfinder villagerPathfinder = villager.getPathfinder();

        boolean result = villagerPathfinder.moveTo(target);

        if(/* path == null || */ !result) {
            System.out.println("Kein Pfad gefunden. Teleportiere Villager");
            // Villager in die Nähe teleportieren
            villager.teleport(target);
            setSlowness(true);
        }
        
        if (villagerPathfinder.hasPath()) {
            System.out.println("Villager bewegt sich.");

            BukkitTask[] villagerTask = new BukkitTask[2]; // Ein Array, um die Referenz später zu setzen
            villagerTask[0] = Bukkit.getScheduler().runTaskTimer(AlgDatDamen.getInstance(), () -> {
                if (villager.getLocation().distanceSquared(target) < 2) {
                    setSlowness(true);
                    System.out.println("Villager wird gefreezed.");
                    villagerTask[0].cancel(); // Zugriff auf die Array-Referenz
                }
            }, 0L, 3L);
            System.out.println("Task gestartet: " + villagerTask[0].toString());

            int delay = 3;  // Sekunden

            villagerTask[1] = Bukkit.getScheduler().runTaskLater(AlgDatDamen.getInstance(), () -> {
                if (!villagerTask[0].isCancelled()) {
                    System.out.println("Villager hat Ziel nicht gefunden... teleportiere.");
                    villager.teleport(target);
                    setSlowness(true);
                    villagerTask[0].cancel();
                }
                villagerTask[1].cancel();
            }, 20L * delay);
        }      
    }
}