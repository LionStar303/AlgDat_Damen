package de.hsmw.algDatDamen.tutorialHandler;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import de.hsmw.algDatDamen.AlgDatDamen;

public class Teleporter {
    private Material teleporterMaterial = Material.SOUL_CAMPFIRE;
    private Location teleporterLocation;
    private boolean enabled = false;

    public Teleporter(Location teleporterLocation) {
        this.teleporterLocation = teleporterLocation;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            if (teleporterMaterial == Material.CAMPFIRE || teleporterMaterial == Material.SOUL_CAMPFIRE) {
                List<MetadataValue> metadata = teleporterLocation.getBlock().getMetadata("lit");
                metadata.set(0, new FixedMetadataValue(AlgDatDamen.getInstance(), true));
            }
        } else {
            if (teleporterMaterial == Material.CAMPFIRE || teleporterMaterial == Material.SOUL_CAMPFIRE) {
                List<MetadataValue> metadata = teleporterLocation.getBlock().getMetadata("lit");
                metadata.set(0, new FixedMetadataValue(AlgDatDamen.getInstance(), false));
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

        if (blockLocation != this.teleporterLocation)
            return false;
        if (material != this.teleporterMaterial)
            return false;

        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
