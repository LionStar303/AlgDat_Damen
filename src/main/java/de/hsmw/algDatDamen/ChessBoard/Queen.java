package de.hsmw.algDatDamen.ChessBoard;

/**
 * Represents a Queen on the chessboard.
 * A Queen can move any number of squares along a row, column, or diagonal.
 *
 * This class defines its position and provides collision-detection logic 
 * to validate the Queen's placement on the board.
 *
 * @version 1.1, 24.10.2024
 * @author Moritz Kockert
 */
public class Queen extends Piece {

    /**
     * Constructor to create a Queen at the specified position.
     *
     * @param x The x-coordinate (row) of the Queen's position.
     * @param y The y-coordinate (column) of the Queen's position.
     */
    public Queen(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Provides a string representation of the Queen, including its current position.
     *
     * @return A string describing the Queen's position on the board.
     */
    @Override
    public String toString() {
        return "Queen is at (" + getX() + ", " + getY() + ")";
    }

    /**
     * Compares the position of this Queen with another Queen.
     *
     * @param q Another Queen object to compare.
     * @return True if both Queens occupy the same position, false otherwise.
     */
    public boolean equals(Queen q) {
        return q != null && q.getX() == getX() && q.getY() == getY();
    }

    /**
     * Checks if placing a Queen at the specified coordinates (checkX, checkY)
     * would cause a collision with this Queen.
     *
     * A collision occurs if the target position shares the same row, column,
     * or diagonal as this Queen.
     *
     * @param checkX The x-coordinate of the position to check.
     * @param checkY The y-coordinate of the position to check.
     * @return True if a collision is detected, false otherwise.
     */
    @Override
    public boolean checkCollision(int checkX, int checkY) {
        // Check for conflicts in row, column, or diagonal
        return getX() == checkX || getY() == checkY ||
               Math.abs(getX() - checkX) == Math.abs(getY() - checkY);
    }

    /**
     * Retrieves the character representation of the Queen.
     *
     * @return 'Q', representing the Queen piece.
     */
    @Override
    public char getLetter() {
        return 'Q';
    }
  
    @Override
  public Queen clone() {
    Queen newP = new Queen(this.x , this.y);
    return newP;
  }
  
  
}
