package de.hsmw.algDatDamen;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static de.hsmw.algDatDamen.DevelopmentHandles.boardSize;

public final class AlgDatDamen extends JavaPlugin implements Listener {

    // List to store all created MChessBoard instances
    public static ChessBoardSaveManager saveManager;
    // Generate development menu
    public static final Menu devMenu = new Menu(27);
    public static AlgDatDamen instance;

    @Override
    public void onEnable() {

        instance = this;

        // Initialize the list of chess boards
        saveManager = new ChessBoardSaveManager();

        // Load saved chess boards
        getLogger().info("Loaded " + saveManager.getCbList().size() + " chess boards from file!");
        for (MChessBoard chessBoard : saveManager.getCbList()) {
            chessBoard.spawnChessBoard();
            chessBoard.spawnAllQueens();
            getLogger().info("Chess board has been spawned!");
        }

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(devMenu, this);

        // Register commands
        getCommand("schachmenu").setExecutor(new MenuCommand(devMenu));

        // Configure Menus
        // - Chess Board Functions
        devMenu.addMenuItem(Material.DIAMOND, "Spawne Schachbrett", MenuSlots.ADD_BOARD, "handleBoardCreation",
                boardSize);
        devMenu.addMenuItem(Material.BARRIER, "Entferne Schachbrett", MenuSlots.REMOVE_BOARD,
                "removeChessBoardFromGame");
        devMenu.addMenuItem(Material.REDSTONE_TORCH, "Größe: " + boardSize, MenuSlots.BOARD_SIZE, "increaseBoardSize");
        devMenu.addMenuItem(Material.RED_CARPET, "Zeige Teppiche", MenuSlots.CARPETS, "handleCollisionCarpets");
        // - Queen Functions
        devMenu.addMenuItem(Material.IRON_HELMET, "Spawne/Entferne Königin", MenuSlots.QUEEN, "placeQueen");
        devMenu.addMenuItem(Material.GOLDEN_HELMET, "Spawne getestete Königin", MenuSlots.TESTED_QUEEN,
                "placeTestedQueen");
        devMenu.addMenuItem(Material.TNT, "Entferne alle Königinnen", MenuSlots.REMOVE_ALL_QUEENS, "removeAllQueens");
        devMenu.addMenuItem(Material.COMPASS, "Rotiere Königinnen", MenuSlots.ROTATE_QUEENS, "rotateQueens");
        // - Backtrack Functions
        devMenu.addMenuItem(Material.DIAMOND_SWORD, "Löse Schachbrett", MenuSlots.BACKTRACK_FULL, "handleBacktrack");
        devMenu.addMenuItem(Material.IRON_SWORD, "Backtracking nächster Schritt", MenuSlots.BACKTRACK_STEP,
                "handleBacktrackStep");
        devMenu.addMenuItem(Material.DIAMOND_AXE, "Backtracking Animation", MenuSlots.BACKTRACK_ANIMATION, "handleBacktrackAnimation");
        devMenu.addMenuItem(Material.GOLDEN_AXE, "Backtracking Animation schnell", MenuSlots.BACKTRACK_ANIMATION_FAST, "handleBacktrackAnimationQueenStep");
        devMenu.addMenuItem(Material.GREEN_CARPET, "Damen Movement Carpets checken", MenuSlots.CHECK_USER_CARPETS, "checkUserCarpets");
        devMenu.addMenuItem(Material.PURPLE_CARPET, "Damen Movement Carpet setzen", MenuSlots.PLACE_USER_CARPET, "placeUserCarpet");    

        devMenu.fillEmptySlots();
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
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (itemInHand == null)
            return;

        // Check for Development menu item
        if (itemInHand.getType() == Material.EMERALD && itemInHand.getItemMeta().displayName()
                .equals(Component.text("Developer Menü", NamedTextColor.BLUE))) {
            devMenu.openInventory(player, event);
            event.setCancelled(true);
        }

    }

    public void BacktrackAnimationStep(MChessBoard board, long ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (board.animationStep()) {
                    Bukkit.getLogger().info("Backtracking abgeschlossen, Scheduler wird beendet.");
                    cancel();
                }

            }
        }.runTaskTimer(this, 0L, ticks);
    }

    public void BacktrackAnimationQueenStep(MChessBoard board, long ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (board.animationQueenStep()) {
                    Bukkit.getLogger().info("Backtracking abgeschlossen, Scheduler wird beendet.");
                    cancel();
                }

            }
        }.runTaskTimer(this, 0L, ticks);
    }
}
