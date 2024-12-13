package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.tutorialHandler.Levels.Level1;

public class Tutorial {

    Level currentLevel;
    Player player;

    public Tutorial(Player player) {
        this.player = player;
    }

    public void initialize() {
        currentLevel = new Level1(player);
        
    }

    public void start() {
        System.out.println("Tutorial: starte Tutorial");
        currentLevel.start();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
    public Player getPlayer() {
        return player;
    }
}