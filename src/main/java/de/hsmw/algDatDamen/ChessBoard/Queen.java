//package de.hsmw.algDatDamen.ChessBoard;

/**
 * This class represents a Queen on the chessboard.
 * It stores the position of the queen and whether it is a "super queen."
 *
 * @version 1.1, 24.10.2024
 */
public class Queen extends Piece {
   /**
     * Returns a string representation of the queen, including position and type.
     *
     * @return String A description of the queen and its status.
     */
 @Override
 public String toString() {
        return "Queen is at (" + getX() + ", " + getY() + ") ";
 }
  
  public boolean equals(Queen q) {
        if ((q.getX() == getX()) && (q.getY() == getY())) {
            return true;
        } // end of if
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
    @Override
    public boolean checkCollision(int checkX, int checkY) {
            // Check for row, column, or diagonal conflicts
            if (getX() == checkX || getY() == checkY ||
                    Math.abs(getX() - checkX) == Math.abs(getY() - checkY)) {
                return true; // Collision detected
            }
      return false; // No collision found
    }
  
  @Override
  public char getLetter(){
    return 'Q';
    }
}
