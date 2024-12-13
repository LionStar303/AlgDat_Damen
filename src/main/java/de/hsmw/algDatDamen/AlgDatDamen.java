package de.hsmw.algDatDamen;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.menu.Menu;
import de.hsmw.algDatDamen.menu.MenuCommand;
import de.hsmw.algDatDamen.saveManager.TutorialSaveManager;
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

    /**
     * initialize chessboard list
     * 
     * @deprecated only for testing purposes, use TutorialHandler instead
     */
    public static ArrayList<MChessBoard> chessBoards = new ArrayList<MChessBoard>();

    public static TutorialSaveManager saveManager = new TutorialSaveManager();

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

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(devMenu, this);

        // Register commands
        getCommand("schachmenu").setExecutor(new MenuCommand(devMenu));
        getCommand("startTutorial").setExecutor(new TutorialCommand(saveManager.getTutorialList()));

        devMenu.init(boardSize); // Configure Menus

        getLogger().info("AlgDatDamen Plugin is now active!"); // Log plugin startup
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        saveManager.saveTutorialProgress();

        // remove custom chessboards
        chessBoards.forEach((cb) -> {
            cb.despawnChessBoard();
        });
        getLogger().info("AlgDatDamen Plugin is now inactive.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        saveManager.getTutorialList().forEach((tutorial) -> {
            if (tutorial.getPlayer().equals(event.getPlayer()))
                return;
        });

        // Tutorial erstellen falls Spieler neu ist und zu Start teleportieren
        saveManager.getTutorialList().add(new Tutorial(event.getPlayer(), saveManager.getProgress(event.getPlayer())));
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, -45, 170));
        event.getPlayer().setFlying(false);
        saveManager.getTutorialList().getLast().initialize();
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (itemInHand == null)
            return;
        Material itemInHandType = itemInHand.getType();

        // Check for Development menu item
        if (itemInHandType == Material.EMERALD && itemInHand.hasItemMeta() &&
                itemInHand.getItemMeta().displayName().equals(Component.text("Developer Menü", NamedTextColor.BLUE))) {
            devMenu.openInventory(player, event);
            event.setCancelled(true);
        } else if (itemInHandType == Material.RED_DYE || itemInHandType == Material.GREEN_DYE
                || itemInHandType == Material.BLUE_DYE) {
            saveManager.getTutorialList().forEach((t) -> {
                if (t.getPlayer().equals(player)) {
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
