package de.hsmw.algDatDamen.ChessBoard;

/**
 * Represents a SuperQueen chess piece with combined movement capabilities
 * of a Queen and a Knight.
 *
 * A SuperQueen can move like a standard Queen (row, column, and diagonal) and
 * also like a Knight (L-shaped jumps). This class extends the generic `Piece`
 * class and integrates the behaviors of both Queen and Knight.
 *
 * @version 1.0, November 24, 2024
 * @author Moritz Kockert
 */
public class Superqueen extends Piece {

    /**
     * Constructor to create a SuperQueen at the specified position.
     *
     * @param x The x-coordinate (row) of the SuperQueen's position.
     * @param y The y-coordinate (column) of the SuperQueen's position.
     */
    public Superqueen(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor to create a Superqueen at 0 0
     */
    public Superqueen() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Provides a string representation of the SuperQueen, including its current position.
     *
     * @return A string indicating the SuperQueen's position on the board.
     */
    @Override
    public String toString() {
        return "SuperQueen is at (" + getX() + ", " + getY() + ")";
    }

    /**
     * Compares this SuperQueen's position with another SuperQueen's position.
     *
     * @param q Another SuperQueen object to compare against.
     * @return True if both SuperQueens occupy the same position, false otherwise.
     */
    public boolean equals(Superqueen q) {
        if ((q.getX() == getX()) && (q.getY() == getY())) {
            return true; // SuperQueens are in the same position
        }
        return false; // SuperQueens are in different positions
    }

    /**
     * Checks if placing a SuperQueen at the specified coordinates (checkX, checkY)
     * would cause a collision with this SuperQueen.
     *
     * A collision occurs if the target position is either in the same row, column,
     * diagonal, or falls within the possible moves of a Knight.
     *
     * @param checkX The x-coordinate of the position to check.
     * @param checkY The y-coordinate of the position to check.
     * @return True if a collision is detected, false otherwise.
     */
    @Override
    public boolean checkCollision(int checkX, int checkY) {
        // Check for row, column, or diagonal conflicts using Queen's logic
        Queen q = new Queen(getX(), getY());
    
        // Check for Knight-specific conflicts
        Knight k = new Knight(getX(), getY());

        // A collision exists if either the Queen or the Knight movement rules are violated
        return q.checkCollision(checkX, checkY) || k.checkCollision(checkX, checkY);
    }

    /**
     * Retrieves a character representing the SuperQueen piece.
     *
     * @return 'S', the standard letter representation for a SuperQueen.
     */
    @Override
    public char getLetter() {
        return 'S';
    }
  
  @Override
  public Superqueen clone() {
    Superqueen newP = new Superqueen(this.x , this.y);
    return newP;
  }
} // End of Superqueen class
