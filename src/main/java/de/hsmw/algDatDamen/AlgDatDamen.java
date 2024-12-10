package de.hsmw.algDatDamen;

import de.hsmw.algDatDamen.menu.Menu;
import de.hsmw.algDatDamen.menu.MenuCommand;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import de.hsmw.algDatDamen.tutorialHandler.TutorialCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import static de.hsmw.algDatDamen.menu.DevelopmentHandles.boardSize;

import java.util.ArrayList;

public final class AlgDatDamen extends JavaPlugin implements Listener {


    private ArrayList<Tutorial> tutorials;
    // private ArrayList<MChessBoard> chessBoards;
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
        // saveManager = new ChessBoardSaveManager();
        tutorials = new ArrayList<Tutorial>();
        // chessBoards = new ArrayList<MChessBoard>();

        // Load saved chess boards
        /*getLogger().info("Loaded " + saveManager.getCbList().size() + " chess boards from file!");
        for (MChessBoard chessBoard : saveManager.getCbList()) {
            chessBoards.add(chessBoard);
            // chessBoard.spawnChessBoard();
            // chessBoard.spawnAllQueens();
            getLogger().info("chessBoards wurde initialisiert");
        }*/

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(devMenu, this);

        // Register commands
        getCommand("schachmenu").setExecutor(new MenuCommand(devMenu));
        getCommand("startTutorial").setExecutor(new TutorialCommand(tutorials));

        devMenu.init(boardSize); // Configure Menus

        getLogger().info("AlgDatDamen Plugin is now active!"); // Log plugin startup
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        // saveManager.saveChessBoards();
        getLogger().info("AlgDatDamen Plugin is now inactive.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        tutorials.forEach((tutorial) -> { if(tutorial.getPlayer().equals(event.getPlayer())) return; });

        // Tutorial erstellen falls Spieler neu ist und zu Start teleportieren
        tutorials.add(new Tutorial(event.getPlayer()));
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, -45, 170));
        event.getPlayer().setFlying(false);
        tutorials.getLast().initialize();
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (itemInHand == null) return;
        Material itemInHandType = itemInHand.getType();

        // Check for Development menu item
        if (itemInHandType == Material.EMERALD && itemInHand.hasItemMeta() &&
                itemInHand.getItemMeta().displayName().equals(Component.text("Developer Menü", NamedTextColor.BLUE))) {
            devMenu.openInventory(player, event);
            event.setCancelled(true);
        } else if(itemInHandType == Material.RED_DYE || itemInHandType == Material.GREEN_DYE || itemInHandType == Material.BLUE_DYE) {
            tutorials.forEach((t) -> {
                if(t.getPlayer().equals(player)) {
                    t.getCurrentLevel().handleEvent(event);
                    event.setCancelled(true);
                    return;
                }
            });
        }
    }

    public static AlgDatDamen getInstance() {
        return instance;
    }
}
