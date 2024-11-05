package de.hsmw.algDatDamen;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class AlgDatDamen extends JavaPlugin implements Listener {

    // List to store all created ChessBoard instances
    private ChessBoardSaveManager saveManager;

    @Override
    public void onEnable() {
        // Initial startup logic for the plugin
        System.out.println("Plugin Started!");

        // Initialize the list of chess boards
        this.saveManager = new ChessBoardSaveManager();

        // Load saved chess boards
        getLogger().info("Loaded " + saveManager.getCbList().size() + " chess boards from file!");
        for (ChessBoard chessBoard : saveManager.getCbList()) {
            chessBoard.spawnCB();
            chessBoard.spawnAllQueens();
            getLogger().info("Chess board has been spawned!");
        }

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BlockChangePlugin is now active!");
    }

    @Override
    public void onDisable() {
        // Logic to execute when the plugin is disabled
        saveManager.saveChessBoards();
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {

        // Handle spawning of a ChessBoard instance
        ItemStack itemInHand = event.getItem();
        if (itemInHand != null &&
                ((itemInHand.getType() == Material.WHITE_CONCRETE) || (itemInHand.getType() == Material.GRAY_CONCRETE))) {

            // Retrieve the block the player interacted with
            Block clickedBlock = event.getClickedBlock();
            // Check that the block is not null and is a GOLD_BLOCK
            if (clickedBlock != null && clickedBlock.getType() == Material.GOLD_BLOCK) {
                Player player = event.getPlayer();
                Inventory inventory = player.getInventory();
                int stackCount = 0;

                // Prevent the block placement action
                event.setCancelled(true);

                // Calculate the total number of items in the player's hand
                for (ItemStack item : inventory.getContents()) {
                    if (item != null && item.getType() == itemInHand.getType()) {
                        stackCount += item.getAmount();
                    }
                }

                // Limit the stack count to a maximum of 12
                if (stackCount > 12) {
                    stackCount = 12;
                }

                // Set the clicked block to the type held in hand
                clickedBlock.setType(itemInHand.getType());

                // Create a new ChessBoard at the clicked block's location with the stack count
                ChessBoard cb = new ChessBoard(clickedBlock.getLocation(), stackCount, player);
                saveManager.getCbList().add(cb);
            }
        }

        if (itemInHand != null && itemInHand.getType() == Material.STICK &&
                itemInHand.getItemMeta() != null &&
                "Queen".equals(itemInHand.getItemMeta().getDisplayName())) {

            // Loop through all chessboards to find if the clicked block is part of any board
            for (ChessBoard chessBoard : saveManager.getCbList()) {
                if (chessBoard.isPartOfBoard(event.getClickedBlock().getLocation())) {
                    // Remove existing queen if there is one on the clicked location
                    Queen existingQueen = chessBoard.getQueenAt(event.getClickedBlock().getLocation());
                    if (existingQueen != null) {
                        chessBoard.removeQueen(existingQueen); // Remove the existing queen
                        getLogger().info("Existing queen has been removed from the board!");
                        event.setCancelled(true); // Cancel the default action to avoid conflicts
                        break;
                    }

                    // Add the new queen to the clicked location
                    chessBoard.addQueen(event.getClickedBlock().getLocation());
                    getLogger().info("Queen has been successfully placed and spawned on the board!");
                    event.setCancelled(true); // Cancel the default action to avoid conflicts
                    break;
                }
            }
        }


        if (itemInHand != null && itemInHand.getType() == Material.STICK &&
                itemInHand.getItemMeta() != null &&
                "TestedQueen".equals(itemInHand.getItemMeta().getDisplayName())) {

            // Loop through all chessboards to find if clicked block is part of any board
            for (ChessBoard chessBoard : saveManager.getCbList()) {
                if (chessBoard.isPartOfBoard(event.getClickedBlock().getLocation())) {
                    chessBoard.addTestedQueen(event.getClickedBlock().getLocation());
                    getLogger().info("Queen has been successfully placed and spawned on the board!");
                    event.setCancelled(true); // Cancel the default action to avoid conflicts
                    break;
                }
            }
        }

        if (itemInHand != null && itemInHand.getType() == Material.IRON_SWORD) {
            for (ChessBoard chessBoard : saveManager.getCbList()) {
                if (chessBoard.isPartOfBoard(event.getClickedBlock().getLocation())) {
                    chessBoard.playBacktrack();
                    for(Queen q : chessBoard.getQueens()){
                        chessBoard.spawnQueen(q);
                    }
                    event.setCancelled(true); // Cancel the default action to avoid conflicts
                    break;
                }
            }
        }

        if (itemInHand != null && itemInHand.getType() == Material.WOODEN_SWORD) {
            for (ChessBoard chessBoard : saveManager.getCbList()) {
                if (chessBoard.isPartOfBoard(event.getClickedBlock().getLocation())) {
                    chessBoard.spawnCollisionCarpets();
                    event.setCancelled(true); // Cancel the default action to avoid conflicts
                    break;
                }
            }
        }


    }
}




