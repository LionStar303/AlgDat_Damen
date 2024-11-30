package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.tutorialHandler.Level;

public class Level1 extends Level{

    private final static String DESCRIPTION = "";
    
        public Level1(MChessBoard chessBoard, Player player) {
            this(chessBoard, player, chessBoard.getOriginCorner().add(0,1,0));
        }
    
        public Level1(MChessBoard chessBoard, Player player, Location startLocation) {
            this(chessBoard, player, startLocation, false);
        }
    
        public Level1(MChessBoard chessBoard, Player player, Location startLocation, boolean completed) {
            // ruft den Konstruktor der Elternklasse Level auf
            super("Level 1", DESCRIPTION, chessBoard, player, startLocation, completed);
    }

    @Override
    public void start() {
        player.getInventory().clear();
        teleportToStart();
    }
    
}
