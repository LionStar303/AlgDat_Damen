package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;

public class Level {

    private String name;
    private Step firstStep;
    private MChessBoard chessBoard;
    private Player player;
    private Location startLocation;

    public Level(String name, MChessBoard chessBoard, Player player) {
        this.name = name;
        this.chessBoard = chessBoard;
        this.player = player;
    }

    public void initialize() {
        readSteps();
    }

    public void start() {
        
        // Inventar des Spielers leeren
        player.getInventory().clear();
        player.teleport(startLocation);
    }

    private void readSteps() {

    }
}
