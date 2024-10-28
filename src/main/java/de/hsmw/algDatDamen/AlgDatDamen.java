package de.hsmw.algDatDamen;


import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public final class AlgDatDamen extends JavaPlugin implements Listener{

    public ArrayList<ChessBoard> cbList;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Moin!");

        // Initialisiere Die Schachfelder
        this.cbList = new ArrayList<>();

        // Create a chessboard with size n x n
        int boardSize = 8; // You can change this to any size
        ChessBoard board = new ChessBoard(boardSize);

        // default true but --> activates the Console Messages
        board.setConsole(true);

        // Place queens on all fields of the board
        board.placeAllQueens();

        // Print the final layout of the chessboard
        board.printBoard();

        // Test the Backtracking
        board.playBacktrk();

        // Print the final layout of the chessboard
        board.printBoard();

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BlockChangePlugin aktiviert!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        // Prüfen, ob der Spieler ein Item in der Hand hat und ob es ein Stick ist
        ItemStack itemInHand = event.getItem();
        if (itemInHand != null && itemInHand.getType() == Material.STICK) {
            // Den Block, mit dem interagiert wurde, abrufen
            Block clickedBlock = event.getClickedBlock();

            // Sicherstellen, dass der Block nicht null ist und vom Typ GRASS_BLOCK ist
            if (clickedBlock != null && clickedBlock.getType() == Material.GRASS_BLOCK) {
                // Blocktyp in GOLD_BLOCK ändern
                clickedBlock.setType(Material.GOLD_BLOCK);
                ChessBoard cb = new ChessBoard(clickedBlock, 8);
                cbList.add(cb);

            }
        }
    }
}


