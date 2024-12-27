package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;

public class Teleporter {
    private Material teleporterMaterial = Material.SOUL_CAMPFIRE;
    private Location teleporterLocation;
    private boolean enabled = false;

    public Teleporter(Location teleporterLocation) {
        this.teleporterLocation = teleporterLocation;
        setEnabled(enabled);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        Block block = teleporterLocation.getBlock();

        if (block.getType() == Material.CAMPFIRE || block.getType() == Material.SOUL_CAMPFIRE) {
            BlockData blockData = block.getBlockData();

            if (blockData instanceof Campfire) {
                Campfire campfire = (Campfire) blockData;
                
                campfire.setLit(enabled);

                block.setBlockData(campfire);
            }
        }
    }

    public void setTeleporterMaterial(Material teleporterMaterial) {
        this.teleporterMaterial = teleporterMaterial;
    }

    public void setTeleporterLocation(Location teleporterLocation) {
        this.teleporterLocation = teleporterLocation;
    }

    /**
     * Spawns the Teleporter at the configured Location.
     */
    public void spawnTeleporter() {
        teleporterLocation.getBlock().setType(teleporterMaterial);
        if (enabled) {
            this.setEnabled(enabled);
        } else {
            this.setEnabled(false);
        }
    }

    public boolean isTeleportBlock(Block block) {
        Location blockLocation = block.getLocation();
        Material material = block.getType();


        if (!blockLocation.equals(this.teleporterLocation))
            return false;
        if (!material.equals(this.teleporterMaterial))
            return false;

        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Location getLocation(){
        return teleporterLocation;
    }
}
