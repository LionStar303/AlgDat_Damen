package de.hsmw.algDatDamen.menu;

import de.hsmw.algDatDamen.ChessBoard.*;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Piece;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import de.hsmw.algDatDamen.AlgDatDamen;
import static de.hsmw.algDatDamen.AlgDatDamen.devMenu;
import static de.hsmw.algDatDamen.AlgDatDamen.saveManager;

/**
 * Class that contains development handles for managing chess boards and related
 * interactions.
 */
public class DevelopmentHandles {

    public static int boardSize = 4;
    public static int backtrackRow = 1;
    public static Piece p = new Queen();
    public static Material customWhiteFieldMaterial = Material.WHITE_CONCRETE;
    public static Material customBlackFieldMaterial = Material.GRAY_CONCRETE;

    /**
     * Generates a new chess board at the clicked block location.
     * 
     * @param event Triggering event.
     * @param size  Size of the board.
     */
    public static void handleBoardCreation(PlayerInteractEvent event, Integer size) {
        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();

        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) {
            player.sendMessage(
                    Component.text("Du musst einen Block anklicken, an dem das Schachbrett gespawnt werden soll!",
                            NamedTextColor.RED));
            return;
        }

        MChessBoard cb = new MChessBoard(clickedBlock.getLocation(), boardSize, player, customWhiteFieldMaterial,
                customBlackFieldMaterial);
        saveManager.getCbList().add(cb);
    }

    /**
     * Removes the clicked chess board if there is one.
     * 
     * @param event Triggering event.
     */
    public static void removeChessBoardFromGame(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
            event.getPlayer().sendMessage(Component.text("Du musst einen Block des Schachbrettes anklicken, " +
                    "welches gelöscht werden soll!", NamedTextColor.RED));
            return;
        }
        ;
        MChessBoard mcB = getClickedMCB(event);
        mcB.despawnChessBoard();
        saveManager.getCbList().remove(mcB);
        event.setCancelled(true);
    }

    /**
     * Places a queen on the clicked block. Removes the existing queen if already
     * present.
     * 
     * @param event Triggering event.
     */
    public static void placeQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB == null) {
            event.getPlayer().sendMessage(Component.text("Kein gültiges Schachbrett gefunden!", NamedTextColor.RED));
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) {
            event.getPlayer()
                    .sendMessage(Component.text("Bitte klicke auf ein gültiges Schachfeld!", NamedTextColor.RED));
            return;
        }

        Piece existingQueen = mcB.getPieceAt(clickedBlock.getLocation());
        if (existingQueen != null) {
            mcB.removePiece(existingQueen);
        } else {
            mcB.addPiece(clickedBlock.getLocation(), p);
        }
        event.setCancelled(true);
    }

    /**
     * Like <code>placeQueen</code> but with a check, if the queen is allowed on
     * this field of the board.
     * 
     * @param event Triggering event.
     */
    public static void placeTestedQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.addTestedQueen(event.getClickedBlock().getLocation(), p);
        event.setCancelled(true);
    }

    public static void placeUserCarpet(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB == null)
            return;

        mcB.spawnUserCarpet(event.getClickedBlock().getLocation());
        event.setCancelled(true);
    }

    public static void checkUserCarpets(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB == null)
            return;

        Player player = event.getPlayer();
        if (mcB.checkUserCarpets()) {
            player.sendMessage(Component.text("Die Teppiche sind korrekt!", NamedTextColor.GREEN));
        } else {
            player.sendMessage(Component.text("Die Teppiche sind nicht korrekt!", NamedTextColor.RED));
        }
        event.setCancelled(true);
    }

    public static void showMovementSolution(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB == null)
            return;

        mcB.spawnUserCarpetSolution();
        event.setCancelled(true);
    }

    public static void removeAllQueens(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB == null)
            return;
        mcB.removeAllPieces();
        mcB.getPieces().clear();
        event.setCancelled(true);
    }

    /**
     * Initiates the animation for the backtracking algorithm.
     * 
     * @param event The triggering event.
     */
    public static void handleBacktrackAnimation(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);

        if (mcB == null) {
            event.getPlayer().sendMessage(Component.text("Kein gültiges Schachbrett gefunden!", NamedTextColor.RED));
            return;
        }

        if (mcB.isAnimationRunning()) {
            mcB.stopCurrentAnimation();
        } else {
            mcB.animationField2Field(AlgDatDamen.getInstance(), 5, p);
        }
        event.setCancelled(true);
    }

    public static void handleBacktrackAnimationQueenStep(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);

        if (mcB == null) {
            event.getPlayer().sendMessage(Component.text("Kein gültiges Schachbrett gefunden!", NamedTextColor.RED));
            return;
        }

        if (mcB.isAnimationRunning()) {
            mcB.stopCurrentAnimation();
        } else {
            mcB.animationPiece2Piece(AlgDatDamen.getInstance(), 5, p);
        }

        event.setCancelled(true);
    }

    public static void handleBongoSolve(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);

        if (mcB == null) {
            event.getPlayer().sendMessage(Component.text("Kein gültiges Schachbrett gefunden!", NamedTextColor.RED));
            return;
        }

        if (mcB.isAnimationRunning()) {
            mcB.stopCurrentAnimation();
        } else {
            mcB.BongoSolveAnimation(AlgDatDamen.getInstance(), 5, p);
        }

        event.setCancelled(true);
    }

    /**
     * Retrieves the chess board associated with the clicked block, if any.
     * 
     * @param event Triggering event.
     * @return Corresponding chess board or null if not found.
     */
    public static MChessBoard getClickedMCB(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) {
            return null;
        }

        for (MChessBoard mcB : saveManager.getCbList()) {
            if (mcB.isPartOfBoard(clickedBlock.getLocation())) {
                return mcB;
            }
        }
        return null;
    }

    /**
     * Toggles collision carpets for the chess board.
     * 
     * @param event Triggering event.
     */
    public static void handleCollisionCarpets(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        if (mcB == null)
            return;

        if (mcB.isCollisionCarpets()) {
            mcB.despawnCollisionCarpets();
            mcB.setCollisionCarpets(false);
        } else {
            mcB.spawnCollisionCarpets();
            mcB.setCollisionCarpets(true);
        }
        event.setCancelled(true);
    }

    /**
     * Adjusts the size of the chess board cyclically between 4 and 16.
     * 
     * @param event Not used but needed by <code>addMenuItem()</code>
     */
    public static void increaseBoardSize(PlayerInteractEvent event) {
        boardSize = (boardSize < 16) ? boardSize + 1 : 4;
        devMenu.updateItemName(MenuSlots.BOARD_SIZE, "Größe: " + boardSize);
    }

    public static void increaseBacktrackRow(PlayerInteractEvent event) {
        backtrackRow = (backtrackRow < 16) ? backtrackRow + 1 : 1;
        devMenu.updateItemName(MenuSlots.BACKTRACK_ROW, "Backtrack Zeile: " + backtrackRow);
    }

    /**
     * A full run of the algorithm on the given chess board.
     * 
     * @param event The triggering event.
     */
    public static void handleBacktrack(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.animationSolve(p);
        event.setCancelled(true);
    }

    public static void handleBacktrackToRow(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.animationSolveToRow(p, backtrackRow);
        event.setCancelled(true);
    }

    /**
     * Performs the next step of the algorithm.
     * 
     * @param event The triggering event.
     */
    public static void handleBacktrackStep(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());
        mcB.animationStepToNextField(p);
        event.setCancelled(true);
    }

    public static void rotateQueens(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.rotateMPieces(1);
        event.setCancelled(true);
    }

    public static void changeWhiteFieldMaterial(PlayerInteractEvent event) {
        Material clickedBlock = event.getClickedBlock().getType();
        if (!clickedBlock.isAir()) {
            customWhiteFieldMaterial = clickedBlock;
        } else {
            customWhiteFieldMaterial = Material.WHITE_CONCRETE;
        }
        devMenu.updateItemMaterial(MenuSlots.WHITE_FIELD_MATERIAL, customWhiteFieldMaterial);
    }

    public static void changeBlackFieldMaterial(PlayerInteractEvent event) {
        Material clickedBlock = event.getClickedBlock().getType();
        if (!clickedBlock.isAir()) {
            customBlackFieldMaterial = clickedBlock;
        } else {
            customBlackFieldMaterial = Material.BLACK_CONCRETE;
        }
        devMenu.updateItemMaterial(MenuSlots.BLACK_FIELD_MATERIAL, customBlackFieldMaterial);
    }

    public static void changePiece(PlayerInteractEvent event) {
        switch (p.getLetter()){
        case 'Q':
            p = new Superqueen();
            break;
        case 'S':
            p = new Knight();
            break;
        case 'K':
            p = new Queen();
            break;
        default:

            p = new Queen();
            System.out.println("Default");
        break;
    }
    devMenu.updateItemName(MenuSlots.PIECE, ("Figur " + p.getName()));
}

}
