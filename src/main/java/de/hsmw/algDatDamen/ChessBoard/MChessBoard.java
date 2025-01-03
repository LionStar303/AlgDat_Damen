package de.hsmw.algDatDamen.ChessBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.hsmw.algDatDamen.AlgDatDamen;

@SuppressWarnings("unused")
public class MChessBoard extends ChessBoard {

    // ----------- Attributes -----------

    private Location originCorner; // Starting corner for the chessboard
    private boolean isOriginCornerWhite; // Indicates if the origin corner is white
    private boolean collisionCarpets; // Checks if carpets cause collision issues
    private Material whiteFieldMaterial; // Material used for white fields on the board
    private Material blackFieldMaterial; // Material used for black fields on the board
    private boolean isAnimationRunning;
    private boolean active; // true wenn der Spieler Pieces setzen darf, false wenn nicht
    private BukkitRunnable currentAnimationTask = null;
    private Map<Location, Material> savedBlocks;
    private MChessBoardMode mode;

    private final static Material USER_CARPET_MATERIAL = Material.LIME_CARPET;

    // ----------- Constructors -----------

    /**
     * Constructor to create a chessboard with specific parameters and default field
     * materials.
     * Use this Constructor for Level Creation
     * 
     * @param originCorner Starting location for the chessboard.
     * @param size         The size of the chessboard.
     *                     he player for determining the board direction.
     */
    public MChessBoard(Location originCorner, int size, Player player) {
        this(originCorner, size, player, false);
    }

    /**
     * Constructor to create a chessboard with specific parameters and default field
     * materials.
     *
     * @param originCorner     Starting location for the chessboard.
     * @param size             The size of the chessboard.
     *                         he player for determining the board direction.
     * @param spawnInDirection board will be spawned in the direction of the player
     *                         + no NPC will be spawned, SET FALSE IF USED IN LEVEL
     *                         CONFIG
     */
    public MChessBoard(Location originCorner, int size, Player player, boolean spawnInDirection) {
        this(originCorner, size, player, Material.WHITE_CONCRETE, Material.GRAY_CONCRETE, spawnInDirection); // Default
                                                                                                             // materials:
                                                                                                             // wool
    }

    /**
     * Constructor to create a chessboard with specific parameters.
     *
     * @param originCorner       Starting location for the chessboard.
     * @param size               The size of the chessboard.
     * @param player             The player for determining the board direction.
     * @param whiteFieldMaterial Material used for the white fields.
     * @param blackFieldMaterial Material used for the black fields.
     * @param spawnInDirection   board will be spawned in the direction of the playe
     *                           + no NPC will be spawned
     */
    public MChessBoard(Location originCorner, int size, Player player, Material whiteFieldMaterial,
            Material blackFieldMaterial, boolean spawnInDirection) {
        this.size = size;
        this.pieces = new ArrayList<>();
        this.console = false;
        this.originCorner = originCorner;
        this.isOriginCornerWhite = (originCorner.getBlock().getType() == whiteFieldMaterial);
        this.whiteFieldMaterial = whiteFieldMaterial;
        this.blackFieldMaterial = blackFieldMaterial;
        this.stateX = 0;
        this.stateY = 0;
        this.isAnimationRunning = false;
        this.active = false;
        this.mode = MChessBoardMode.INACTIVE;
        if (spawnInDirection) {
            updateOriginCorner(getBoardDirection(player));
        }
    }

    // ----------- Getters and Setters -----------

    // Getter and Setter Methods for MChessBoard attributes

    /**
     * Gets the origin corner location of the chessboard.
     *
     * @return The starting location of the chessboard.
     */
    public Location getOriginCorner() {
        return originCorner;
    }

    /**
     * Sets the origin corner location of the chessboard.
     *
     * @param originCorner The new starting location for the chessboard.
     */
    public void setOriginCorner(Location originCorner) {
        this.originCorner = originCorner;
    }

    /**
     * Checks if the origin corner is a white field.
     *
     * @return True if the origin corner is white, false otherwise.
     */
    public boolean isOriginCornerWhite() {
        return isOriginCornerWhite;
    }

    /**
     * Sets whether the origin corner is a white field.
     *
     * @param isOriginCornerWhite True if the origin corner should be white, false
     *                            otherwise.
     */
    public void setOriginCornerWhite(boolean isOriginCornerWhite) {
        this.isOriginCornerWhite = isOriginCornerWhite;
    }

    /**
     * Checks if carpets cause collision on the chessboard.
     *
     * @return True if collision carpets are enabled, false otherwise.
     */
    public boolean isCollisionCarpets() {
        return collisionCarpets;
    }

    /**
     * Sets whether carpets cause collision on the chessboard.
     *
     * @param collisionCarpets True to enable collision for carpets, false
     *                         otherwise.
     */
    public void setCollisionCarpets(boolean collisionCarpets) {
        this.collisionCarpets = collisionCarpets;
    }

