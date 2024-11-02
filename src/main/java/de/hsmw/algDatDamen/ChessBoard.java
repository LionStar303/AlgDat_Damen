package de.hsmw.algDatDamen;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

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
    private Location originCorner;
    private Vector direction;

    public boolean isConsole() {
        return console;
    }

    public Location getOriginCorner() {
        return originCorner;
    }

    public void setOriginCorner(Location originCorner) {
        this.originCorner = originCorner;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    /**
     * Default constructor that initializes an empty chessboard.
     */
    public ChessBoard() {
        this.size = 0;
        this.queens = new ArrayList<>();
        this.originCorner = new Location(null, 0, 0, 0);
        this.direction = new Vector(1, 0, 0);
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
        this.originCorner = null;
        this.direction = null;
    }

    // Hier Doku einfügen
    public ChessBoard(Location originCorner, int size, Player player) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = false;
        this.originCorner = originCorner;
        this.direction = this.getBoardDirection(player); // get player direction
        updateOriginCorner();
        spawnCB((originCorner.getBlock().getType() == Material.WHITE_CONCRETE));
    }

    /**
     * Constructor to create a chessboard of a specific size and Console messages.
     *
     * @param size    The size of the chessboard (size x size).
     * @param console console messages for debugging
     */
    public ChessBoard(int size, boolean console) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = console;
        this.originCorner = null;

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
    public boolean addTestedQueen(int x, int y) {
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
     * Adds a new Queen to the ArrayList queens
     *
     * @param q The queen to adding the ArrayList queens
     */
    public void addQueen(Queen q){
        queens.add(q);
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
     * Checks if placing a queen at the specified coordinates (x, y) would cause a collision
     * with any existing queens on the chessboard. A collision occurs if the new queen
     * would be in the same row, column, or diagonal as any already-placed queen.
     *
     * @param x The x-coordinate (column) where the new queen is to be placed.
     * @param y The y-coordinate (row) where the new queen is to be placed.
     * @return true if a collision is detected with any existing queens;
     *         false if there are no conflicts.
     */
    public boolean collision(int x, int y) {
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
                boolean added = addTestedQueen(x, y);
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
    public boolean playBacktrack() {
        if (console) {
            System.out.println("Start Backtracking Algorithm");
        }

        int row = 0;

        while (numberOfQueens() != size) {
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

            //queens.remove(numberOfQueens() - 1);
            // --- Achtung Hier Minecraft Befehl
        removeQueen();

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


    // ------------------------------- Minecraft Methoden ----------------------------------------
    /**
     * Spawns a chessboard pattern of alternating white and gray blocks on the ground, starting from `originCorner`
     * The board's size is determined by the `size` variable
     * The first corner block is either white or gray
     *
     * @param white If true, the left corner of the chessboard will be white; otherwise, it will be gray.
     */
    public void spawnCB(boolean white) {
        // Iterate over each x and z coordinate within the specified board size.
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {

                // determine the block color at the current relative coordinate
                boolean isWhite = ((x + z) % 2 == 0) == white;
                Material material = isWhite ? Material.WHITE_CONCRETE : Material.GRAY_CONCRETE;

                // calculate global coordinates of the block to place
                Block currentBlock = originCorner.getBlock().getWorld().getBlockAt(
                        originCorner.getBlockX() + x,
                        originCorner.getBlockY(),
                        originCorner.getBlockZ() + z);

                // Set the type of the block to the determined material, either white or gray concrete.
                currentBlock.setType(material);
            }
        }
    }

    /**
     * Spawns a queen on the chessboard at the specified position.
     *
     * @param q The Queen object that contains the relative coordinates where the queen should be placed.
     * @return boolean True if the queen was successfully spawned, or false if a queen already occupies that spot.
     */
    public boolean spawnQueen(Queen q) {
        // Retrieve the queen's (x, y) coordinates from the Queen object
        int x = q.getX();
        int y = q.getY();

        // Calculate the queen's global coordinates, offset slightly to center within the block
        Location queenLocation = originCorner.getBlock().getLocation().add(x + 0.5, 1, y + 0.5);

        // Check if an armor stand already occupies the target block (indicating a queen is already present)
        if (queenLocation.getBlock().getType() == Material.ARMOR_STAND) {
            return false; // Exit early if another queen is already in this position
        }

        // Spawn an Armor Stand to represent the queen at the calculated location
        ArmorStand armorStand = queenLocation.getWorld().spawn(queenLocation, ArmorStand.class);

        // Configure the Armor Stand's properties to resemble a queen piece
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.setCustomName(q.getX() + ". Queen");
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setSmall(true); // Smaller size to fit the chess piece style

        // Equip the Armor Stand with items to give the appearance of a chess queen
        EntityEquipment equipment = armorStand.getEquipment();
        equipment.setHelmet(new ItemStack(Material.GOLDEN_HELMET)); // Crown-like helmet
        equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE)); // Body armor
        equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS)); // Leg armor
        equipment.setBoots(new ItemStack(Material.IRON_BOOTS)); // Boot armor

        // Adjust arm poses to provide a distinctive look for the queen piece
        armorStand.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(-10)));
        armorStand.setLeftArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(10)));

        return true; // Indicates whether the queen was successfully spawned
    }

    /**
     * This Methode Spawn all Queens in the ArrayList<Queen> queens
     *
     */
    public void spawnAllQueens(){
        for(Queen q : this.queens){
            spawnQueen(q);
        }
    }

    public boolean isPartOfBoard(Location location) {
        if (originCorner == null) {
            return false;
        }

        // Calculate boundaries based on origin location and board size
        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();
        int maxX = minX + size - 1;
        int maxZ = minZ + size - 1;

        // Check if the given location is within the boundaries
        int x = location.getBlockX();
        int z = location.getBlockZ();

        return x >= minX && x <= maxX && z >= minZ && z <= maxZ && location.getWorld().equals(originCorner.getWorld());
    }

    public boolean addQueen(Location l){
        if(!isPartOfBoard(l)){
            return false;
        }

        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();

        int x = l.getBlockX();
        int z = l.getBlockZ();

        Queen q = new Queen(x - minX, z - minZ);
        addQueen(q);
        spawnQueen(q);

        return true;
    }

    public boolean addTestedQueen(Location l){
        if(!isPartOfBoard(l)){
            return false;
        }

        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();

        int x = l.getBlockX();
        int z = l.getBlockZ();

        Queen q = new Queen(x - minX, z - minZ);
        if(collision(q)){
            return false;
        }
        addQueen(q);
        spawnQueen(q);

        return true;
    }

    public Queen getQueenAt(Location l){
        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();

        for(Queen q : this.queens){
            System.out.println(q.getX() + " == " +(l.getBlockX()-minX)+" && "+q.getY() + " == " + (l.getBlockZ()-minZ) );
            if((q.getX() == (l.getBlockX()-minX)) && (q.getY() == (l.getBlockZ()-minZ))){
                return q;
            }
        }
        return null;
    }

    // Method to remove the most recently added queen
    public void removeQueen() {
        // Check if there are any queens to remove
        if (!queens.isEmpty()) {
            Queen lastQueen = queens.remove(queens.size() - 1); // Remove last queen in list
            removeQueenFromBoard(lastQueen); // Remove queen from Minecraft board
        }
    }

    // Method to remove a specific queen
    public void removeQueen(Queen q) {
        if (queens.remove(q)) { // If queen is found and removed from list
            removeQueenFromBoard(q); // Remove queen from Minecraft board
        }
    }

    /**
     * Helper method to visually remove a queen (armor stand) from the Minecraft board.
     *
     * @param queen The queen to remove from the board.
     */
    private void removeQueenFromBoard(Queen queen) {
        if (queen != null) {
            Location queenLocation = getLocationofQueen(queen);
            if (queenLocation != null) {
                // Iterate through entities at the queen's location
                for (Entity entity : queenLocation.getWorld().getEntities()) {
                    // Check if the entity is an armor stand and at the same location
                    if (entity instanceof ArmorStand && entity.getLocation().equals(queenLocation)) {
                        entity.remove(); // Remove the armor stand
                        break; // Exit the loop once the queen is found and removed
                    }
                }
            }
        }
    }

    /**
     * Get the location of the queen on the board.
     *
     * @param q The queen whose location is to be determined.
     * @return The Location object representing the queen's position.
     */
    private Location getLocationofQueen(Queen q) {
        double x = q.getX() + originCorner.getBlockX()+0.5;
        double z = q.getY() + originCorner.getBlockZ()+0.5;

        return new Location(originCorner.getWorld(),  x, originCorner.getY() + 1,z);
    }

    public void spawnCollisionCarpets() {
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                if(collision(x, y)){
                    Location location = new Location(originCorner.getWorld(), originCorner.getX()+x, originCorner.getY()+1, originCorner.getBlockZ()+y); // Y-coordinate can be adjusted as needed
                    Block block = location.getBlock();
                    block.setType(Material.RED_CARPET);
                }
            }
        }
    }

    /**
     * Determines the cardinal direction of the player based on their location.
     * 
     * @param player
     * @return
     */
    private Vector getBoardDirection(Player player) {
        // Get the player's direction as a 2D vector
        Vector playerDirection = player.getLocation().getDirection();
        playerDirection.setY(0); // Ignore vertical component
        playerDirection = playerDirection.normalize();

        double x = playerDirection.getX();
        double z = playerDirection.getZ();

        if (x > 0 && z > 0) {
            return new Vector(1, 0, 1); // East
        } else if (x < 0 && z > 0) {
            return new Vector(-1, 0, 1); // South
        } else if (x < 0 && z < 0) {
            return new Vector(-1, 0, -1); // West
        } else {
            return new Vector(1, 0, -1); // North
        }
    }

    private void updateOriginCorner() {
        // Determine direction modifiers based on boardDirection
        int xMod = (int) direction.getX(); // Will be either -1 or 1 for East/West orientation
        int zMod = (int) direction.getZ(); // Will be either -1 or 1 for North/South orientation

        // Current origin corner coordinates
        int x = originCorner.getBlockX();
        int z = originCorner.getBlockZ();

        // Calculate new origin corner based on the player's direction and board size
        if (xMod > 0 && zMod > 0) {
            // Facing East and South: Set origin at the bottom-left corner
            originCorner = new Location(originCorner.getWorld(), x, originCorner.getBlockY(), z);
        } else if (xMod < 0 && zMod > 0) {
            // Facing West and South: Set origin at the bottom-left corner and shift to the
            // right
            originCorner = new Location(originCorner.getWorld(), x - (size - 1), originCorner.getBlockY(), z);
        } else if (xMod < 0 && zMod < 0) {
            // Facing West and North: Set origin at the bottom-left corner and shift to both
            // right and up
            originCorner = new Location(originCorner.getWorld(), x - (size - 1), originCorner.getBlockY(), z - (size - 1));
        } else if (xMod > 0 && zMod < 0) {
            // Facing East and North: Set origin at the bottom-left corner and shift up
            originCorner = new Location(originCorner.getWorld(), x, originCorner.getBlockY(), z - (size - 1));
        }
    }

}
