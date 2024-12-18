package de.hsmw.algDatDamen.tutorialHandler;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import de.hsmw.algDatDamen.AlgDatDamen;

public class Teleporter {
    private Material teleporterMaterial = Material.SOUL_CAMPFIRE;
    private Location teleporterLocation;
    private boolean enabled;


    public Teleporter(Location teleporterLocation) {
        this.teleporterLocation = teleporterLocation;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
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
    }
}
