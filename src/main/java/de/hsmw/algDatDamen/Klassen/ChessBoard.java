package de.hsmw.algDatDamen;

import java.util.ArrayList;

/**
 * Represents a chessboard for solving the Queen's problem.
 * Allows placing queens, checking for conflicts, and solving using
 * backtracking.
 *
 * @version 1.2, 24.10.2024
 */
public class ChessBoard {

    // Attributes
    private ArrayList<Queen> queens; // List of queens placed on the board
    private int size; // Size of the chessboard (n x n)
    private boolean console; // Controls console messages for debugging

    /**
     * Default constructor that initializes an empty chessboard.
     */
    public ChessBoard() {
        this.size = 0;
        this.queens = new ArrayList<>();
        this.console = true; // Enable console messages by default
    }

    /**
     * Constructor to create a chessboard of a specific size.
     *
     * @param size The size of the chessboard (size x size).
     */
    public ChessBoard(int size) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = true;
    }

    /**
     * Constructor to create a chessboard of a specific size and Console messages.
     *
     * @param size     The size of the chessboard (size x size).
     * @param Controls console messages for debugging
     */
    public ChessBoard(int size, boolean console) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = console;
    }

    // Getters and Setters

    /**
     * Returns the list of queens placed on the chessboard.
     *
     * @return ArrayList of Queen objects representing placed queens.
     */
    public ArrayList<Queen> getQueens() {
        return queens;
    }

    /**
     * Sets a new list of queens on the chessboard.
     *
     * @param newQueens ArrayList of Queen objects to be set on the board.
     */
    public void setQueens(ArrayList<Queen> newQueens) {
        this.queens = newQueens;
    }

    /**
     * Returns the size of the chessboard.
     *
     * @return int The size of the chessboard.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the chessboard.
     *
     * @param newSize The new size of the chessboard.
     */
    public void setSize(int newSize) {
        this.size = newSize;
    }

    /**
     * Sets the size of the chessboard.
     *
     * @param consoleEnabled The state of Console printing/debuging mode.
     */
    public void setConsole(boolean consoleEnabled) {
        this.console = consoleEnabled;
    }

    // Functional Methods

    /**
     * Adds a queen to the chessboard at the specified coordinates.
     * Checks for collisions before placing.
     *
     * @param x The x-coordinate (row) for the queen.
     * @param y The y-coordinate (column) for the queen.
     * @return boolean True if the queen was added successfully, false otherwise.
     */
    public boolean addQueen(int x, int y) {
        if (x >= this.size || y >= this.size) {
            if (console) {
                System.out.println("Position (" + x + ", " + y + ") is out of bounds.");
            }
            return false;
        }

        if (collision(new Queen(x, y))) {
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
     * @param q The queen to check for collisions.
     * @return boolean True if a collision is detected, false otherwise.
     */
    public boolean collision(Queen q) {
        for (Queen queen : queens) {
            if (queen.getX() == q.getX() || queen.getY() == q.getY() ||
                    Math.abs(queen.getX() - q.getX()) == Math.abs(queen.getY() - q.getY())) {
                return true;
            }
        }
        return false; // No collision
    }

    /**
     * Clears all queens from the chessboard.
     * This method removes all queens from the internal list, effectively
     * resetting the chessboard to an empty state.
     * 
     * If the console flag is set to true, a message indicating that the
     * board has been cleaned will be printed to the console.
     */
    public void cleanBoard() {
        queens.clear();
        if (console) {
            System.out.println("Board cleaned.");
        }
    }

    /**
     * Returns the number of queens currently placed on the chessboard.
     * 
     * @return int The total number of queens on the chessboard.
     */
    public int numberOfQueens() {
        return queens.size();
    }

    /**
     * Places a queen on every square of the chessboard.
     * If a queen cannot be placed, a message is printed to the console.
     */
    public void placeAllQueens() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                boolean added = addQueen(x, y);
                if (!added && console) {
                    System.out.println("Cannot place a queen at (" + x + ", " + y + ").");
                }
            }
        }
    }

    /**
     * Prints the chessboard to the console, showing the positions of queens.
     * Queens are represented by 'Q', and empty squares by '.'.
     */
    public void printBoard() {
        System.out.println("------------------------------------------------------------");

        // Print the X-axis labels (columns)
        System.out.print("   "); // Padding for row numbers
        for (int x = 0; x < size; x++) {
            System.out.print(x + " ");
        }
        System.out.print(" x");
        System.out.println(); // Move to the next line after printing X-axis

        // Create and populate the board array
        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }

        // Place queens on the board ('Q')
        for (Queen queen : queens) {
            board[queen.getX()][queen.getY()] = 'Q';
        }

        // Print the board with row numbers (Y-axis labels)
        for (int i = 0; i < size; i++) {
            System.out.print(i + "  "); // Print row number before each row
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row
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
    public boolean playBacktrk() {
        if (console) {
            System.out.println("Start Backtracking Algorithm");
        }
        cleanBoard();
        int row = 0;

        while (numberOfQueens() != size) {
            for (int i = 0; i < size; i++) {
                if (addQueen(row, i)) {
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

        while (!addQueen(row, newY)) {
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
}
