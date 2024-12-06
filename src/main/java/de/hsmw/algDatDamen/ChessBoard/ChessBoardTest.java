//package de.hsmw.algDatDamen.ChessBoard;

public class ChessBoardTest {
       
       public boolean console = false;

    public static void main(String[] args) {
        System.out.println("Starting ChessBoard tests...");

//        //Test each piece with varying board sizes
//        for (int size = 4; size < 8; size++) {
//            runTestsForPiece(new Queen(), size, "Queen");
//        }
//        for (int size = 4; size < 8; size++) {
//            runTestsForPiece(new Knight(), size, "Knight");
//        }
//        for (int size = 10; size < 12; size++) {
//            runTestsForPiece(new Superqueen(), size, "Superqueen");
//        }
        
        
        ChessBoard board = new ChessBoard(6);
        board.console = false;
        board.clearBoard();
        board.verfyPieces(new Queen());
        int j = 1;
        while (!board.stepBacktrack(new Queen())) {
            // Delay to simulate animation
            //delay(500);
            j++;
        }
        System.out.println("Step-by-step Backtrack completed. Board solved: " + board.isSolved());
        board.printBoard(true);
        board.printBoard(true);
        board.console = true;
        int i = 0;
        while(!board.reverseBackStep(new Queen())){
        //board.printBoard(true);
        i++;
        }
        System.out.println( i + "=="+ j);
        System.out.println("All tests completed.");
    }

    private static void runTestsForPiece(Piece piece, int size, String pieceName) {
        System.out.println("\n========== Running tests for " + pieceName + " on " + size + "x" + size + " board ==========");
        
        ChessBoard board = new ChessBoard(size);
        board.console = false;

        System.out.println("\n[Test 1] Initializing ChessBoard:");
        board.printBoard(true);

        int center = board.getSize() / 2;
        piece.setX(center);
        piece.setY(center);

        System.out.println("\n[Test 2] Adding a " + pieceName + " at (" + center + ", " + center + "):");
        boolean added = board.addPiece(piece);
        System.out.println("Expected: true, Actual: " + added);

        System.out.println("\n[Test 3] Attempting to add the same " + pieceName + " again:");
        boolean addedAgain = board.addPiece(piece);
        System.out.println("Expected: false, Actual: " + addedAgain);

        System.out.println("\n[Test 4] Displaying threatened positions:");
        board.printBoard(true);

        System.out.println("\n[Test 5] Removing the " + pieceName + " from (" + center + ", " + center + "):");
        boolean removed = board.removePiece(piece.getX(), piece.getY());
        System.out.println("Expected: true, Actual: " + removed);

        System.out.println("\n[Test 6] Running Backtrack algorithm:");
        board.playBacktrack(piece);
        board.printBoard(true);
        System.out.println("Board solved: " + board.isSolved());

        System.out.println("\n[Test 7.1] Step-by-step Backtrack:");
        board.clearBoard();
        board.verfyPieces(piece);
        int i = 0;
        while (!board.stepBacktrack(piece)) {
            // Delay to simulate animation
            //delay(500);
            i++;
            if(i >= 100){
                 break;
              }
        }
        board.printBoard(true);
    
        System.out.println("\n[Test 7.2] Step-by-step Backtrack:");
        board.verfyPieces(piece);
        board.stepBacktrack(piece);
        board.printBoard(true);
        i++;
        board.stepBacktrack(piece);
        while (!board.stepBacktrack(piece)) {
            // Delay to simulate animation
            //delay(500);
            i++;
        }
    
        System.out.println("\n[Test 7.3] Step-by-step Backtrack:");
        board.clearBoard();
        board.verfyPieces(piece);
        int j = 0;
        while (!board.stepBacktrack(piece)) {
            // Delay to simulate animation
            //delay(500);
            j++;
        }
        System.out.println( i + "=="+ j);
        System.out.println("Step-by-step Backtrack completed. Board solved: " + board.isSolved());
        board.printBoard(true);

        System.out.println("\n[Test 8] Backtrack to specific row:");
        int row = board.getSize() / 2;
        board.clearBoard();
        board.addPiece(new Knight(1,2));
        board.verfyPieces(piece);
        board.playBacktrackToRow(piece, row);
        System.out.println("Expected pieces: " + row + ", Actual: " + board.getPieces().size());

        System.out.println("\n[Test 9] Backtrack to next piece:");
        board.playBacktrackToNextPiece(piece);
        System.out.println("Expected pieces: " + (row + 1) + ", Actual: " + board.getPieces().size());

        System.out.println("\n[Test 10] Verifying sorted pieces:");
        board.sortPiecesByY();
        boolean isSorted = verifySortedPieces(board);
        System.out.println("Pieces sorted correctly: " + isSorted);
    
        System.out.println("\n[Test 11] Verifying rotate ChessBoard:");
        board.playBacktrack(piece);
        ChessBoard cb = board;
        board.printBoard(true);
        board.rotatePieces(1);
        board.printBoard(true);
        System.out.println("Pieces rotaited correctly(Expected: false): " + cb.equals(board));
        board.rotatePieces(1);
        board.rotatePieces(1);
        board.rotatePieces(1);
        System.out.println("Pieces rotaited correctly(Expected: true): " + (board.isSolved() && cb.equals(board)) );

        System.out.println("\n========== Tests for " + pieceName + " on " + size + "x" + size + " board completed ==========\n");
    }

    private static boolean verifySortedPieces(ChessBoard board) {
        int lastY = -1;
        for (Piece piece : board.getPieces()) {
            if (piece.getY() < lastY) {
                System.err.println("Error: Pieces are not sorted by Y coordinate.");
                return false;
            }
            lastY = piece.getY();
        }
        return true;
    }

    private static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.err.println("Delay interrupted: " + e.getMessage());
        }
    }
}
