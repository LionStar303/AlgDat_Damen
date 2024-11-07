package de.hsmw.algDatDamen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class AlgDatDamen extends JavaPlugin implements Listener {

    // List to store all created ChessBoard instances
    private List<MChessBoard> cbList;

    @Override
    public void onEnable() {
        // Initialize the chessboard list
        cbList = new ArrayList<MChessBoard>();

        // Register the event listeners
        getServer().getPluginManager().registerEvents(this, this);

        // Log plugin startup
        getLogger().info("AlgDatDamen Plugin is now active!");
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        getLogger().info("AlgDatDamen Plugin is now inactive.");
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getItem();
        if (itemInHand == null) return;

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
            cbList.add(cb);

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
        } else if (itemInHand.getItemMeta() != null && "TestedQueen".equals(itemInHand.getItemMeta().getDisplayName())) {
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
        }

        mcB.addQueen(event.getClickedBlock().getLocation());
        getLogger().info("Queen has been successfully placed and spawned on the board!");
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
            mcB.spawnCollisionCarpets();
            mcB.setCollisionCarpets(false);
        } else {
            mcB.cleanCollisionCarpets();
            mcB.setCollisionCarpets(true);
        }
        event.setCancelled(true);
    }

    private MChessBoard getClickedMCB(PlayerInteractEvent event){
        for (MChessBoard mcB : cbList) {
            if (mcB.isPartOfBoard(event.getClickedBlock().getLocation())) {
              return mcB;
            }
        }
        return null;
    }
}


