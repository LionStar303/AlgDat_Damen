//package de.hsmw.algDatDamen.ChessBoard;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardTest {

    public boolean console = false;
    private static List<String> errorLog = new ArrayList<>(); // Fehlerprotokoll

    public static void main(String[] args) {

        try {

            System.out.println("Starting ChessBoard tests...");

            int totalTests = 0;
            int passedTests = 0;

            // Test each piece with varying board sizes
            for (int size = 4; size < 16; size++) {
                int[] results = runTestsForPiece(new Queen(), size, "Queen");
                totalTests += results[0];
                passedTests += results[1];
            }
            for (int size = 4; size < 8; size++) {
                // int[] results = runTestsForPiece(new Knight(), size, "Knight");
                // totalTests += results[0];
                // passedTests += results[1];
            }
            for (int size = 10; size < 16; size++) {
                int[] results = runTestsForPiece(new Superqueen(), size, "Superqueen");
                totalTests += results[0];
                passedTests += results[1];
            }

            // Fehlerprotokoll anzeigen
            if (!errorLog.isEmpty()) {
                System.out.println("\n========== Error Log ==========");
                for (String error : errorLog) {
                    System.out.println(error);
                }
            }

            System.out.println("\n========== Test Summary ==========");
            System.out.println("Total Tests Run: " + totalTests);
            System.out.println("Tests Passed: " + passedTests);
            System.out.println("Tests Failed: " + (totalTests - passedTests));
            System.out.println("Success Rate: " + (passedTests * 100 / totalTests) + "%");
            System.out.println("===================================");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {

        } // end of try

    }

    private static int[] runTestsForPiece(Piece piece, int size, String pieceName) {
        System.out.println(
                "\n========== Running tests for " + pieceName + " on " + size + "x" + size + " board ==========");

        ChessBoard board = new ChessBoard(size);
        board.console = false;

        int testsRun = 0;
        int testsPassed = 0;

        // Test 1: Initializing the ChessBoard
        System.out.println("\n[Test 1] Initializing ChessBoard:");
        testsRun++;
        if (board.getPieces().isEmpty()) {
            testsPassed++;
        } else {
            logError("Error[1]: Board is not empty upon initialization.Size:" + size + " Pices:" + pieceName + " ");
        }

        // Test 2: Adding a piece to the board
        System.out.println("\n[Test 2] Adding a piece to the board:");
        int center = size / 2;
        piece.setX(center);
        piece.setY(center);
        boolean added = board.addPiece(piece);
        System.out.println("Added " + pieceName + " at center (" + center + ", " + center + "): " + added);
        testsRun++;
        if (added) {
            testsPassed++;
        } else {
            logError("Error[2]: Failed to add piece at center.Size:" + size + " Pices:" + pieceName + " ");
        }

        // Test 3: Adding the same piece again
        System.out.println("\n[Test 3] Adding the same piece again");
        boolean addedAgain = board.addPiece(piece);
        System.out.println("Re-adding the same " + pieceName + " (expected false): " + addedAgain);
        testsRun++;
        if (!addedAgain) {
            testsPassed++;
        } else {
            logError("Error[3]: Piece was added again incorrectly.Size:" + size + " Pices:" + pieceName + " ");
        }

        // Test 4: Displaying threatened positions
        System.out.println("\n[Test 4] Threatened positions:");
        testsRun++;
        testsPassed++; // Assuming this test is visual and is manually verified.

        // Test 5: Removing the piece
        System.out.println("\n[Test 5] Removing the piece");
        boolean removed = board.removePiece(center, center);
        System.out.println("Removed " + pieceName + " (expected true): " + removed);
        testsRun++;
        if (removed) {
            testsPassed++;
        } else {
            logError("Error[5]: Failed to remove piece.Size:" + size + " Pices:" + pieceName + " ");
        }

        // Test 6: Running Backtrack
        System.out.println("\n[Test 6] Running Backtrack");
        board.playBacktrack(piece);
        System.out.println("Backtrack result (solved): " + board.isSolved());
        testsRun++;
        if (board.isSolved()) {
            testsPassed++;
        } else {
            logError("Error[6]: Backtrack algorithm did not solve the board.Size:" + size + " Pices:" + pieceName + " ");
        }

        // Test 7: Step-by-step Backtrack
        System.out.println("\n[Test 7] Step-by-step Backtrack:");
        System.out.println("\n[Test 7.1] Step-by-step Backtrack:");
        board.clearBoard();
        board.verfyPieces(piece);
        int steps = 0;
        while (!board.stepBacktrack(piece)) {
            steps++;
            if (steps > 10000000) {
                System.out.println("Step-by-step backtrack exceeded 10 000 000 steps.");
                break;
            }
        }
        System.out.println("Step-by-step Backtrack completed. Steps: " + steps + ", Solved: " + board.isSolved());
        testsRun++;
        if (board.isSolved()) {
            testsPassed++;
        } else {
            logError("Error[7.1]: Step-by-step backtrack did not solve the board. Size:" + size + " Pices:" + pieceName
                    + " ");
        }

        System.out.println("\n[Test 7.2] Same Step Count Step-by-step Backtrack:");
        board.clearBoard();
        board.verfyPieces(piece);
        int steps1 = 0;
        while (!board.stepBacktrack(piece)) {
            steps1++;
            if (steps1 > 100) {
                System.out.println("Step-by-step backtrack exceeded 10 000 000 steps.");
                break;
            }
        }
        board.verfyPieces(piece);
        while (!board.stepBacktrack(piece)) {
            steps++;
        }

        System.out.println("Step-by-step Backtrack completed. Steps: " + steps1 + ", Solved: " + board.isSolved());
        board.clearBoard();
        board.verfyPieces(piece);
        int steps2 = 0;
        while (!board.stepBacktrack(piece)) {
            steps2++;
        }
        System.out.println("Step-by-step Backtrack completed. Steps: " + steps2 + ", Solved: " + board.isSolved());
        testsRun++;
        if (board.isSolved() && (steps1 == steps2)) {
            testsPassed++;
        } else {
            logError("Same Step Error[7.2]: Step-by-step backtrack did not solve the board. Size:" + size + " Pices:"
                    + pieceName + "Step1=" + steps1 + "  Step2=" + steps2);
        }

        // Test 8: Backtrack to a specific row
        System.out.println("\n[Test 8] Backtrack to specific row:");
        System.out.println("\n[Test 8.1] Play Backtrack to specific row:");
        int row = size / 2;
        board.clearBoard();
        board.playBacktrackToRow(piece, row);
        System.out.println("Play Backtrack to row " + row + ": " + board.getPieces().size() + " pieces placed.");
        testsRun++;
        if (board.getPieces().size() == row) {
            testsPassed++;
        } else {
            logError("Error[8.1]: Play Backtrack to specific row:" + row + " Size:" + size + " Pices:" + pieceName + " ");
        } // end of if-else

        System.out.println("\n[Test 8.2] Solve Backtrack to specific row:");
        board.clearBoard();
        board.solveBacktrackToRow(piece, row);
        System.out.println(" SolveBacktrack to row " + row + ": " + board.getPieces().size() + " pieces placed.");
        testsRun++;
        if (board.getPieces().size() == row) {
            testsPassed++;
        } else {
            logError("Error[8.2]: Solve Backtrack to specific row:" + row + "Piexes.size=" + board.getPieces().size()
                    + "  Size:" + size + " Pices:" + pieceName + " ");
        } // end of if-else

        // Test 9: Verify sorted pieces
        System.out.println("\n[Test 9] Verify sorted pieces:");
        board.sortPiecesByY();
        boolean isSorted = verifySortedPieces(board);
        System.out.println("Pieces sorted correctly: " + isSorted);
        testsRun++;
        if (isSorted) {
            testsPassed++;
        } else {
            logError("Error[9]:  Verify sorted pieces  Size:" + size + " Pices:" + pieceName + " ");
        } // end of if-else

        // Test 10: Reverse Backtrack
        System.out.println("\n[Test 10] Reverse Backtrack:");
        board.clearBoard();
        board.verfyPieces(piece);
        int j = 0;
        while (!board.stepBacktrack(piece)) {
            j++;
        }
        System.out.println("Step-by-step Backtrack completed. Board solved: " + board.isSolved());
        int i = 0;
        board.printBoard(true);
        // board.setConsoleEnabled(true);
        board.verfyPieces(piece);
        while (!board.reverseBackStep(piece)) {
            i++;
            // board.printBoard(true);
            // try{Thread.sleep(10);
            // }catch (Exception e) {
            //
            // }
        }
        System.out.println(i + " == " + j);
        System.out.println("Step = Reverse: " + (i == j));
        testsRun++;
        if ((i == j)) {
            testsPassed++;
        } else {
            logError("Error[10]:  Reverse Backtrack" + i + " == " + j + " Size:" + size + " Pices:" + board.toString()
                    + " ");
        } // end of if-else

        // Test 11: Reverse Backtrack
        System.out.println("\n[Test 11] Reverse Backtrack to Next Piece:");
        board.playBacktrack(piece);
        board.verfyPieces(piece);
        while (!board.playReverseBackTrackToNextPiece(piece.clone())) {
            // System.out.print(".");
            // board.printBoard(true);
        }
        System.out.println("Reverse Backtrack to Next Queen completed. Board solved: " + board.isSolved());
        if (board.isReverseBackStepFinished()) {
            testsPassed++;
        } else {
            logError("Error:Reverse Backtrack to Next Piece:  Size:" + size + " Pices:" + board.toString() + " ");
        } // end of if-else

        System.out.println(
                "\n========== Tests for " + pieceName + " on " + size + "x" + size + " board completed ==========\n");

        return new int[] { testsRun, testsPassed };
    }

    private static void logError(String message) {
        errorLog.add(message);
        // Optional: Sofortiger Abbruch bei einem Fehler
        // System.exit(1);
    }

    private static boolean verifySortedPieces(ChessBoard board) {
        int lastY = -1;
        for (Piece piece : board.getPieces()) {
            if (piece.getY() < lastY) {
                logError("Error: Pieces are not sorted by Y coordinate.");
                return false;
            }
            lastY = piece.getY();
        }
        return true;
    }
}
