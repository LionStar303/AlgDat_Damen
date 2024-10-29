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
        if (itemInHand != null && ( ( itemInHand.getType() == Material.WHITE_CONCRETE) ||  (itemInHand.getType() ==  Material.GRAY_CONCRETE) )) {
            // Den Block, mit dem interagiert wurde, abrufen
            Block clickedBlock = event.getClickedBlock();
            // Sicherstellen, dass der Block nicht null ist und vom Typ GRASS_BLOCK ist
            if (clickedBlock != null && clickedBlock.getType() == Material.GOLD_BLOCK) {
                Player player = event.getPlayer();
                Inventory inventory = player.getInventory();
                int stackCount = 0;

                // cancel envent to prevent block placing
                event.setCancelled(true);

                for (ItemStack item : inventory.getContents()) {
                    if (item != null && item.getType() == itemInHand.getType()) {
                        stackCount += item.getAmount();
                    }
                }

                if(stackCount > 12){
                    stackCount = 12;
                }
                clickedBlock.setType(itemInHand.getType());
                ChessBoard cb = new ChessBoard(clickedBlock.getLocation(), stackCount);
                cbList.add(cb);
            }
        }
    }
}


