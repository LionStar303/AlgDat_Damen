public class ChessBoardTest {

    public static void main(String[] args) {
     System.out.println("Starting ChessBoard tests...");
    
     testPiece(new Queen(0,0));
     
     testPiece(new Knight(0,0));
    
     testPiece(new Superqueen(0,0));
    }
  
    private static void testPiece(Piece p){

        // Test 1: Initialize a ChessBoard
        ChessBoard board = new ChessBoard(8); // Assume a constructor with dimensions
        System.out.println("\nChessBoard initialized (8x8):");
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
        System.out.println("\nPlay Backtrack Algorythem:");
        board.playBacktrack(p);
        board.printBoard(true);
        
      }

    private static void delay(long l) {
        try {
            Thread.sleep(l);
        } catch (Exception e) {
            System.err.println("Delay interrupted: " + e.getMessage());
        }
    }
}
