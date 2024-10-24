package de.hsmw.algDatDamen;


import org.bukkit.plugin.java.JavaPlugin;



public final class AlgDatDamen extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Moin!");

        // Create a chessboard with size n x n
        int boardSize = 8; // You can change this to any size
        ChessBoard board = new ChessBoard(boardSize);

        // default true but --> activates the Console Messages
        board.setConsole(true);

        // Place queens on all fields of the board
        board.placeAllQueens();

        // Print the final layout of the chessboard
        board.printBoard();

        // Test the Backtracking
        board.playBacktrk();

        // Print the final layout of the chessboard
        board.printBoard();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

