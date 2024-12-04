package de.hsmw.algDatDamen;

import de.hsmw.algDatDamen.menu.Menu;
import de.hsmw.algDatDamen.ChessBoard.*;
import de.hsmw.algDatDamen.menu.MenuCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static de.hsmw.algDatDamen.menu.DevelopmentHandles.boardSize;

public final class AlgDatDamen extends JavaPlugin implements Listener {

    // List to store all created MChessBoard instances
    public static ChessBoardSaveManager saveManager;
    // Generate development menu
    public static final Menu devMenu = new Menu(27);
    public static AlgDatDamen instance;
    public static Material QUEEN_BLOCK_TOP = Material.EMERALD_BLOCK;
    public static Material QUEEN_BLOCK_BOTTOM = Material.SEA_LANTERN;
    public static Material SUPERQUEEN_BLOCK_TOP = Material.DIAMOND_BLOCK;
    public static Material KNIGHT_BLOCK_TOP = Material.LAPIS_BLOCK;
    public static Material KNIGHT_BLOCK_BOTTOM = Material.IRON_BLOCK;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize the list of chess boards
        saveManager = new ChessBoardSaveManager();

        // Load saved chess boards
        getLogger().info("Loaded " + saveManager.getCbList().size() + " chess boards from file!");
        for (MChessBoard chessBoard : saveManager.getCbList()) {
            chessBoard.spawnChessBoard();
            // chessBoard.spawnAllQueens();
            getLogger().info("Chess board has been spawned!");
        }

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(devMenu, this);

        // Register commands
        getCommand("schachmenu").setExecutor(new MenuCommand(devMenu));

        devMenu.init(boardSize); // Configure Menus

        getLogger().info("AlgDatDamen Plugin is now active!"); // Log plugin startup
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        saveManager.saveChessBoards();
        getLogger().info("AlgDatDamen Plugin is now inactive.");
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (itemInHand == null)
            return;

        // Check for Development menu item
        if (itemInHand.getType() == Material.EMERALD && itemInHand.hasItemMeta() &&
                itemInHand.getItemMeta().displayName().equals(Component.text("Developer Menü", NamedTextColor.BLUE))) {
            devMenu.openInventory(player, event);
            event.setCancelled(true);
        }
    }

    public static AlgDatDamen getInstance() {
        return instance;
    }
}
