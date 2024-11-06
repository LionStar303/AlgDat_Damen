package de.hsmw.algDatDamen;

public class ChessBoardTest {

    public static void main(String[] args) {
        // aCreate a chessboard with size n x n
        
        int boardSize = 10; // You can change this to any size
        ChessBoard board = new ChessBoard(boardSize);

        // default true but --> activates the Console Messages
        board.setConsoleEnabled(true);

        // Print the final layout of the chessboard
        board.printBoard();
        long i = 0;
        // Test the Backtracking
        while (board.stepBacktrack() == false) { 
        i++;
        } // end of while
        
        System.out.println("Hier Junge -> "+i);
        // Print the final layout of the chessboard
        board.printBoard();
        
    }
}