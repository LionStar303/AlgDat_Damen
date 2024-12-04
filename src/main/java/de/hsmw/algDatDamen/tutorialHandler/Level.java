package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;

public abstract class Level {

    private String name; // vielleicht als Bossbar anzeigen
    private String description;
    private Location startLocation;
    private int stepCount;
    protected MChessBoard chessBoard;
    protected Player player;
    protected boolean active;
    protected boolean completed;
    protected Step[] steps = new Step[stepCount];

    public Level(String name, String description, MChessBoard chessBoard, Player player, Location startLocation, boolean completed) {
        this.name = name;
        this.description = description;
        this.chessBoard = chessBoard;
        this.player = player;
        this.startLocation = startLocation;
        this.completed = completed;
    }

    public void start() {
        System.out.println("Level: starte level");
        active = true;
        teleportToStart();

        setInventory();
        // Erzeugung eines 8x8 Schachbretts
        chessBoard.setSize(8);
        chessBoard.spawnChessBoard();

        player.setRespawnLocation(startLocation);
        player.sendMessage(name);
        player.sendMessage(description);
        
        playSteps();
    }

    private void teleportToStart() {
        player.teleport(startLocation);
        player.setFlying(false);
    }

    protected abstract void setInventory();
    protected abstract void playSteps();
}
