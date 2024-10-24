package de.hsmw.algDatDamen;
/**
 * This class represents a Queen on the chessboard.
 * It stores the position of the queen and whether it is a "super queen."
 *
 * @version 1.1, 24.10.2024
 */
public class Queen {

    // Attributes
    private int x; // Row position
    private int y; // Column position
    private boolean isSuperQueen;

    /**
     * Constructor to create a regular queen at the specified position.
     *
     * @param x The x-coordinate (row) of the queen.
     * @param y The y-coordinate (column) of the queen.
     */
    public Queen(int x, int y) {
        this.x = x;
        this.y = y;
        this.isSuperQueen = false;
    }

    /**
     * Default constructor that initializes the queen at position (0, 0) as a
     * regular queen.
     */
    public Queen() {
        this.x = 0;
        this.y = 0;
        this.isSuperQueen = false;
    }

    // Methods

    /**
     * Returns the x-coordinate (row) of the queen.
     *
     * @return int The x-coordinate of the queen.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate (row) of the queen.
     *
     * @param newX The new x-coordinate of the queen.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Returns the y-coordinate (column) of the queen.
     *
     * @return int The y-coordinate of the queen.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate (column) of the queen.
     *
     * @param newY The new y-coordinate of the queen.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * Returns whether the queen is a "super queen."
     *
     * @return boolean True if the queen is a super queen, false otherwise.
     */
    public boolean isSuperQueen() {
        return isSuperQueen;
    }

    /**
     * Sets the status of the queen as a "super queen."
     *
     * @param newSuperQueen True to mark the queen as a super queen, false to set as
     *                      regular queen.
     */
    public void setSuperQueen(boolean newSuperQueen) {
        this.isSuperQueen = newSuperQueen;
    }

    /**
     * Returns a string representation of the queen, including position and type.
     *
     * @return String A description of the queen and its status.
     */
    @Override
    public String toString() {
        return "Queen is at (" + x + ", " + y + ") "
                + (isSuperQueen ? "and is a Super Queen." : "and is a regular Queen.");
    }
}
