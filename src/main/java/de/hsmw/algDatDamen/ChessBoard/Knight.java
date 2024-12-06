//package de.hsmw.algDatDamen.ChessBoard;

/**
 * Represents a Knight chess piece with its unique movement and attributes.
 *
 * This class extends the generic Piece class and implements the specific
 * behaviors
 * and characteristics of a Knight in chess. Knights can "jump" to specific
 * positions following an L-shaped pattern.
 *
 * @version 1.0, November 24, 2024
 * @author Moritz Kockert
 */
public class Knight extends Piece {

    /**
     * Constructor to create a Knight at a specified position.
     *
     * @param x The x-coordinate (row) of the Knight's position.
     * @param y The y-coordinate (column) of the Knight's position.
     */
    public Knight(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor to create a Knight at 0 0
     */
    public Knight() {
        this.x = 0;
        this.y = 0;
    }

    // Methods specific to the Knight class

    /**
     * Provides a string representation of the Knight, including its current
     * position.
     *
     * @return A string indicating the Knight's position on the board.
     */
    @Override
    public String toString() {
        return "Knight is at (" + getX() + ", " + getY() + ")";
    }

    /**
     * Compares this Knight's position with another Knight's position.
     *
     * @param q Another Knight object to compare against.
     * @return True if both Knights occupy the same position, false otherwise.
     */
    public boolean equals(Knight q) {
        if ((q.getX() == getX()) && (q.getY() == getY())) {
            return true; // Knights are in the same position
        }
        return false; // Knights are in different positions
    }

    /**
     * Checks if placing a Knight at the specified coordinates (checkX, checkY)
     * would cause a collision with this Knight.
     *
     * A collision occurs if the target position is either in the same row, column,
     * or if it falls within the possible moves of a Knight (an L-shape).
     *
     * @param checkX The x-coordinate of the position to check.
     * @param checkY The y-coordinate of the position to check.
     * @return True if a collision is detected, false otherwise.
     */
    @Override
    public boolean checkCollision(int checkX, int checkY) {
        // Check for row or column conflicts
        if (getX() == checkX && getY() == checkY) {
            return true; // Collision detected
        }

        // Check for Knight-specific movement pattern conflicts
        if ((checkX == (getX() + 1) && checkY == (getY() + 2)) ||
                (checkX == (getX() + 2) && checkY == (getY() + 1)) ||
                (checkX == (getX() - 1) && checkY == (getY() - 2)) ||
                (checkX == (getX() - 2) && checkY == (getY() - 1)) ||
                (checkX == (getX() + 2) && checkY == (getY() - 1)) ||
                (checkX == (getX() + 1) && checkY == (getY() - 2)) ||
                (checkX == (getX() - 2) && checkY == (getY() + 1)) ||
                (checkX == (getX() - 1) && checkY == (getY() + 2))) {
            return true; // Collision detected
        }

        return false; // No collision detected
    }

    /**
     * Retrieves a character representing the Knight piece.
     *
     * @return 'K', the standard letter representation for a Knight in chess.
     */
    @Override
    public char getLetter() {
        return 'K';
    }

    @Override
    public Knight clone() {
        Knight newP = new Knight(this.x, this.y);
        return newP;
    }
  
    public Knight clone(int x, int y) {
        Knight newP = new Knight(x, y);
        return newP;
    }

    public String getName(){
        return "Knight";
    }
} // End of Knight class
