package de.hsmw.algDatDamen;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import static de.hsmw.algDatDamen.AlgDatDamen.saveManager;

/**
 * Class which contains the functions/handles used to develop the tutorial.
 */
public class DevelopmentHandles {

    public static void handleBoardCreation(PlayerInteractEvent event, int size) {
        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();

        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) {
            player.sendMessage(Component.text("Du musst einen Block anklicken, an dem das " +
                    "Schachbrett gespawnt werden soll!", NamedTextColor.RED));
            return;
        };

        MChessBoard cb = new MChessBoard(clickedBlock.getLocation(), size, player);
        saveManager.getCbList().add(cb);
    }

    public static void handleQueenPlacement(PlayerInteractEvent event) {
        placeQueen(event);
    }

    public static void handleTestedQueenPlacement(PlayerInteractEvent event) {
        placeTestedQueen(event);
    }

    private static void placeQueen(PlayerInteractEvent event) {
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

    private static void placeTestedQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.addTestedQueen(event.getClickedBlock().getLocation());
        //getLogger().info("TestedQueen has been successfully placed on the board!");
        event.setCancelled(true);
    }

    public static MChessBoard getClickedMCB(PlayerInteractEvent event) {
        for (MChessBoard mcB : saveManager.getCbList()) {
            if (mcB.isPartOfBoard(event.getClickedBlock().getLocation())) {
                return mcB;
            }
        }
        return null;
    }

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

}
