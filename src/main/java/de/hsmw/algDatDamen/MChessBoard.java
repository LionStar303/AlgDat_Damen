package de.hsmw.algDatDamen;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class MChessBoard extends ChessBoard {

    private Location originCorner; // Starting corner for the chessboard
    private boolean isOriginCornerWhite; // Indicates if the origin corner is white
    private boolean collisionCarpets; // Checks if carpets cause collision issues
    private Material whiteFieldMaterial; // Material used for white fields on the board
    private Material blackFieldMaterial; // Material used for black fields on the board

    /**
     * Constructor to create a chessboard with specific parameters.
     *
     * @param originCorner        Starting location for the chessboard.
     * @param size                The size of the chessboard.
     * @param player              The player for determining the board direction.
     * @param whiteFieldMaterial  Material used for the white fields.
     * @param blackFieldMaterial  Material used for the black fields.
     */
    public MChessBoard(Location originCorner, int size, Player player, Material whiteFieldMaterial, Material blackFieldMaterial) {
        this.size = size;
        this.queens = new ArrayList<>();
        this.console = true;
        this.originCorner = originCorner;
        updateOriginCorner(this.getBoardDirection(player));
        this.isOriginCornerWhite = (originCorner.getBlock().getType() == Material.WHITE_CONCRETE);
        this.whiteFieldMaterial = whiteFieldMaterial;
        this.blackFieldMaterial = blackFieldMaterial;
        this.stateX = 0;
        this.stateY = 0;
        spawnChessBoard();
    }

    /**
     * Constructor to create a chessboard with specific parameters and default field materials.
     *
     * @param originCorner Starting location for the chessboard.
     * @param size         The size of the chessboard.
     * @param player       The player for determining the board direction.
     */
    public MChessBoard(Location originCorner, int size, Player player) {
        this(originCorner, size, player, Material.WHITE_CONCRETE, Material.GRAY_CONCRETE); // Default materials: wool
    }

    // Getter and Setter Methods

    /**
     * Retrieves the starting corner location of the chessboard.
     *
     * @return The starting location of the chessboard.
     */
    public Location getOriginCorner() {
        return originCorner;
    }

    /**
     * Sets the starting corner location of the chessboard.
     *
     * @param originCorner The new starting location for the chessboard.
     */
    public void setOriginCorner(Location originCorner) {
        this.originCorner = originCorner;
    }
      
    /**
     * Retrieves whether the origin corner of the chessboard is white.
     *
     * @return true if the origin corner is white, false otherwise.
     */
    public boolean isOriginCornerWhite() {
        return isOriginCornerWhite;
    }

    /**
     * Sets whether the origin corner of the chessboard is white.
     *
     * @param isOriginCornerWhite true if the origin corner should be white, false otherwise.
     */
    public void setOriginCornerWhite(boolean isOriginCornerWhite) {
        this.isOriginCornerWhite = isOriginCornerWhite;
    }

    /**
     * Retrieves whether carpets cause collision issues on the chessboard.
     *
     * @return true if carpets cause collision issues, false otherwise.
     */
    public boolean isCollisionCarpets() {
        return collisionCarpets;
    }

    /**
     * Sets whether carpets cause collision issues on the chessboard.
     *
     * @param collisionCarpets true if carpets should cause collision issues, false otherwise.
     */
    public void setCollisionCarpets(boolean collisionCarpets) {
        this.collisionCarpets = collisionCarpets;
    }

    /**
     * Retrieves the material used for the white fields on the chessboard.
     *
     * @return The material for white fields.
     */
    public Material getWhiteFieldMaterial() {
        return whiteFieldMaterial;
    }

    /**
     * Sets the material used for the white fields on the chessboard.
     *
     * @param whiteFieldMaterial The material to set for white fields.
     */
    public void setWhiteFieldMaterial(Material whiteFieldMaterial) {
        this.whiteFieldMaterial = whiteFieldMaterial;
    }

    /**
     * Retrieves the material used for the black fields on the chessboard.
     *
     * @return The material for black fields.
     */
    public Material getBlackFieldMaterial() {
        return blackFieldMaterial;
    }

    /**
     * Sets the material used for the black fields on the chessboard.
     *
     * @param blackFieldMaterial The material to set for black fields.
     */
    public void setBlackFieldMaterial(Material blackFieldMaterial) {
        this.blackFieldMaterial = blackFieldMaterial;
    }



    /**
     * Spawns a chessboard on the ground with alternating white and gray blocks.
     *
     * @param white If true, the left corner of the chessboard will be white; otherwise, it will be gray.
     */
    public void spawnChessBoard() {
        // Iterate over each coordinate pair within the board's size
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                // Determine if the current block should be white or gray based on position and initial corner color
                boolean isWhite = ((x + z) % 2 == 0) == this.isOriginCornerWhite;
                Material material = isWhite ? whiteFieldMaterial : blackFieldMaterial;

                // Calculate the exact position of the block within the world
                Block currentBlock = originCorner.getBlock().getWorld().getBlockAt(
                        originCorner.getBlockX() + x,
                        originCorner.getBlockY(),
                        originCorner.getBlockZ() + z);

                // Set the block's type to either white or gray material
                currentBlock.setType(material);
            }
        }
        updateCollisionCarpets();
    }

    /**
     * Spawns a queen on the chessboard at the specified position.
     *
     * @param q The Queen object that contains the (x, y) position where the queen should be placed.
     * @return boolean True if the queen was successfully spawned, or false if a queen already occupies that spot.
     */
    public boolean spawnQueen(Queen q) {
        int x = q.getX();
        int y = q.getY();

        // Calculate the queen's exact position on the chessboard, offset slightly to center within the block
        Location queenLocation = originCorner.getBlock().getLocation().add(x + 0.5, 1, y + 0.5);

        // Check if an armor stand already occupies the target block
        if (queenLocation.getBlock().getType() == Material.ARMOR_STAND) {
            return false; // Return false if another queen is already at this location
        }

        // Spawn an Armor Stand at the calculated location to represent the queen
        ArmorStand armorStand = queenLocation.getWorld().spawn(queenLocation, ArmorStand.class);

        // Configure the Armor Stand properties to resemble a queen piece
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.setCustomName(q.getY() + ". Queen");
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);

        // Equip the Armor Stand with items to give the appearance of a chess queen
        armorStand.setSmall(true); // Smaller size to fit the chess piece style
        armorStand.setHelmet(new ItemStack(Material.IRON_HELMET)); // Crown-like helmet
        armorStand.setChestplate(new ItemStack(Material.IRON_CHESTPLATE)); // Body armor
        armorStand.setLeggings(new ItemStack(Material.IRON_LEGGINGS)); // Leg armor
        armorStand.setBoots(new ItemStack(Material.IRON_BOOTS)); // Boot armor

        // Adjust arm poses for a distinctive queen look
        armorStand.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(-10)));
        armorStand.setLeftArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(10)));

        updateCollisionCarpets();
        return true; // Indicates the queen was successfully spawned
    }

    /**
     * Spawns all queens from the ArrayList<Queen> onto the chessboard.
     */
    public void spawnAllQueens() {
        if (queens == null || queens.size() == 0) {
            return;
        }
        for (Queen queen : queens) {
            spawnQueen(queen); // Spawn each queen
        }
    }


    public void updateCollisionCarpets(){
        if(this.collisionCarpets){
            cleanCollisionCarpets();
            spawnCollisionCarpets();
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
        if(checkCollision(q)){
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

    private void removeALLQueensFromBoard(){
        for(Queen q : queens){
            removeQueenFromBoard(q);
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
        this.collisionCarpets = true;
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                if(checkCollision(x, y)){
                    Location location = new Location(originCorner.getWorld(), originCorner.getX()+x, originCorner.getY()+1, originCorner.getBlockZ()+y); // Y-coordinate can be adjusted as needed
                    Block block = location.getBlock();
                    if((x+y) % 2 == 0){
                        if (isOriginCornerWhite){
                            block.setType(Material.ORANGE_CARPET);
                        }else {
                            block.setType(Material.RED_CARPET);
                        }

                    } else {
                        if (isOriginCornerWhite == false){
                            block.setType(Material.ORANGE_CARPET);
                        }else {
                            block.setType(Material.RED_CARPET);
                        }
                    }
                }
            }
        }
    }

    public void cleanCollisionCarpets() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location location = new Location(originCorner.getWorld(), originCorner.getX() + x, originCorner.getY() + 1, originCorner.getBlockZ() + y); // Y-coordinate can be adjusted as needed
                Block block = location.getBlock();
                block.setType(Material.AIR);
            }
        }
    }

    /**
     * Determines the cardinal direction of the player based on their location.
     * This method converts the player's direction into one of the cardinal directions (East, South, West, North)
     * by analyzing the player's facing direction and ignoring the vertical component.
     *
     * @param player The player whose facing direction determines the board's orientation.
     * @return The direction vector representing one of the four cardinal directions (East, South, West, North).
     */
    private Vector getBoardDirection(Player player) {
        // Get the player's direction as a 2D vector
        Vector playerDirection = player.getLocation().getDirection();
        playerDirection.setY(0); // Ignore vertical component
        playerDirection = playerDirection.normalize();

        double x = playerDirection.getX();
        double z = playerDirection.getZ();

        // Determine cardinal direction based on player's facing direction
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

    /**
     * Updates the origin corner of the chessboard based on the player's facing direction.
     * The origin corner is adjusted to ensure the chessboard's orientation aligns with the player's view
     * and also takes the size of the board into account.
     */
    private void updateOriginCorner(Vector direction) {
        // Determine direction modifiers based on boardDirection
        int xMod = (int) direction.getX(); // East/West orientation modifier
        int zMod = (int) direction.getZ(); // North/South orientation modifier

        // Current origin corner coordinates
        int x = originCorner.getBlockX();
        int z = originCorner.getBlockZ();

        // Adjust origin corner based on player's facing direction
        if (xMod > 0 && zMod > 0) {
            // Facing East and South: Set origin at the bottom-left corner
            originCorner = new Location(originCorner.getWorld(), x, originCorner.getBlockY(), z);
        } else if (xMod < 0 && zMod > 0) {
            // Facing West and South: Shift origin to the right
            originCorner = new Location(originCorner.getWorld(), x - (size - 1), originCorner.getBlockY(), z);
        } else if (xMod < 0 && zMod < 0) {
            // Facing West and North: Shift origin both right and up
            originCorner = new Location(originCorner.getWorld(), x - (size - 1), originCorner.getBlockY(), z - (size - 1));
        } else if (xMod > 0 && zMod < 0) {
            // Facing East and North: Shift origin up
            originCorner = new Location(originCorner.getWorld(), x, originCorner.getBlockY(), z - (size - 1));
        }
    }

    /*
     * Solves the Queen's problem using a backtracking algorithm.
     * It tries to place all queens on the chessboard without conflicts, placing queens row by row,
     * and backtracking when a conflict occurs. It also handles visual updates of queen positions and board state.
     *
     * @return boolean True if the algorithm successfully places all queens, false otherwise.

    public boolean MCBacktrackStep() {
        if (console) {
            System.out.println("Start Backtracking Algorithm");
            printBoard();
        }

        // Clean and reset the board state before starting
        cleanCollisionCarpets();
        removeALLQueensFromBoard();

       // so();

        if (console) {
            System.out.println("Queens Sorted");
            printBoard();
        }

        int row = 0;
        ArrayList<Queen> newQ = new ArrayList<Queen>();

        // Check and add queens that are in the same row, excluding collisions
        for (Queen q : queens) {
            if (q.getY() != row) {
                if (console) {
                    System.out.println("Transfer Abort");
                    System.out.println(row + " != " + q.getY());
                }
                break;
            }

            queens.remove(q);
            if (checkCollision(q) == false) {
                newQ.add(q);
            }
            row++;
        }

        setQueens(newQ);

        if (console) {
            System.out.println("New Queens Placed");
            printBoard();
        }

        spawnAllQueens();

        statex = 0;
        statey = queens.size();

        // Try placing queens on the board row by row
        while (numberOfQueens() != size) {
            for (int i = 0; i < size; i++) {
                statex = i;
                Location l = new Location(originCorner.getWorld(), originCorner.getX() + statex, originCorner.getY() + 1, originCorner.getZ() + statey);
                Block block = l.getBlock();
                block.setType(Material.BLUE_CARPET);

                // Attempt to place the queen, backtracking if necessary
                if (addTestedQueen(i, row)) {
                    spawnQueen(queens.getLast());
                    row++;
                    block.setType(Material.AIR);
                    break;
                } else if (i == size - 1) {
                    block.setType(Material.AIR);
                    row = MCbackStep(row) + 1;
                }
            }
        }
        return true;
    }

    /**
     * Performs a backstep in the backtracking algorithm.
     * This method removes the last placed queen and attempts to place it in the next possible position in the same row.
     *
     * @param row The current row where backtracking is performed.
     * @return int The updated row after backtracking.

    public int MCbackStep(int row) {
        int oldX = queens.get(numberOfQueens() - 1).getX();

        // Remove last queen placed
        removeQueen();

        if (console) {
            System.out.println("Start Backstep -> row: " + row);
        }

        int newX = oldX + 1;

        // Try placing the queen in the next position until a valid spot is found
        while (!addTestedQueen(newX, row)) {
            newX++;
            if (console) {
                System.out.println("Back-Place -> row(Y): " + row + "  X = " + newX);
            }

            if (newX >= size) {
                backStep(row);
                newX = 0;
            }
        }

        if (console) {
            System.out.println("Back-Place -> row(Y): " + row + "  X = " + newX);
            printBoard();
        }
        return row;
    }*/


}


