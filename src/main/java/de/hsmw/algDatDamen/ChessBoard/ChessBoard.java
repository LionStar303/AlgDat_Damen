// package de.hsmw.algDatDamen.ChessBoard;

import java.util.ArrayList;

/**
 * Represents a chessboard for solving the Queen's problem.
 * Allows placing pieces, checking for conflicts, and solving using
 * a backtracking algorithm.
 *
 * @version 1.2, 24.10.2024
 */
public class ChessBoard {

    // ----------- Attributes -----------
    protected ArrayList<Piece> pieces; // List of pieces placed on the board
    protected int size; // Size of the chessboard (n x n)
    protected boolean console; // Controls console messages for debugging
    protected int stateX; // Tracks the current X-axis state
    protected int stateY; // Tracks the current Y-axis state
  

    // ----------- Constructors -----------
    public ChessBoard(int boardSize) {
        this.size = boardSize;
        this.pieces = new ArrayList<Piece>();
        this.console = true;
        this.stateX = 0;
        this.stateY = 0;
    }

    public ChessBoard() {
        this(4); // Default to a 1x1 board
    }

    // ----------- Getters and Setters -----------
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isConsoleEnabled() {
        return console;
    }

    public void setConsoleEnabled(boolean consoleEnabled) {
        this.console = consoleEnabled;
    }

    public int getStateX() {
        return stateX;
    }

    public void setStateX(int stateX) {
        this.stateX = stateX;
    }

    public int getStateY() {
        return stateY;
    }

    public void setStateY(int stateY) {
        this.stateY = stateY;
    }

    // ----------- Functional Methods -----------
    // --- Adding/Removing Pieces ---
    public boolean addPiece(Piece pnew) {
      if (pnew.getX() >= size || pnew.getY() >= size) {
           return false; 
        } // end of if
    
        for (Piece p : pieces) {
            if (p.getX() == pnew.getX() && p.getY() == pnew.getY()) {
                return false; // Duplicate position
            }
        }
        pieces.add(pnew);
        return true;
    }
  
   public boolean addTestedPiece(Piece pnew) {
        if (pnew.getX() >= size || pnew.getY() >= size) {
           System.out.println("Not on Field "+size+"x"+size+" on X: "+pnew.getX()+"  Y: "+ pnew.getY());
           printBoard(true);
           return false; 
        } // end of if
        
        for (Piece p : pieces) {
            if (p.checkCollision(pnew.getX(), pnew.getY())) {
                if (console) {
                  System.out.println("Collision on X: "+pnew.getX()+"  Y: "+ pnew.getY());
                  printBoard(true);
                } // end of if                              
                return false; // Collision
            }
        }
        pieces.add(pnew);
        return true;
    }

    public void removePiece(Piece p) {
        pieces.remove(p);
    }
  
    public boolean removePiece(int x, int y) {
        for(Piece p : pieces){
          if (p.getX() == x && p.getY() == y) {
             pieces.remove(p);
             return true;
          } 
        }
        return false;
    }

    public void removeAllPieces() {
        pieces.clear();
    }

    public void removeLastPiece() {
        if (!pieces.isEmpty()) {
            pieces.remove(pieces.size() - 1);
        }
    }

    public void removeLastQueen() {
        for (int i = pieces.size() - 1; i >= 0; i--) {
            if (pieces.get(i).getLetter() == 'Q') {
                pieces.remove(i);
                break;
            }
        }
    }

    public void removeLastKnight() {
        for (int i = pieces.size() - 1; i >= 0; i--) {
            if (pieces.get(i).getLetter() == 'K') {
                pieces.remove(i);
                break;
            }
        }
    }

    public void removeLastSuperQ() {
        for (int i = pieces.size() - 1; i >= 0; i--) {
            if (pieces.get(i).getLetter() == 'S') {
                pieces.remove(i);
                break;
            }
        }
    }

    // --- Sorting Pieces ---
    public void sortPiecesByX() {
        pieces.sort((p1, p2) -> Integer.compare(p1.getX(), p2.getX()));
    }

    public void sortPiecesByY() {
        pieces.sort((p1, p2) -> Integer.compare(p1.getY(), p2.getY()));
    }

    // --- Collision Checks ---
    public boolean checkCollision(Piece p) {
        for (Piece existingPiece : pieces) {
            if (existingPiece.checkCollision(p.getX(), p.getY())) {
                return true;
            }
        }
        return false; // No collision
    }

    public boolean checkCollision(int x, int y) {
        for (Piece existingPiece : pieces) {
            if (existingPiece.checkCollision(x, y)) {
                return true;
            }
        }
        return false; // No collision
    }

