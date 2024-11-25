/**
 * Represents a generic chess piece with a position on a board.
 *
 * This class provides the basic functionality and attributes for a chess piece,
 * including its position and methods to check collisions, equality, and to retrieve
 * a representative letter.
 *
 * @version 1.0, November 24, 2024
 * @author Moritz Kockert
 */
public class Piece {

  // Attributes for the position of the piece
  protected int x; // The row coordinate of the piece on the board
  protected int y; // The column coordinate of the piece on the board

  /**
   * Constructor to create a chess piece at a specified position.
   *
   * @param x The x-coordinate (row) of the piece's position.
   * @param y The y-coordinate (column) of the piece's position.
   */
  public Piece(int x, int y) {
      this.x = x;
      this.y = y;
  }

  /**
   * Default constructor that initializes the piece at the default position (0, 0).
   */
  public Piece() {
      this.x = 0;
      this.y = 0;
  }

  /**
   * Retrieves the x-coordinate (row) of the piece's position.
   *
   * @return The current x-coordinate of the piece.
   */
  public int getX() {
      return x;
  }

  /**
   * Updates the x-coordinate (row) of the piece's position.
   *
   * @param xNeu The new x-coordinate for the piece.
   */
  public void setX(int xNeu) {
      x = xNeu;
  }

  /**
   * Retrieves the y-coordinate (column) of the piece's position.
   *
   * @return The current y-coordinate of the piece.
   */
  public int getY() {
      return y;
  }

  /**
   * Updates the y-coordinate (column) of the piece's position.
   *
   * @param yNeu The new y-coordinate for the piece.
   */
  public void setY(int yNeu) {
      y = yNeu;
  }

  /**
   * Checks for a collision with another position on the board.
   *
   * A collision occurs if either the x-coordinate or the y-coordinate
   * of the given position matches this piece's position.
   *
   * @param checkX The x-coordinate of the position to check.
   * @param checkY The y-coordinate of the position to check.
   * @return True if there is a collision, false otherwise.
   */
  public boolean checkCollision(int checkX, int checkY) {
      if (getX() == checkX && getY() == checkY) {
          return true; // Collision detected
      }
      return false; // No collision found
  }

  /**
   * Compares this piece's position to another piece's position.
   *
   * @param p Another Piece object to compare.
   * @return True if both pieces have the same x and y coordinates, false otherwise.
   */
  public boolean equals(Piece p) {
      if ((p.getX() == getX()) && (p.getY() == getY())) {
          return true; // Positions are equal
      }
      return false; // Positions are not equal
  }

  /**
   * Retrieves a character representing the piece type.
   *
   * This method is intended to be overridden in subclasses for specific piece types.
   *
   * @return 'P' by default, representing a generic piece.
   */
  public char getLetter() {
      return 'P';
  }
  
  @Override
  public Piece clone() {
    Piece newP = new Piece(this.x , this.y);
    return newP;
  }
 
} // End of the Piece class
