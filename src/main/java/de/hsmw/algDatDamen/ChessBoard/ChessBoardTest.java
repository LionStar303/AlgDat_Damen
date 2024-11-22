package de.hsmw.algDatDamen.ChessBoard;


public class ChessBoardTest {

  public static void main(String[] args) {
    // aCreate a chessboard with size n x n

    int boardSize = 5; // You can change this to any size
    int queens = 3;
    ChessBoard board = new ChessBoard(boardSize);
    
    /*
    // default true but --> activates the Console Messages
    board.setConsoleEnabled(true);

    // Print the final layout of the chessboard
    board.printBoard();

    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        board.addQueen(new Queen(i, j));
      }
      i++;
    } // end of for
    // Test the Backtracking

    board.addQueen(new Queen(boardSize - 2, boardSize - 1));

    board.playBacktrack();
    board.removeLastQueen();
    board.removeLastQueen();
    board.removeLastQueen();

    board.addQueen(new Queen(boardSize - 1, boardSize - 1));

    board.verfyQueens();
    System.out.println(board.numberOfQueens());
    board.printBoard();

    // Print the final layout of the chessboard
    board.printBoard();
    */
    
    //board.bongoSolve();
    board.stateX = 0;
        while(board.bongoStep() == false){
            if(board.console){
                board.printBoard();
                
            }
      delay(5);
            if (board.stateX == (board.size)) {
                board.removeAllQueens();
                board.stateX = 0;
            }
        }
  }

  private static void delay(long l) {
    try {
      Thread.sleep(l);
    } catch (Exception e) {

    } finally {

    } // end of try

  }

}