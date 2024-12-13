package de.hsmw.algDatDamen.tutorialHandler;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.annotations.Expose;

import de.hsmw.algDatDamen.tutorialHandler.Levels.Level1;

public class Tutorial {

    @Expose
    private int progress;
    @Expose
    private UUID playerUUID;
    private ArrayList<Level> levels;
    private Level currentLevel;

    public Tutorial(Player player, int progress) {
        this.playerUUID = player.getUniqueId();
        this.progress = progress;
    }

    public Tutorial(UUID playerUUID, int progress) {
        this.playerUUID = playerUUID;
        this.progress = progress;
    }

    public void initialize() {
        // Level ArrayList initialisieren
        levels = new ArrayList<Level>();

        // Levels erstellen
        levels.add(new Level1(getPlayer(), this));

        // aktuelles Level basierend auf dem gespeicherten Progress setzen
        currentLevel = levels.get(progress);
    }

    public void start() {
        System.out.println("Tutorial: starte Tutorial");
        currentLevel.start();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return this.progress;
    }

    public void incProgress() {
        progress++;
    }
}
