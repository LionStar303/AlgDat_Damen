public class ChessBoardTest {

    public static void main(String[] args) {
     System.out.println("Starting ChessBoard tests...");
    
     testPiece(new Queen(0,0), 4);
     
     testPiece(new Knight(0,0),10);
    
     testPiece(new Superqueen(0,0),10);
    }
  
    private static void testPiece(Piece p, int size){

        // Test 1: Initialize a ChessBoard
        ChessBoard board = new ChessBoard(size); // Assume a constructor with dimensions
        System.out.println("\nChessBoard initialized (8x8):");
        board.console = false;
        board.printBoard(true); // Assuming a method to display the board
    
        int position = board.getSize() / 2;    
        p.setX(position);    
        p.setY(position);
    
        // Test 2: Add a Queen to the board
        System.out.println("\nAdding a Queen at position ("+position+","+position+"):");
        boolean queenAdded = board.addPiece(p);
        System.out.println("Queen added: " + queenAdded);
        board.printBoard(false);

        // Test 3: Attempt to add a Queen in an invalid position
        System.out.println("\nTrying to add a Queen at position ("+position+","+position+") again:");
        boolean queenAddedInvalid = board.addPiece(p);
        System.out.println("Queen added (expected false): " + queenAddedInvalid);

        // Test 4: Check for threatened positions
        System.out.println("\nDisplaying threatened positions for all Queens:");
        board.printBoard(true);

        // Test 5: Remove a Queen
        System.out.println("\nRemoving a Queen at position ("+position+","+position+"):");
        boolean queenRemoved = board.removePiece(p.getX(), p.getY());
        System.out.println("Queen removed: " + queenRemoved);
        board.printBoard(true);
        
        // Test 6: Play Backtrack
        try {
        System.out.println("\nPlay Backtrack Algorythem:");
        board.playBacktrack(p);
        board.printBoard(true);
        System.out.println("isSolved:"+ board.isSolved()); 
        } catch(Exception e) {
           System.out.println(e.getMessage());
           if(e.getMessage().equals("-1")){
           System.out.println("Dosent Work with " + p.getLetter());
           }
        } 
        
        /* Test 7: Play Backtrack Steps
        try {
        System.out.println("\nPlay Backtrack Algorythem:");
        board.clearBoard();
        while (!board.stepBacktrack(p)) { 
        } // end of while
        System.out.println("Abgeschlossen");
        board.printBoard(true); 
        } catch(Exception e) {
           System.out.println(e.getMessage());
           if(e.getMessage().equals("-1")){
           System.out.println("Dosent Work with " + p.getLetter());
           }
        } 
    
        // Test 8: Play Backtrack To Row
        try {
        int row = board.size/2;
        System.out.println("\nPlay Backtrack To Row " + row + ":");
        board.clearBoard();
        board.verfyPieces();
        board.playBacktrackToRow(p, row);
        board.printBoard(true); 
        } catch(Exception e) {
           System.out.println(e.getMessage());
           if(e.getMessage().equals("-1")){
           System.out.println("Dosent Work with " + p.getLetter());
           }
        } 
    
        // Test 8: Play Backtrack To Next
        try {
        System.out.println("\nPlay Backtrack To Next:");
        board.verfyPieces();
        board.playBacktrackToNextPiece(p);
        board.printBoard(true); 
        } catch(Exception e) {
           System.out.println(e.getMessage());
           if(e.getMessage().equals("-1")){
           System.out.println("Dosent Work with " + p.getLetter());
           }
        } 
    */
    
      } 

    private static void delay(long l) {
        try {
            Thread.sleep(l);
        } catch (Exception e) {
            System.err.println("Delay interrupted: " + e.getMessage());
        }
    }
}
