package de.hsmw.algDatDamen.tutorialHandler;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.annotations.Expose;

import de.hsmw.algDatDamen.tutorialHandler.Levels.*;

public class Tutorial {

    @Expose
    private int progress;
    @Expose
    private UUID playerUUID;
    private ArrayList<Level> levels;
    private Level currentLevel;
    private boolean console;

    public Tutorial(boolean console, Player player, int progress) {
        this(console, player.getUniqueId(), progress);
    }

    public Tutorial(boolean console, UUID playerUUID, int progress) {
        this.playerUUID = playerUUID;
        this.progress = progress;
        this.console = console;
    }

    public void initialize() {
        // Level ArrayList initialisieren
        levels = new ArrayList<Level>();

        // Levels erstellen
        levels.add(new Level1(console, getPlayer(), this));
        levels.add(new Level2(console, getPlayer(), this));
        levels.add(new Level3(console, getPlayer(), this));
        levels.add(new Level4(console, getPlayer(), this));
        levels.add(new Level5(console, getPlayer(), this));

        // aktuelles Level basierend auf dem gespeicherten Progress setzen
        currentLevel = levels.get(progress);
    }

    public void start() {
        if(console) System.out.println("Tutorial: starte Tutorial");
        currentLevel.start();
        // Progress Bar auf Level 1 setzen
        getPlayer().setLevel(progress);
        getPlayer().setExp(0);
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
        getPlayer().setLevel(progress);
        getPlayer().setExp(0);
        currentLevel = levels.get(progress);
    }
}
