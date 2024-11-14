package de.hsmw.algDatDamen.menu;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.MChessBoard;
import de.hsmw.algDatDamen.Queen;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import static de.hsmw.algDatDamen.AlgDatDamen.devMenu;
import static de.hsmw.algDatDamen.AlgDatDamen.saveManager;

/**
 * Class which contains the functions/handles used to develop the tutorial.
 */
public class DevelopmentHandles {

    public static int boardSize = 3;
    private final AlgDatDamen plugin;

    public DevelopmentHandles(AlgDatDamen plugin) {
        this.plugin = plugin;
    }

    /**
     * Generates a new board.
     * @param event Triggering event.
     * @param size Size of the board.
     */
    public static void handleBoardCreation(PlayerInteractEvent event, Integer size) {
        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();

        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) {
            player.sendMessage(Component.text("Du musst einen Block anklicken, an dem das " +
                    "Schachbrett gespawnt werden soll!", NamedTextColor.RED));
            return;
        };

        MChessBoard cb = new MChessBoard(clickedBlock.getLocation(), boardSize, player);
        saveManager.getCbList().add(cb);
    }

    /**
     * Places a queen on the clicked block. No check for allowed placement. Use <code>placeTestedQueen()</code> instead.
     * @param event Triggering event.
     */
    public static void placeQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());

        Queen existingQueen = mcB.getQueenAt(event.getClickedBlock().getLocation());
        if (existingQueen != null) {
            mcB.removeQueen(existingQueen); // Remove the existing queen
            //getLogger().info("Existing queen has been removed from the board!");
            event.setCancelled(true);
        } else {
            mcB.addQueen(event.getClickedBlock().getLocation());
            //getLogger().info("Queen has been successfully placed and spawned on the board!");
        }
        event.setCancelled(true);

    }

    public static void placeUserCarpet(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.placeUserCarpet(event.getClickedBlock().getLocation());
        event.setCancelled(true);
    }

    public static void checkUserCarpets(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB.checkUserCarpets()) {
            event.getPlayer().sendMessage(Component.text("Die Teppiche sind korrekt!", NamedTextColor.GREEN));
        } else {
            event.getPlayer().sendMessage(Component.text("Die Teppiche sind nicht korrekt!", NamedTextColor.RED));
        }
        event.setCancelled(true);
    }

    /**
     * Like <code>placeQueen</code> but with a check, if the queen is allowed on this field of the board.
     * @param event Triggering event.
     */
    public static void placeTestedQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.addTestedQueen(event.getClickedBlock().getLocation());
        event.setCancelled(true);
    }

    /**
     * Gets the clicked chess board if possible.
     * @param event Triggering event.
     * @return Clicked chess board or null.
     */
    public static MChessBoard getClickedMCB(PlayerInteractEvent event) {
        for (MChessBoard mcB : saveManager.getCbList()) {
            if (mcB.isPartOfBoard(event.getClickedBlock().getLocation())) {
                return mcB;
            }
        }
        return null;
    }

    /**
     * Removes the clicked chess board if there is one.
     * @param event Triggering event.
     */
    public static void removeChessBoardFromGame(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
            event.getPlayer().sendMessage(Component.text("Du musst einen Block des Schachbrettes anklicken, " +
                    "welches gelöscht werden soll!", NamedTextColor.RED));
            return;
        };
        MChessBoard mcB = getClickedMCB(event);
        mcB.removeChessBoardFromGame();
        saveManager.getCbList().remove(mcB);
        event.setCancelled(true);
    }

    /**
     * Increases the board size between 3 and 12.
     * @param event Not used but needed by <code>addMenuItem()</code>
     */
    public static void increaseBoardSize(PlayerInteractEvent event) {
        if (boardSize < 12) {
            boardSize++;
        } else {
            boardSize = 3;
        }
        devMenu.updateItemName(MenuSlots.BOARD_SIZE, "Größe: " + boardSize);
    }

    /**
     * Enables or disables the collision carpets on the given chess board.
     * @param event Triggering event.
     */
    public static void handleCollisionCarpets(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);

        if (mcB.isCollisionCarpets()) {
            mcB.cleanCollisionCarpets();
            mcB.setCollisionCarpets(false);
        } else {
            mcB.spawnCollisionCarpets();
            mcB.setCollisionCarpets(true);
        }
        event.setCancelled(true);
    }

    /**
     * A full run of the algorithm on the given chess board.
     * @param event The triggering event.
     */
    public static void handleBacktrack(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());
        mcB.playBacktrack();
        mcB.spawnAllQueens();
        event.setCancelled(true);
    }

    /**
     * Performs the next step of the algorithm.
     * @param event The triggering event.
     */
    public static void handleBacktrackStep(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());
        mcB.animationStep();
        event.setCancelled(true);
    }

    /**
     * Removes all queens from the chess board.
     * @param event The triggering event.
     */
    public static void removeAllQueens(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.removeALLQueensFromBoard();
        event.setCancelled(true);
    }

    public static void rotateQueens(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.rotateMQueens(1);
        event.setCancelled(true);
    }

    private void handleBacktrackAnimation(PlayerInteractEvent event){
        MChessBoard mcB = getClickedMCB(event);
        mcB.verfyQueens();
        plugin.BacktrackAnimationStep(mcB, 5);
        event.setCancelled(true);
    }

    private void handleBacktrackAnimationQueenStep(PlayerInteractEvent event){
        MChessBoard mcB = getClickedMCB(event);
        mcB.verfyQueens();
        plugin.BacktrackAnimationQueenStep(mcB, 20);
        event.setCancelled(true);
    }

}