    /**
     * Gets the material used for white fields on the chessboard.
     *
     * @return The material for white fields.
     */
    public Material getWhiteFieldMaterial() {
        return whiteFieldMaterial;
    }

    /**
     * Sets the material used for white fields on the chessboard.
     *
     * @param whiteFieldMaterial The new material for white fields.
     */
    public void setWhiteFieldMaterial(Material whiteFieldMaterial) {
        this.whiteFieldMaterial = whiteFieldMaterial;
    }

    /**
     * Gets the material used for black fields on the chessboard.
     *
     * @return The material for black fields.
     */
    public Material getBlackFieldMaterial() {
        return blackFieldMaterial;
    }

    /**
     * Sets the material used for black fields on the chessboard.
     *
     * @param blackFieldMaterial The new material for black fields.
     */
    public void setBlackFieldMaterial(Material blackFieldMaterial) {
        this.blackFieldMaterial = blackFieldMaterial;
    }

    /**
     * Checks if an animation is currently running on the chessboard.
     *
     * @return True if an animation is running, false otherwise.
     */
    public boolean isAnimationRunning() {
        return isAnimationRunning;
    }

    /**
     * @return True wenn Spieler Pieces setzen darf, sonst false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether an animation is currently running on the chessboard.
     *
     * @param isAnimationRunning True to indicate that an animation is running,
     *                           false otherwise.
     */
    public void setAnimationRunning(boolean isAnimationRunning) {
        this.isAnimationRunning = isAnimationRunning;
    }

    /**
     * @param active wenn Spieler Pieces setzen darf, sonst false
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the current animation task running on the chessboard.
     *
     * @return The current animation task or null if none is running.
     */
    public BukkitRunnable getCurrentAnimationTask() {
        return currentAnimationTask;
    }

    /**
     * Sets the current animation task running on the chessboard.
     *
     * @param currentAnimationTask The new animation task to set.
     */
    public void setCurrentAnimationTask(BukkitRunnable currentAnimationTask) {
        this.currentAnimationTask = currentAnimationTask;
    }

    // ----------- Functional Methods -----------

    // --- extension getter / setter ---

    public Location getLocation(Piece p) {
        Location l = new Location(originCorner.getWorld(), originCorner.getX() + p.getX(),
        originCorner.getY() + 1, originCorner.getZ() + p.getY());
        return l;
    }

    public MChessBoardMode getMode() {
        return this.mode;
    }

