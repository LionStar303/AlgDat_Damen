package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.tutorialHandler.Levels.level1.Level1;

public class Tutorial {

    Level firstLevel;
    Player player;

    public Tutorial(Player player) {
        this.player = player;
    }

    public void initialize() {
        firstLevel = new Level1(player);
        
    }

    public void start() {
        System.out.println("Tutorial: starte Tutorial");
        firstLevel.start();
    }

    public Player getPlayer() {
        return player;
    }
}