    // ----------- Debugging and Visualization -----------
    public void printBoard(boolean collision) {
        System.out.println("------------------------------------------------------------");
        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
                if (collision && checkCollision(i, j)) {
                board[i][j] = '*';
                } // end of if
            }
        }

        for (Piece p : pieces) {
            board[p.getX()][p.getY()] = p.getLetter();
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void clearBoard() {
        pieces.clear();
        if (console) {
            System.out.println("Board cleared.");
        }
    }
  

    // ----------- Backtracking Algorithm -----------
    // Funktion ist noch Falsch!!! <-- Jede Figur hat mit sich selber eine Collision
    public boolean isSolved() {
    if (console) {
        System.out.println("Checking if the chessboard is solved...");
    }
    
    // Check if the number of pieces matches the required board size
    if (pieces.size() == size) {
        if (console) {
            System.out.println("The number of pieces matches the board size: " + pieces.size());
        }
        
        // Check for collisions between pieces
        for (Piece p : pieces) {
            if (checkCollision(p)) {
                if (console) {
                    System.out.println("Collision detected with piece: " + p);
                }
                return false;
            }
        }
        
        if (console) {
            System.out.println("No collisions found. The chessboard is solved.");
        }
        return true;
    }
    
    if (console) {
        System.out.println("The number of pieces does NOT match the required board size. Current count: " + pieces.size());
    }
    return false;
    }

  
      /**
     * Solves the Queen's problem using a backtracking algorithm.
     * It tries to place all queens on the board without any conflicts.
     *
     * @return boolean True if the algorithm successfully places all queens, false
     *         otherwise.
     */
    public boolean playBacktrack(Piece p) {
        clearBoard();
        int row = 0;

        if (console) {
            System.out.println("Start Backtracking Algorithm");
            printBoard(true);
        }

        while (pieces.size() != size) {
            for (int i = 0; i < size; i++) { 
            p.setX(row);
            p.setY(i);
            if (console) {
            System.out.println("Check -> row: " + row + " y: " +i);
            }
                if (addTestedPiece(p.clone())) {
                    if (console) {
                        System.out.println("Step -> row: " + row);
                        printBoard(true);
                    }
                    row++;
                    break;
                } else if (i == size - 1) {
                    row = backStep(p,row) + 1;
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
    public int backStep(Piece p, int row) {
    row--;
    int oldY = pieces.get(pieces.size() - 1).getY();
    removeLastPiece();

    if (console) {
       System.out.println("Start Backstep -> row: " + row);
    }
    int newY = oldY + 1;
    
    p.setX(row);
    p.setY(newY);
    
    while (!addTestedPiece(p.clone())) {
            newY++;
            if (console) {
                System.out.println("Back-Place -> row: " + row + "  Y = " + newY);
            }

            if (newY >= size) {
                if (console) {
                    printBoard(true);
                }
                backStep(p, row);
                newY = 0;
            }
          p.setX(row);
          p.setY(newY);
        }

        if (console) {
            System.out.println("End Backstep -> row: " + row + "  Y = " + newY);
            printBoard(true);
        }
        return row;
    }
  
    // Important in fron of this ?
    // sortQueensByX();
    // verfyQueens();
    // stateX = numberOfQueens();
    public boolean stepBacktrack(Piece p){
        if ((pieces.size() == size)) {
           if (isSolved()) {
             if (console) {
               System.out.println("Gelöst!");
               printBoard(true);
             } // end of if
             return true;
           } else {
             System.out.println("FEHLER!!!!");
             return true;
           } // end of if-else
      } // end of if

        if (stateY == size - 1 && stateX == pieces.size() - 1) {
            stateY = 0;
            stateX++;
        }
    
    p.setX(stateX);
    p.setY(stateY);
        if (addTestedPiece(p.clone())) {
            stateX++;
            stateY = -1;
            if (console) {
                System.out.println("Step -> row: " + stateX);
                printBoard(true);
            }
        } else if (stateY >= size) {
            stateX--;
            stateY = pieces.get(pieces.size()-1).getY();
            removeLastPiece();
        }
        stateY++;
        return false;
    }

    public boolean playBacktrackToRow(Piece p,int x) {
        if (x > this.size) {
            return false;
        } // end of if
        while (getStateX() != x) {
            stepBacktrack(p);
        } // end of while
        return true;
    }

    public boolean solveBacktrackToRow(Piece p, int x){
        if (x > this.size) {
            return false;
        } // end of if
        playBacktrack(p);
        sortPiecesByX();
        for(int i = 0; i < x; i++){
            pieces.remove(pieces.size() - 1);
        }
        return true;
    }

    public boolean playBacktrackToNextPiece(Piece p) {
        int numQ = pieces.size();
        while (numQ == pieces.size()) {
            stepBacktrack(p);
        } // end of while
        return isSolved();
    }

    public void verfyPieces() {
        sortPiecesByX();
        for (int i = pieces.size() - 1; i > 0; i--) {

            if (console) {
                System.out.println("Check Collision = " + checkCollision(pieces.get(i)));
                System.out.println("(" + pieces.get(i).getX() + " == " + i + ")");
            } // end of if

            // Check for Collision
            if (!(checkCollision(pieces.get(i))) &&
            // Check for right line in X direction
                    (pieces.get(i).getX() == i)) {

            } else {
                pieces.remove(pieces.get(i));
            } // end of if-else

        }

        setStateX(pieces.size());
    }

    public void rotatePieces(int rotations) {
        // Reduce rotations to within [0-3] (4 rotations would result in the same
        // original board)
        rotations = rotations % 4;

        for (int i = 0; i < rotations; i++) {
            for (Piece p : pieces) {
                // Apply 90-degree rotation on each queen's position
                int originalX = p.getX();
                int originalY = p.getY();
                p.setX(originalY);
                p.setY(size - 1 - originalX);
            }
        }
    }

    public void bongoSolve(Piece p){
        verfyPieces();
        while(bongoStep(p) == false){
            if(console){
                printBoard(true);
            }
            if (stateX == (size)) {
                removeAllPieces();
                stateX = 0;
            }
        }

    }

    public boolean bongoStep(Piece p){
        p.setX(stateX);
        p.setY( (int) ( Math.random() * (size) ));
        addPiece(p.clone());
        stateX++;
        return isSolved();
    }

}
