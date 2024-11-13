package de.hsmw.algDatDamen;

import java.util.ArrayList;

/**
 * Represents a chessboard for solving the Queen's problem.
 * Allows placing queens, checking for conflicts, and solving using
 * a backtracking algorithm.
 *
 * @version 1.2, 24.10.2024
 */
public class ChessBoard {

    // Attributes
    protected ArrayList<Queen> queens; // List of queens placed on the board
    protected int size; // Size of the chessboard (n x n)
    protected boolean console; // Controls console messages for debugging
    protected int stateX; // Tracking the state along X-axis
    protected int stateY; // Tracking the state along Y-axis

    // Getters and Setters

    public ChessBoard(int boardSize) {
        this.size = boardSize;
        this.queens = new ArrayList<>();
        this.console = true;
        this.stateX = 0;
        this.stateY = 0;
    }

    public ChessBoard() {
        this.size = 1;
        this.queens = new ArrayList<>();
        this.console = true;
        this.stateX = 0;
        this.stateY = 0;
    }

    public boolean isConsole() {
        return console;
    }

    /**
     * Retrieves the list of queens currently placed on the chessboard.
     *
     * @return The list of queens placed on the board.
     */
    public ArrayList<Queen> getQueens() {
        return queens;
    }

    /**
     * Sets the list of queens for the chessboard.
     *
     * @param queens The list of queens to be placed on the chessboard.
     */
    public void setQueens(ArrayList<Queen> queens) {
        this.queens = queens;
    }

    /**
     * Retrieves the size of the chessboard (n x n).
     *
     * @return The size of the chessboard.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the chessboard (n x n).
     *
     * @param size The new size of the chessboard.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Checks if console messages are enabled for debugging purposes.
     *
     * @return true if console messages are enabled, false otherwise.
     */
    public boolean isConsoleEnabled() {
        return console;
    }

    /**
     * Sets whether console messages are enabled for debugging purposes.
     *
     * @param consoleEnabled true to enable console messages, false to disable.
     */
    public void setConsoleEnabled(boolean consoleEnabled) {
        this.console = consoleEnabled;
    }

    /**
     * Retrieves the current state along the X-axis on the chessboard.
     *
     * @return The current state along the X-axis.
     */
    public int getStateX() {
        return stateX;
    }

    /**
     * Sets the current state along the X-axis on the chessboard.
     *
     * @param stateX The new state along the X-axis.
     */
    public void setStateX(int stateX) {
        this.stateX = stateX;
    }

    /**
     * Retrieves the current state along the Y-axis on the chessboard.
     *
     * @return The current state along the Y-axis.
     */
    public int getStateY() {
        return stateY;
    }

    /**
     * Sets the current state along the Y-axis on the chessboard.
     *
     * @param stateY The new state along the Y-axis.
     */
    public void setStateY(int stateY) {
        this.stateY = stateY;
    }

    /**
     *
     * --------------Functional Methods-----------------
     *
     */

