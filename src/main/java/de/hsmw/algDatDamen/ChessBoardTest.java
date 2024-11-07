package de.hsmw.algDatDamen;
import java.io.*;
import java.lang.Thread;

//import ..ChessBoard;


public class ChessBoardTest {

    public static void main(String[] args) {
        // aCreate a chessboard with size n x n
        
        int boardSize = 16; // You can change this to any size
        ChessBoard board = new ChessBoard(boardSize);

        // default true but --> activates the Console Messages
        board.setConsoleEnabled(false);

        // Print the final layout of the chessboard
        board.printBoard();
        long i = 0;
        // Test the Backtracking
        while (board.stepBacktrack() == false) { 
        i++;
        //long l = 50;
        try {
               // Thread.sleep(l);  
        } catch(Exception e) {
          
        } finally {
          
        } // end of try

        } // end of while
        
        System.out.println("Hier Junge -> "+i);
        // Print the final layout of the chessboard
        board.printBoard();
        
    }
}