package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;

public class Tutorial {

    Level firstLevel;
    Player player;

    public Tutorial(Player player) {
        this.player = player;
    }

    public void initialize(MChessBoard chessBoard) {
        firstLevel = new Level("Testlevel", chessBoard, player);
        firstLevel.start();
    }

    public void start() {
        firstLevel.start();
    }
}