    public boolean isSolved() {
        if (numberOfQueens() == size) {
            for (Queen q : queens) {
                if (checkCollision(q)) {
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    /**
     * Adds a new Queen to the ArrayList queens
     *
     * @param q The queen to adding the ArrayList queens
     */
    public boolean addQueen(Queen q) {
        for (Queen qold : queens) {
            if ((qold.getX() == q.getX()) && (qold.getY() == q.getY())) {
                return false;
            }
        }
        queens.add(q);
        return true;
    }

    public void removeQueen(Queen q) {
        for (Queen qarray : queens) {
            if (q.equals(qarray)) {
                queens.remove(qarray);
            } // end of if
        }
    }

    public void removeLastQueen() {
        queens.remove(queens.size() - 1);
    }

    /**
     * Sorts queens based on their Y coordinates.
     */
    public void sortQueensByY() {
        if (console) {
            printBoard();
        }
        queens.sort((q1, q2) -> Integer.compare(q1.getY(), q2.getY()));
        if (console) {
            printBoard();
        }
    }

    /**
     * Sorts queens based on their X coordinates.
     */
    public void sortQueensByX() {
        if (console) {
            printBoard();
        }
        queens.sort((q1, q2) -> Integer.compare(q1.getX(), q2.getX()));
        if (console) {
            printBoard();
        }
    }

    /**
     * Adds a queen to the chessboard at the specified coordinates.
     * Checks for conflicts before placing.
     *
     * @param x The x-coordinate for the queen.
     * @param y The y-coordinate for the queen.
     * @return True if the queen was added successfully, false otherwise.
     */
    public boolean addTestedQueen(int x, int y) {
        if (x >= this.size || y >= this.size) {
            if (console) {
                System.out.println("Position (" + x + ", " + y + ") is out of bounds.");
            }
            return false;
        }
        if (checkCollision(new Queen(x, y))) {
            if (console) {
                System.out.println("Cannot place queen at (" + x + ", " + y + ") due to a conflict.");
            }
            return false;
        }
        queens.add(new Queen(x, y));
        if (console) {
            System.out.println("Queen added at (" + x + ", " + y + ").");
        }
        return true;
    }

    /**
     * Checks whether the given queen collides with any existing queens
     * on the chessboard (in the same row, column, or diagonal).
     *
     * @param queen The queen to check for collisions.
     * @return True if a collision is detected, false otherwise.
     */
    public boolean checkCollision(Queen queen) {
        int x = queen.getX();
        int y = queen.getY();
        for (Queen q : queens) {
            // Check for row, column, or diagonal conflicts
            if (!q.equals(queen)) {
                if (q.getX() == x || q.getY() == y ||
                        Math.abs(q.getX() - x) == Math.abs(q.getY() - y)) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision found
    }

    /**
     * Checks if placing a queen at the specified coordinates (x, y) would cause a
     * collision
     * with any existing queens on the chessboard. A collision occurs if the new
     * queen
     * would be in the same row, column, or diagonal as any already-placed queen.
     *
     * @param x The x-coordinate (column) where the new queen is to be placed.
     * @param y The y-coordinate (row) where the new queen is to be placed.
     * @return true if a collision is detected with any existing queens;
     *         false if there are no conflicts.
     */
    public boolean checkCollision(int x, int y) {
        for (Queen queen : queens) {
            // Check for row, column, or diagonal conflicts
            if (queen.getX() == x || queen.getY() == y ||
                    Math.abs(queen.getX() - x) == Math.abs(queen.getY() - y)) {
                return true; // Collision detected
            }
        }
        return false; // No collision found
    }

    /**
     * Clears all queens from the chessboard.
     */
    public void clearBoard() {
        queens.clear();
        if (console) {
            System.out.println("Board cleaned.");
        }
    }

    /**
     * Prints the chessboard to the console, showing the positions of queens.
     */
    public void printBoard() {
        System.out.println("------------------------------------------------------------");

        System.out.print("\t");
        for (int x = 0; x < size; x++) {
            if (x > 9) {
                System.out.printf(x + " ");
            } else {
                System.out.printf(x + "  ");
            } // end of if-else

        }
        System.out.print(" x\n");

        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }

        for (Queen queen : queens) {
            board[queen.getX()][queen.getY()] = 'Q';
        }

        for (int i = 0; i < size; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println("\n");
        }
        System.out.println("\ny");
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Solves the Queen's problem using a backtracking algorithm.
     * It tries to place all queens on the board without any conflicts.
     *
     * @return boolean True if the algorithm successfully places all queens, false
     *         otherwise.
     */
    public boolean playBacktrack() {
        queens.clear();
        sortQueensByX();
        int row = 0;

        if (console) {
            System.out.println("Start Backtracking Algorithm");
        }

        while (queens.size() != size) {
            for (int i = 0; i < size; i++) {
                if (addTestedQueen(row, i)) {
                    if (console) {
                        System.out.println("Step -> row: " + row);
                        printBoard();
                    }
                    row++;
                    break;
                } else if (i == size - 1) {
                    row = backStep(row) + 1;
                }
            }
        }
        return true;
    }

    /**
     * Performs a backstep in the backtracking algorithm.
     * Removes the last placed queen and tries the next position in the same row.
     *
     * @param row The current row where backtracking is performed.
     * @return int The updated row after backtracking.
     */
    public int backStep(int row) {
        row--;
        int oldY = queens.get(numberOfQueens() - 1).getY();

        queens.remove(numberOfQueens() - 1);

        if (console) {
            System.out.println("Start Backstep -> row: " + row);
        }
        int newY = oldY + 1;

        while (!addTestedQueen(row, newY)) {
            newY++;
            if (console) {
                System.out.println("Back-Place -> row: " + row + "  Y = " + newY);
            }

            if (newY >= size) {
                if (console) {
                    printBoard();
                }
                backStep(row);
                newY = 0;
            }
        }

        if (console) {
            System.out.println("End Backstep -> row: " + row + "  Y = " + newY);
            printBoard();
        }
        return row;
    }

    public boolean stepBacktrack() {
        if ((queens.size() == size)) {
            return true;
        } // end of if

        // sortQueensByX();
        // verfyQueens();
        // stateX = numberOfQueens();

        if (stateY == size - 1 && stateX == numberOfQueens() - 1) {
            stateY = 0;
            stateX++;
        }

        if (addTestedQueen(stateX, stateY)) {
            stateX++;
            stateY = -1;
            if (console) {
                System.out.println("Step -> row: " + stateX);
                printBoard();
            }
        } else if (stateY >= size - 1) {
            stateX--;
            stateY = queens.get(numberOfQueens() - 1).getY();
            queens.remove(numberOfQueens() - 1);
        }

        stateY++;
        return false;
    }

    public boolean playBacktrackToRow(int x) {
        if (x > this.size) {
            return false;
        } // end of if
        while (getStateX() != x) {
            stepBacktrack();
        } // end of while
        return true;
    }

    public boolean playBacktrackToNextQueen() {
        int numQ = queens.size();
        while (numQ == queens.size()) {
            stepBacktrack();
        } // end of while
        return isSolved();
    }

    public void verfyQueens() {
        sortQueensByX();
        for (int i = queens.size() - 1; i > 0; i--) {

            if (console) {
                System.out.println("Check Collision = " + checkCollision(queens.get(i)));
                System.out.println("(" + queens.get(i).getX() + " == " + i + ")");
            } // end of if

            // Check for Collision
            if (!(checkCollision(queens.get(i))) &&
            // Check for right line in X direction
                    (queens.get(i).getX() == i)) {

            } else {
                queens.remove(queens.get(i));
            } // end of if-else

        }

        setStateX(numberOfQueens());
    }

    public void rotateQueens(int rotations) {
        // Reduce rotations to within [0-3] (4 rotations would result in the same
        // original board)
        rotations = rotations % 4;

        for (int i = 0; i < rotations; i++) {
            for (Queen queen : queens) {
                // Apply 90-degree rotation on each queen's position
                int originalX = queen.getX();
                int originalY = queen.getY();
                queen.setX(originalY);
                queen.setY(size - 1 - originalX);
            }
        }
    }

    @Override
    public String toString() {
        return "Chessboard size: " + size + "x" + size + ", Queens placed: " + numberOfQueens();
    }

    public int numberOfQueens() {
        return queens.size();
    }
}