/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 24.11.2024
 * @author 
 */

public class Piece {
  
  // Anfang Attribute
  protected int x;
  protected int y;
  // Ende Attribute
  
  /**
   * Constructor to create a regular queen at the specified position.
   *
   * @param x The x-coordinate (row) of the queen.
   * @param y The y-coordinate (column) of the queen.
   */
  public Piece(int x, int y) {
        this.x = x;
        this.y = y;
  }

  /**
   * Default constructor that initializes the queen at position (0, 0) as a
   * regular queen.
   */
  public Piece() {
        this.x = 0;
        this.y = 0;
  }

  // Anfang Methoden
  public int getX() {
    return x;
  }

  public void setX(int xNeu) {
    x = xNeu;
  }

  public int getY() {
    return y;
  }

  public void setY(int yNeu) {
    y = yNeu;
  }
  
  public boolean checkCollision(int checkX, int checkY) {
            // Check for row, column, or diagonal conflicts
            if (getX() == checkX || getY() == checkY) {
                return true; // Collision detected
            }
      return false; // No collision found
  }
  
  public boolean equals(Piece p) {
    if ((p.getX() == getX()) && (p.getY() == getY())) {
       return true;
    } // end of if
    return false;
  }

  
  public char getLetter(){
    return 'P';
    }
  // Ende Methoden
} // end of Pieces
