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

    /**
     * Default constructor that initializes an empty chessboard.
     */
    public ChessBoard() {
        this.size = 0;
        this.queens = new ArrayList<>();
        this.console = true; // Enable console messages by default
        this.stateX = 0;
        this.stateY = 0;
    }

    /**
     * Constructor to create a chessboard of a specific size.
     *
     * @param size The size of the chessboard (size x size).
     */
    public ChessBoard(int size) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = false;
        this.stateX = 0;
        this.stateY = 0;
    }

    /**
     * Constructor to create a chessboard of a specific size with console messages.
     *
     * @param size    The size of the chessboard (size x size).
     * @param console Enables console messages for debugging.
     */
    public ChessBoard(int size, boolean console) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = console;
        this.stateX = 0;
        this.stateY = 0;
    }

    // Getters and Setters

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
     --------------Functional Methods-----------------
     *
     */


    /**
     * Adds a new Queen to the ArrayList queens
     *
     * @param q The queen to adding the ArrayList queens
     */
    public void addQueen(Queen q) {
        queens.add(q);
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
     * Checks if the given queen collides with any existing queens.
     *
     * @param queen The queen to check for collisions.
     * @return True if a collision is detected, false otherwise.
     */
    public boolean checkCollision(Queen queen) {
        for (Queen q : queens) {
            if (q.getX() == queen.getX() || q.getY() == queen.getY() ||
                    Math.abs(q.getX() - queen.getX()) == Math.abs(q.getY() - queen.getY())) {
                return true;
            }
        }
        return false;
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

        System.out.print("   ");
        for (int x = 0; x < size; x++) {
            System.out.print(x + " ");
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
            System.out.print(i + "  ");
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\ny");
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Solves the Queen's problem using a backtracking algorithm.
     * It tries to place all queens on the board without any conflicts.
     *
     * @return boolean True if the algorithm successfully places all queens, false
     * otherwise.
     */
    public boolean playBacktrack() {
        sortQueensByX();
       // int row = 0;
       /* for (Queen q : queens) {
            if (q.getY() != row) {
                if (console) {
                    System.out.println("Übertrag Abbruch");
                    System.out.println(row + " == ROW  != " + q.getY());
                }
                break;
            }
            queens.remove(q);
            if (console) {
                System.out.println("Vor Collisions Prüfung");
                System.out.println(row + " == ROW  != " + q.getY());
            }
            if (checkCollision(q) == false) {

                newQ.add(q);
            }
            row++;
        }*/

        if (console) {
            System.out.println("Start Backtracking Algorithm");
        }

        int row = 0;

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

    @Override
    public String toString() {
        return "Chessboard size: " + size + "x" + size + ", Queens placed: " + numberOfQueens();
    }

    public int numberOfQueens(){
        return queens.size();
    }
}