    /**
     * Retrieves the chess piece at a specific location on the board.
     *
     * @param l The location to check for a piece.
     * @return The {@link Piece} at the specified location, or null if no piece
     *         exists there.
     */
    public Piece getPieceAt(Location l) {
        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();

        for (Piece p : this.pieces) {
            if (console) {
                System.out.println(p.getX() + " == " + (l.getBlockX() - minX) + " && " + p.getY() + " == "
                        + (l.getBlockZ() - minZ));
            }
            if ((p.getX() == (l.getBlockX() - minX)) && (p.getY() == (l.getBlockZ() - minZ))) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get the location of the queen on the board.
     *
     * @param q The queen whose location is to be determined.
     * @return The Location object representing the queen's position.
     */
    private Location getLocationofPiece(Piece p) {
        double x = p.getX() + originCorner.getBlockX();
        double z = p.getY() + originCorner.getBlockZ();

        return new Location(originCorner.getWorld(), x, originCorner.getY() + 2, z);
    }

    /**
     * Determines the cardinal direction of the player based on their location.
     * This method converts the player's direction into one of the cardinal
     * directions (East, South, West, North)
     * by analyzing the player's facing direction and ignoring the vertical
     * component.
     *
     * @param player The player whose facing direction determines the board's
     *               orientation.
     * @return The direction vector representing one of the four cardinal directions
     *         (East, South, West, North).
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

    public void setMode(MChessBoardMode mode) {
        this.mode = mode;
    }

    /**
     * setzt den <code>mode</code> auf <code>INACTIVE</code> zurück
     */
    public void resetMode() {
        this.mode = MChessBoardMode.INACTIVE;
    }

    /**
     * Updates the origin corner of the chessboard based on the player's facing
     * direction.
     * The origin corner is adjusted to ensure the chessboard's orientation aligns
     * with the player's view
     * and also takes the size of the board into account.
     * 
     * @return The location where the NPC should spawn.
     */
    private void setOriginCorner(Vector direction) {
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
            originCorner = new Location(originCorner.getWorld(), x - (size - 1), originCorner.getBlockY(),
                    z - (size - 1));
        } else if (xMod > 0 && zMod < 0) {
            // Facing East and North: Shift origin up
            originCorner = new Location(originCorner.getWorld(), x, originCorner.getBlockY(), z - (size - 1));
        }
    }

    // --- Override --

    @Override
    public MChessBoard clone() {
        // Klonen der ursprünglichen MChessBoard-Attribute
        MChessBoard clonedBoard = new MChessBoard(
                this.originCorner.clone(), // Klone die Location
                this.size,
                null, // Spieler wird nicht kopiert, da es kein direkter Bezug ist
                this.whiteFieldMaterial,
                this.blackFieldMaterial,
                false // spawnInDirection wird hier auf einen Standardwert gesetzt
        );

        // Kopiere weitere Attribute
        clonedBoard.isOriginCornerWhite = this.isOriginCornerWhite;
        clonedBoard.collisionCarpets = this.collisionCarpets;
        clonedBoard.isAnimationRunning = this.isAnimationRunning;
        clonedBoard.active = this.active;
        clonedBoard.savedBlocks = new HashMap<>(this.savedBlocks); // Tiefenkopie der Map
        clonedBoard.mode = this.mode;

        // Klone alle Pieces (falls die Liste kopierbar ist)
        clonedBoard.pieces = new ArrayList<>(this.pieces); // Seichte Kopie der Liste
        return clonedBoard;
    }

    // --- Update ---

    /**
     * Updates the visual collision carpets on the chessboard.
     * If collision carpets are enabled, this method ensures they are spawned.
     * Otherwise, any existing carpets are removed.
     */
    public void updateCollisionCarpets() {
        // Teppiche entfernen
        despawnCollisionCarpets();
        // wieder spawnen falls enabled
        if (this.collisionCarpets)
            spawnCollisionCarpets();
    }

    public void updateBoard() {
        despawnChessBoard();
        spawnChessBoard();
        updatePieces();
        updateCollisionCarpets();
    }

    public void updatePieces() {
        despawnAllPieces();
        spawnAllPieces();
    }

    // --- Check ---

    /**
     * Checks whether a given location is within the boundaries of the chessboard.
     *
     * @param location The location to check.
     * @return boolean True if the location is part of the board, false otherwise.
     */
    public boolean isPartOfBoard(Location location) {
        // Return false if the board's origin is not set
        if (originCorner == null) {
            return false;
        }

        // Define the boundaries of the board based on its size and origin corner
        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();
        int minY = originCorner.getBlockY();
        int maxX = minX + size - 1;
        int maxZ = minZ + size - 1;
        int maxY = minY + 2;

        // Extract the coordinates of the given location
        int x = location.getBlockX();
        int z = location.getBlockZ();
        int y = location.getBlockY();

        // Check if the location falls within the defined boundaries
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ && y >= minY && y <= maxY
                && location.getWorld().equals(originCorner.getWorld());
    }

    /**
     * checks if all the user carpets represent a valid solution.
     *
     * @return boolean True if the solution is correct, false otherwise.
     */
    public boolean checkUserCarpets() {

        for (int x = 0; x < size; x++) { // Iterate through the board's X-axis
            for (int y = 0; y < size; y++) { // Iterate through the board's Y-axis
                // Calculate the location of the current carpet
                Location location = new Location(
                        originCorner.getWorld(),
                        originCorner.getX() + x,
                        originCorner.getY() + 1,
                        originCorner.getZ() + y);

                Block block = location.getBlock();

                // Skip blocks where a queen's bottom part is present
                if (block.getType() == AlgDatDamen.QUEEN_BLOCK_BOTTOM || block.getType() == AlgDatDamen.KNIGHT_BLOCK_BOTTOM) {
                    continue;
                }

                // keine Kollision aber Carpet
                if(!checkCollision(x, y) && block.getType() == USER_CARPET_MATERIAL) return false;
                // Kollision aber kein Carpet
                if(checkCollision(x, y) && block.getType() != USER_CARPET_MATERIAL) return false;
            }
        }
        // true wenn keine Fehler
        return true;

        /*
        // return false if there are no pieces on the board
        if (this.pieces.size() == 0) {
            return false;
        }

        // return false and skip checking if no carpets are placed
        boolean carpetPlaced = false;
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                Location location = new Location(originCorner.getWorld(), originCorner.getX() + x,
                        originCorner.getY() + 1, originCorner.getZ() + z);
                if (location.getBlock().getType() == Material.LIME_CARPET) {
                    carpetPlaced = true;
                    break;
                }
            }
        }
        if (!carpetPlaced) {
            return false;
        }

        boolean correct = true;
        for (int i = 0; i < size; i++) {
            outerloop: for (int j = 0; j < size; j++) {
                boolean collision = false;
                for (Piece piece : pieces) {
                    if (piece.getX() == i && piece.getY() == j) {
                        continue outerloop;
                    }
                    if (piece.checkCollision(i, j)) {
                        collision = true;
                        break;
                    }
                }

                if (collision) {
                    Location carpetLocation = new Location(originCorner.getWorld(), originCorner.getBlockX() + i,
                            originCorner.getBlockY() + 1, originCorner.getBlockZ() + j);

                    if (carpetLocation.getBlock().getType() != Material.LIME_CARPET) {
                        correct = false;
                    }
                }
            }
        }

        return correct;
        */
    }

    /**
     * checks if the placed movement carpets are a valid solution.
     */
    public void spawnUserCarpetSolution() {
        if (checkUserCarpets() || this.pieces.size() == 0) {
            return;
        }

        despawnCollisionCarpets();
        for (int i = 0; i < size; i++) {
            outerloop: for (int j = 0; j < size; j++) {
                boolean collision = false;
                for (Piece piece : pieces) {
                    if (piece.getX() == i && piece.getY() == j) {
                        continue outerloop;
                    }
                    if (piece.checkCollision(i, j)) {
                        collision = true;
                        break;
                    }
                }

                if (collision) {
                    Location carpetLocation = new Location(originCorner.getWorld(), originCorner.getBlockX() + i,
                            originCorner.getBlockY() + 1, originCorner.getBlockZ() + j);

                    if (carpetLocation.getBlock().getType() != Material.LIME_CARPET) {
                        carpetLocation.getBlock().setType(Material.LIME_CARPET);
                    }
                }
            }
        }
    }

    // --- add and remove ---

    /**
     * Handles a player interaction depending on the current {@link MChessBoardMode}
     *
     * @param l The clicked {@link Location}
     * @param p The {@link Piece} to be added.
     * @return True if the piece was placed, false otherwise
     */
    public boolean addPiece(Location l, Piece p) {
        // check if location is part of the chessboard and chessboard allows player
        // interaction
        if (!isPartOfBoard(l))
            return false;

        // get local coordinates
        int minX = originCorner.getBlockX();
        int minZ = originCorner.getBlockZ();
        p.setX(l.getBlockX() - minX);
        p.setY(l.getBlockZ() - minZ);
        if(console)System.out.println("Prüfung: p.getX() == " + p.getX() + " && p.getY() == " + p.getY());

        Piece existingPiece = this.getPieceAt(l);
        switch (mode) {
            // keine Interaktion
            case INACTIVE:
                return false;
            // jede Dame wird gesetzt
            case NORMAL:
                // remove existing piece if location is already occupied
                if (existingPiece != null) {
                    this.removePiece(existingPiece);
                    return false;
                }
                if (addPiece(p)) {
                    spawnPiece(p);
                    return true;
                } else
                    return false;
                // nur nicht bedrohte Damen werden gesetzt
            case TESTED:
                // remove existing piece if location is already occupied
                if (existingPiece != null) {
                    this.removePiece(existingPiece);
                    return false;
                }
                if (addTestedPiece(p)) {
                    spawnPiece(p);
                    return true;
                } else
                    return false;
                // wie TESTED, nur falsche Damen explodieren
            case EXPLODING:
                // remove existing piece if location is already occupied
                if (existingPiece != null) {
                    this.removePiece(existingPiece);
                    return false;
                }
                if (addTestedPiece(p)) {
                    spawnPiece(p);
                    return true;
                }
                playExplosionAnimation(l);
                return false;
            case TUTORIAL:
                /*
                 * TODO verhalten im Tutorial Mode hinzufügen
                 * Das Schachbrett zeigt mit blauen Teppichen an, wo der Algorithmus das nächste
                 * Piece platzieren würde
                 * Spieler kann das zuletzt platzierte Piece entfernen, um einen Schritt zurück
                 * zu gehen
                 * Ansonsten kann er das Piece nur an der markierten Position platzieren
                 */
                // remove existing piece if location is already occupied
                if(isSolved()){
                    return false;
                }
                
                if(pieces.size() == 0){
                    if(stateX == p.getX() && stateY == p.getY()){
                        addPiece(p);
                        spawnPiece(p);
                        return true;
                    }
                    getLocation(new Piece(stateX, stateY)).getBlock().setType(Material.BLUE_CARPET);
                    return false;
                }

                int oldsize = pieces.size();
                Piece removed = pieces.getLast();
                int OldstateX = stateX;
                int OldstateY = stateY;
                playBacktrackToNextPiece(p.clone());
                if(pieces.size() > oldsize){
                    // Figur wurde hinzugefügt
                    if (console) {
                        System.out.println("Figur wurde hinzugefügt");
                        System.out.println("Position der letzten Spielfigur: x = " + pieces.getLast().getX() + ", y = " + pieces.getLast().getY());
                        System.out.println("Prüfung: p.getX() == " + p.getX() + " && p.getY() == " + p.getY());
                        printBoard(true);
                    }
                    updatePieces();
                    if (pieces.getLast().getX() != p.getX() || pieces.getLast().getY() != p.getY()){
                        if(console)System.out.println("Remove last du HUSO!");
                        despawnPiece(pieces.getLast());
                        Block b = getLocation(pieces.getLast()).getBlock();
                        removeLastPiece();
                        updateCollisionCarpets();
                        b.setType(Material.BLUE_CARPET);
                        this.stateX = OldstateX;
                        this.stateY = OldstateY;
                        playExplosionAnimation(l);
                        return false;
                    }
                    
                } else {
                    // Figur wurde gelöscht
                    if (console) {
                        System.out.println("Figur wurde gelöscht");
                        System.out.println("Position der letzten Spielfigur: x = " + removed.getX() + ", y = " + removed.getY());
                        System.out.println("Prüfung: p.getX() == " + p.getX() + " && p.getY() == " + p.getY());
                        printBoard(true);
                    }
                    updatePieces();
                    if (removed.getX() != p.getX() || removed.getY() != p.getY()){
                        if(console)System.out.println("ADD Removed last du HUSO!");
                        addPiece(removed);
                        spawnPiece(removed);
                        getLocation(removed).getBlock().getRelative(BlockFace.UP).setType(Material.END_STONE);
                        updateCollisionCarpets();
                        this.stateX = OldstateX;
                        this.stateY = OldstateY;
                        playExplosionAnimation(l);
                        return false;
                    }
                    
                }

                return true; // wenn Piece richtig gesetzt wurde
            default:
                return false;
        }
    }

    /**
     * spielt an der übergebenen {@link Location} eine Explosions Animation ab
     * 
     * @param l Location für die Animation
     */
    private void playExplosionAnimation(Location l) {
        // TODO Animation verfeinern
        l.getWorld().spawnParticle(Particle.LAVA, l, 20, 0, 1, 0);
        l.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, l, 10, 0, 1, 0);
        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1);
        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 1);
    }

    /**
     * Removes a specific chess piece from the board.
     *
     * @param p The {@link Piece} to remove.
     */
    public void removePiece(Piece p) {
        despawnPiece(p); // Visual removal from the board
        pieces.remove(p);
        updateCollisionCarpets();
    }

    /**
     * Removes all pieces from the board and clears the visual representation.
     */
    public void removeAllPieces() {
        despawnAllPieces();
        pieces.clear();
        updateCollisionCarpets();
    }

    // --- Spawn/Despawn ---

    /**
     * Spawns the chessboard with alternating white and gray tiles.
     */
    public void spawnChessBoard() {
        saveBlocks();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                    Material material = Material.AIR;
                    if (y == 0) {
                        boolean isWhite = ((x + z) % 2 == 0) == this.isOriginCornerWhite;
                        material = isWhite ? whiteFieldMaterial : blackFieldMaterial;
                    }

                    Block currentBlock = originCorner.getBlock().getWorld().getBlockAt(
                            originCorner.getBlockX() + x,
                            originCorner.getBlockY() + y,
                            originCorner.getBlockZ() + z);
                    currentBlock.setType(material);
                }
            }
        }
        updateCollisionCarpets();
    }

    /**
     * Spawns a specific chess piece on the board.
     *
     * @param p The {@link Piece} to spawn.
     * @return True if the piece was successfully spawned, false otherwise.
     */
    public boolean spawnPiece(Piece p) {
        int x = p.getX();
        int y = p.getY();

        Location pieceLocation = originCorner.clone().add(x, 2, y);

        if (pieceLocation.getBlock().getType() != Material.AIR) {
            return false;
        }

        switch (p.getLetter()) {
            case 'Q': // Queen
                placePieceBlocks(pieceLocation, AlgDatDamen.QUEEN_BLOCK_TOP, AlgDatDamen.QUEEN_BLOCK_BOTTOM);
                break;
            case 'S': // Superqueen
                placePieceBlocks(pieceLocation, AlgDatDamen.SUPERQUEEN_BLOCK_TOP, AlgDatDamen.QUEEN_BLOCK_BOTTOM);
                break;
            case 'K': // Knight
                placePieceBlocks(pieceLocation, AlgDatDamen.KNIGHT_BLOCK_TOP, AlgDatDamen.KNIGHT_BLOCK_BOTTOM);
                break;
            default:
                if (console) {
                    System.out.println("non specific Piece can not spawned");
                }
                return false; // Invalid piece type
        }

        updateCollisionCarpets();
        if (console) {
            printBoard(true);
            System.out.println("successfully spawned Piece");
        }

        return true;
    }

    /**
     * Helper method to place the top and bottom blocks for a piece.
     *
     * @param location        The top block's location.
     * @param topBlockType    The material type for the top block.
     * @param bottomBlockType The material type for the bottom block.
     */
    private void placePieceBlocks(Location location, Material topBlockType, Material bottomBlockType) {

        // Set the top block
        location.getBlock().setType(topBlockType);

        // Check and set the bottom block
        location.getBlock().getRelative(BlockFace.DOWN).setType(bottomBlockType);

        if (console) {
            System.out.println("Placing top block: " + topBlockType + " at " + location);
            System.out.println("Placing bottom block: " + bottomBlockType + " at "
                    + location.getBlock().getRelative(BlockFace.DOWN).getLocation());
            System.out
                    .println("Current bottom block type: " + location.getBlock().getRelative(BlockFace.DOWN).getType());
        }
    }

    /**
     * Spawns all pieces stored in the pieces list onto the board.
     */
    public void spawnAllPieces() {
        if (pieces == null || pieces.isEmpty()) {
            return;
        }
        for (Piece piece : pieces) {
            spawnPiece(piece);
        }
    }

    /**
     * Spawns collision carpets on the chessboard to visually indicate restricted or
     * occupied squares.
     * The carpets alternate between orange and red, depending on the board's color
     * scheme and position.
     *
     * Squares marked with carpets indicate potential collision areas for queens,
     * except squares
     * directly occupied by the bottom part of a queen.
     */
    public void spawnCollisionCarpets() {
        for (int x = 0; x < size; x++) { // Iterate through the board's X-axis
            for (int y = 0; y < size; y++) { // Iterate through the board's Y-axis
                // Calculate the location of the current square
                Location location = new Location(
                        originCorner.getWorld(),
                        originCorner.getX() + x,
                        originCorner.getY() + 1,
                        originCorner.getZ() + y);

                Block block = location.getBlock();

                // Skip blocks where there is no collision or where a queen's bottom part is
                // present
                if (!checkCollision(x, y) || block.getType() == AlgDatDamen.QUEEN_BLOCK_BOTTOM
                        || block.getType() == AlgDatDamen.KNIGHT_BLOCK_BOTTOM) {
                    continue;
                }

                // Determine the color of the carpet based on the square's position and board
                // scheme
                if ((x + y) % 2 == 0) { // Even-positioned squares
                    block.setType(isOriginCornerWhite ? Material.ORANGE_CARPET : Material.RED_CARPET);
                } else { // Odd-positioned squares
                    block.setType(!isOriginCornerWhite ? Material.ORANGE_CARPET : Material.RED_CARPET);
                }
            }
        }
    }

    /**
     * Removes all collision carpets from the chessboard.
     * Only carpets are removed; blocks representing queens or other elements are
     * preserved.
     */
    public void despawnCollisionCarpets() {
        for (int x = 0; x < size; x++) { // Iterate through the board's X-axis
            for (int y = 0; y < size; y++) { // Iterate through the board's Y-axis

                Location location = new Location(
                        originCorner.getWorld(),
                        originCorner.getX() + x,
                        originCorner.getY() + 1,
                        originCorner.getZ() + y);

                Block block = location.getBlock();

                // Skip blocks where there is collision or where a queen's bottom part is
                // present
                if (block.getType() == AlgDatDamen.QUEEN_BLOCK_BOTTOM
                        || block.getType() == AlgDatDamen.KNIGHT_BLOCK_BOTTOM) {
                    continue;
                }

                // Clear the block by setting it to air
                block.setType(Material.AIR);
            }
        }
    }

    /**
     * Places a purple carpet at the specified location to represent a block a queen
     * can move to.
     *
     * @param l
     */
    public void spawnUserCarpet(Location l) {
        Material carpMaterial = USER_CARPET_MATERIAL;
        if (!isPartOfBoard(l)) {
            return;
        }

        // Remove existing carpet if it's a valid move
        if (l.getY() == originCorner.getY() + 1 && l.getBlock().getType() == carpMaterial) {
            l.getBlock().setType(Material.AIR);
            return;
        }

        // return if top queen block got clicked
        if (l.getY() > originCorner.getY()) {
            return;
        }

        int x = l.getBlockX();
        int z = l.getBlockZ();

        Location location = new Location(originCorner.getWorld(), x, originCorner.getBlockY() + 1, z);

        // set Carpet at desired Location
        Block block = location.getBlock();
        block.setType(carpMaterial);
    }

    /**
     * Despawns the chessboard by resetting its materials and removing all visual
     * elements.
     *
     * This method:
     * - Sets the materials for black and white fields to the original blocks
     * - Disables collision carpets to prevent their reappearance.
     * - Removes all existing collision carpets and queens from the board.
     * - Respawns the board layout with the cleared state.
     */
    public void despawnChessBoard() {

        // Remove all queens from the chessboard
        despawnAllPieces();

        // Remove any remaining collision carpets from the board
        despawnCollisionCarpets();

        // Reset the field materials to the original blocks
        restoreBlocks();
    }

    /**
     * Saves the original blocks at the location of the chessboard.
     */
    public void saveBlocks() {
        this.savedBlocks = new HashMap<>();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                    Location location = new Location(originCorner.getWorld(), originCorner.getX() + x,
                            originCorner.getY() + y, originCorner.getZ() + z);
                    Material block = location.getBlock().getType();
                    savedBlocks.put(location, block);
                }
            }
        }
    }

    /**
     * Restores the original blocks at the location of the chessboard.
     */
    public void restoreBlocks() {
        if (savedBlocks == null) {
            return;
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                    Location location = new Location(originCorner.getWorld(), originCorner.getX() + x,
                            originCorner.getY() + y, originCorner.getZ() + z);
                    location.getBlock().setType(savedBlocks.get(location));
                }
            }
        }
    }

    public void despawnPiece(Piece p) {
        if (p != null) {
            Location queenLocation = getLocationofPiece(p);
            if (queenLocation != null) {
                //queenLocation.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);  // Remove Carpets
                queenLocation.getBlock().setType(Material.AIR); // Remove top block
                queenLocation.getBlock().getRelative(BlockFace.DOWN).setType(Material.AIR);
            }
        }
    }

    public void despawnAllPieces() {
        // clear all blocks above the board
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                for (int y = 2; y >= 1; y--) {
                    Location location = new Location(originCorner.getWorld(), originCorner.getX() + x,
                            originCorner.getY() + y, originCorner.getZ() + z);
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    // --- Rotation ---

    public void rotateMPieces(int rotation) {
        this.rotatePieces(rotation);
        updatePieces();
        updateCollisionCarpets();
    }

    // --- Animation Steps ---
    public boolean animationStepToNextField(Piece p) {

        boolean step = stepBacktrack(p);

        updatePieces();
        updateCollisionCarpets();
        if (step) {
            if (console) {
                System.out.println("ChessBoard is Solved!");
            }
            return true;
        }

        // Zeige nächstes Feld
        if (stateX + 1 <= size && stateY + 1 <= size) {
            Location location = new Location(originCorner.getWorld(), originCorner.getX() + stateX,
                    originCorner.getY() + 1, originCorner.getZ() + stateY); // Y-coordinate can be adjusted as needed
            Block block = location.getBlock();
            block.setType(Material.BLUE_CARPET);
        }

        return false;
    }

    public boolean animationStepToNextPiece(Piece p) {
        boolean step = playBacktrackToNextPiece(p);

        updatePieces();
        updateCollisionCarpets();
        if (step) {
            if (console) {
                System.out.println("ChessBoard is Solved!");
            }
            return true;
        }

        return false;

    }

    public boolean animationReverseStepToNextField(Piece p) {

        boolean step = reverseBackStep(p);

        updatePieces();
        updateCollisionCarpets();
        if (step) {
            if (console) {
                System.out.println("ChessBoard is Empty");
            }
            return true;
        }

        // Zeige nächstes Fel
        if (stateX + 1 <= size && stateY + 1 <= size) {
            Location location = new Location(originCorner.getWorld(), originCorner.getX() + stateX,
                    originCorner.getY() + 1, originCorner.getZ() + stateY); // Y-coordinate can be adjusted as needed
            Block block = location.getBlock();
            if (!(block.getType() == AlgDatDamen.QUEEN_BLOCK_BOTTOM)
                    && !(block.getType() == AlgDatDamen.KNIGHT_BLOCK_BOTTOM)) {
                block.setType(Material.BLUE_CARPET);
            }

        }

        return false;
    }

    public boolean animationReverseStepToNextPiece(Piece p) {
        boolean step = playReverseBackTrackToNextPiece(p);

        updatePieces();
        updateCollisionCarpets();
        if (step) {
            if (console) {
                System.out.println("ChessBoard is Solved!");
            }
            return true;
        }

        return false;

    }

    public void animationStepToRow(Piece p, int x) {
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }
        playBacktrackToRow(p, x);
        updatePieces();
        updateCollisionCarpets();

    }

    public void animationSolveToRow(Piece p, int x) {
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }
        solveBacktrackToRow(p, x);
        updatePieces();
        updateCollisionCarpets();
    }

    public void animationSolve(Piece p) {
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }
        playBacktrack(p);
        updatePieces();
        updateCollisionCarpets();
    }

    public boolean animationStepBongo(Piece p) {
        
        bongoStep(p);
        updatePieces();
        updateCollisionCarpets();
        return isSolved();
    }

    // --- Animation with MC ---

    public void animationField2Field(JavaPlugin plugin, long ticks, Piece p) {
        // Überprüfen, ob bereits eine Animation läuft
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }

        this.isAnimationRunning = true; // Setze das Flag, dass eine Animation läuft
        verfyPieces(p);
        try {
            // Starte eine neue Animation
            currentAnimationTask = new BukkitRunnable() {
                @Override
                public void run() {

                    if (animationStepToNextField(p)) {
                        if (console) {
                            System.out.println("Backtracking abgeschlossen, Scheduler wird beendet.");
                        }
                        isAnimationRunning = false; // Setze das Flag zurück
                        cancel(); // Stoppe den Task
                    }
                }
            };

            // Aufgabe wird alle `ticks` wiederholt ausgeführt
            currentAnimationTask.runTaskTimer(plugin, 0L, ticks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animationPiece2Piece(JavaPlugin plugin, long ticks, Piece p) {
        // Überprüfen, ob bereits eine Animation läuft
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }

        this.isAnimationRunning = true; // Setze das Flag, dass eine Animation läuft
        verfyPieces(p);
        try {
            // Starte eine neue Animation
            currentAnimationTask = new BukkitRunnable() {
                @Override
                public void run() {

                    if (animationStepToNextPiece(p)) {
                        if (console) {
                            System.out.println("Backtracking abgeschlossen, Scheduler wird beendet.");
                        }
                        isAnimationRunning = false; // Setze das Flag zurück
                        cancel(); // Stoppe den Task
                    }
                }
            };

            // Aufgabe wird alle `ticks` wiederholt ausgeführt
            currentAnimationTask.runTaskTimer(plugin, 0L, ticks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animationReverseField2Field(JavaPlugin plugin, long ticks, Piece p) {
        // Überprüfen, ob bereits eine Animation läuft
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }

        this.isAnimationRunning = true; // Setze das Flag, dass eine Animation läuft
        verfyPieces(p);
        try {
            // Starte eine neue Animation
            currentAnimationTask = new BukkitRunnable() {
                @Override
                public void run() {

                    if (animationReverseStepToNextField(p)) {
                        if (console) {
                            System.out.println("Reverse Backtracking abgeschlossen, Scheduler wird beendet.");
                        }
                        isAnimationRunning = false; // Setze das Flag zurück
                        cancel(); // Stoppe den Task
                    }
                }
            };

            // Aufgabe wird alle `ticks` wiederholt ausgeführt
            currentAnimationTask.runTaskTimer(plugin, 0L, ticks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animationReversePiece2Piece(JavaPlugin plugin, long ticks, Piece p) {
        // Überprüfen, ob bereits eine Animation läuft
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }

        this.isAnimationRunning = true; // Setze das Flag, dass eine Animation läuft
        verfyPieces(p);
        try {
            // Starte eine neue Animation
            currentAnimationTask = new BukkitRunnable() {
                @Override
                public void run() {

                    if (animationReverseStepToNextPiece(p)) {
                        if (console) {
                            System.out.println("Reverse Backtracking abgeschlossen, Scheduler wird beendet.");
                        }
                        isAnimationRunning = false; // Setze das Flag zurück
                        cancel(); // Stoppe den Task
                    }
                }
            };

            // Aufgabe wird alle `ticks` wiederholt ausgeführt
            currentAnimationTask.runTaskTimer(plugin, 0L, ticks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BongoSolveAnimation(JavaPlugin plugin, long ticks, Piece p) {
        // Überprüfen, ob bereits eine Animation läuft
        if (isAnimationRunning) {
            if (console) {
                System.out.println("Eine Animation läuft bereits! Die neue Animation wird nicht gestartet.");
            }
            return; // Verhindert das Starten einer neuen Animation
        }

        this.isAnimationRunning = true; // Setze das Flag, dass eine Animation läuft
        verfyPieces(p);
        try {
            currentAnimationTask = new BukkitRunnable() {
                @Override
                public void run() {

                    if (animationStepBongo(p)) {
                        if (console) {
                            System.out.println("Backtracking abgeschlossen, Scheduler wird beendet.");
                        }
                        cancel(); // Stoppe den Task
                        isAnimationRunning = false; // Setze das Flag zurück
                    }
                    if (stateX == (size)) {
                        pieces.clear();
                        stateX = 0;
                    }
                }
            };

            // Aufgabe wird alle `ticks` wiederholt ausgeführt
            currentAnimationTask.runTaskTimer(plugin, 0L, ticks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode, um eine laufende Animation zu stoppen
    public void stopCurrentAnimation() {
        if (currentAnimationTask != null) {
            currentAnimationTask.cancel(); // Stoppe den aktuellen Task
            isAnimationRunning = false; // Setze das Flag zurück
        }
    }

    /**
     * Updates the origin corner of the chessboard based on the player's facing
     * direction.
     * The origin corner is adjusted to ensure the chessboard's orientation aligns
     * with the player's view
     * and also takes the size of the board into account.
     * 
     * @return The location where the NPC should spawn.
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
            originCorner = new Location(originCorner.getWorld(), x - (size - 1), originCorner.getBlockY(),
                    z - (size - 1));
        } else if (xMod > 0 && zMod < 0) {
            // Facing East and North: Shift origin up
            originCorner = new Location(originCorner.getWorld(), x, originCorner.getBlockY(), z - (size - 1));
        }
    }
}
