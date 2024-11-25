public class ChessBoardTest {

    public static void main(String[] args) {
        System.out.println("Starting ChessBoard tests...");

        // Test each piece with varying board sizes
        for (int size = 4; size < 16; size++) {
            runTestsForPiece(new Queen(0, 0), size, "Queen");
        }
        for (int size = 4; size < 16; size++) {
            runTestsForPiece(new Knight(0, 0), size, "Knight");
        }
        for (int size = 10; size < 16; size++) {
            runTestsForPiece(new Superqueen(0, 0), size, "Superqueen");
        }

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

        System.out.println("\n[Test 7] Step-by-step Backtrack:");
        board.clearBoard();
        while (!board.stepBacktrack(piece)) {
            // Delay to simulate animation
            //delay(500);
        }
        System.out.println("Step-by-step Backtrack completed. Board solved: " + board.isSolved());
        board.printBoard(true);

        System.out.println("\n[Test 8] Backtrack to specific row:");
        int row = board.getSize() / 2;
        board.clearBoard();
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
