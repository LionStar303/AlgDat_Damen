package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;

public abstract class Level {

    private String name; // vielleicht als Bossbar anzeigen
    private String description;
    private Location startLocation;
    protected MChessBoard chessBoard;
    protected Player player;
    protected boolean completed;

    public Level(String name, String description, MChessBoard chessBoard, Player player, Location startLocation, boolean completed) {
        this.name = name;
        this.description = description;
        this.chessBoard = chessBoard;
        this.player = player;
        this.startLocation = startLocation;
        this.completed = completed;
    }

    public abstract void start();

    public void teleportToStart() {
        player.teleport(startLocation);
        player.setFlying(false);
    }
}
