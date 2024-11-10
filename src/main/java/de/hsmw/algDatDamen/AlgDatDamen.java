package de.hsmw.algDatDamen;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

    // List to store all created MChessBoard instances
    private ChessBoardSaveManager saveManager;
    private Menu menu = new Menu();

    @Override
    public void onEnable() {

        // Initialize the list of chess boards
        this.saveManager = new ChessBoardSaveManager();

        // Load saved chess boards
        getLogger().info("Loaded " + saveManager.getCbList().size() + " chess boards from file!");
        for (MChessBoard chessBoard : saveManager.getCbList()) {
            chessBoard.spawnChessBoard();
            chessBoard.spawnAllQueens();
            getLogger().info("Chess board has been spawned!");
        }

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Menu(), this);

        // Register commands
        getCommand("schachmenu").setExecutor(new MenuCommand(menu));

        // Log plugin startup
        getLogger().info("AlgDatDamen Plugin is now active!");
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        saveManager.saveChessBoards();
        getLogger().info("AlgDatDamen Plugin is now inactive.");
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
            return;
        }

        ItemStack itemInHand = event.getItem();
        if (itemInHand == null)
            return;

        // Handle interaction based on item type
        if (itemInHand.getType() == Material.WHITE_CONCRETE || itemInHand.getType() == Material.GRAY_CONCRETE) {
            handleBoardCreation(event, itemInHand);
        } else if (itemInHand.getType() == Material.STICK) {
            handleQueenPlacement(event, itemInHand);
        } else if (itemInHand.getType() == Material.IRON_SWORD) {
            handleBacktrack(event);
        } else if (itemInHand.getType() == Material.GOLD_BLOCK) {
            handleBacktrackStep(event);
        } else if (itemInHand.getType() == Material.WOODEN_SWORD) {
            handleCollisionCarpets(event);
        } else if (itemInHand.getType() == Material.ACACIA_LOG) {
            removeChessBoardFromGame(event);
        }
    }

    private void handleBoardCreation(PlayerInteractEvent event, ItemStack itemInHand) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock != null && clickedBlock.getType() == Material.GOLD_BLOCK) {
            Player player = event.getPlayer();
            Inventory inventory = player.getInventory();
            int stackCount = countItemsInInventory(inventory, itemInHand);

            // Prevent placement of more than 12 items
            stackCount = Math.min(stackCount, 12);
            clickedBlock.setType(itemInHand.getType());

            // Create and add new ChessBoard
            MChessBoard cb = new MChessBoard(clickedBlock.getLocation(), stackCount, player);
            saveManager.getCbList().add(cb);

            event.setCancelled(true);
        }
    }

    private int countItemsInInventory(Inventory inventory, ItemStack itemInHand) {
        int stackCount = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == itemInHand.getType()) {
                stackCount += item.getAmount();
            }
        }
        return stackCount;
    }

    private void handleQueenPlacement(PlayerInteractEvent event, ItemStack itemInHand) {
        if (itemInHand.getItemMeta() != null && "Queen".equals(itemInHand.getItemMeta().getDisplayName())) {
            placeQueen(event);
        } else if (itemInHand.getItemMeta() != null
                && "TestedQueen".equals(itemInHand.getItemMeta().getDisplayName())) {
            placeTestedQueen(event);
        }
    }

    private void placeQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());

        Queen existingQueen = mcB.getQueenAt(event.getClickedBlock().getLocation());
        if (existingQueen != null) {
            mcB.removeQueen(existingQueen); // Remove the existing queen
            getLogger().info("Existing queen has been removed from the board!");
            event.setCancelled(true);
        } else {
            mcB.addQueen(event.getClickedBlock().getLocation());
            getLogger().info("Queen has been successfully placed and spawned on the board!");
        }
        event.setCancelled(true);

    }

    private void placeTestedQueen(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.addTestedQueen(event.getClickedBlock().getLocation());
        getLogger().info("TestedQueen has been successfully placed on the board!");
        event.setCancelled(true);

    }

    private void handleBacktrack(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());
        mcB.playBacktrack();
        mcB.spawnAllQueens();
        event.setCancelled(true);

    }

    private void handleBacktrackStep(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        System.out.println(mcB.toString());
        mcB.mstep();
        event.setCancelled(true);

    }

    private void handleCollisionCarpets(PlayerInteractEvent event) {
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

    private void removeChessBoardFromGame(PlayerInteractEvent event) {
        MChessBoard mcB = getClickedMCB(event);
        mcB.removeChessBoardFromGame();
        saveManager.getCbList().remove(mcB);
        event.setCancelled(true);
    }

    private MChessBoard getClickedMCB(PlayerInteractEvent event) {
        for (MChessBoard mcB : saveManager.getCbList()) {
            if (mcB.isPartOfBoard(event.getClickedBlock().getLocation())) {
                return mcB;
            }
        }
        return null;
    }

    public static void testMenuCommand(String message) {
        Bukkit.broadcast(Component.text(message));
    }
}
