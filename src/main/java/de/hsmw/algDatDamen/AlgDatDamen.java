package de.hsmw.algDatDamen;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.menu.Menu;
import de.hsmw.algDatDamen.menu.MenuCommand;
import de.hsmw.algDatDamen.saveManager.TutorialSaveManager;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import de.hsmw.algDatDamen.tutorialHandler.TutorialCommand;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import static de.hsmw.algDatDamen.menu.DevelopmentHandles.boardSize;

import java.util.ArrayList;

public final class AlgDatDamen extends JavaPlugin implements Listener {

    /**
     * initialize chessboard list
     * 
     * @deprecated only for testing purposes, use TutorialHandler instead
     */
    public static ArrayList<MChessBoard> chessBoards = new ArrayList<MChessBoard>();

    // Generate development menu
    public static TutorialSaveManager saveManager = new TutorialSaveManager();
    public static final Menu devMenu = new Menu(54);
    public static AlgDatDamen instance;
    public static Material QUEEN_BLOCK_TOP = Material.EMERALD_BLOCK;
    public static Material QUEEN_BLOCK_BOTTOM = Material.SEA_LANTERN;
    public static Material SUPERQUEEN_BLOCK_TOP = Material.DIAMOND_BLOCK;
    public static Material KNIGHT_BLOCK_TOP = Material.LAPIS_BLOCK;
    public static Material KNIGHT_BLOCK_BOTTOM = Material.SHROOMLIGHT;
    public static final boolean CONSOLE = true;
    private static int MIN_HEIGHT = -60;

    @Override
    public void onEnable() {
        instance = this;

        // Register event listeners for player interactions
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(devMenu, this);
        getServer().getPluginManager().registerEvents(new BlockInteractions(), this);

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
        Player player = event.getPlayer();

        saveManager.getTutorialList().forEach((tutorial) -> {
            if (tutorial.getPlayer().equals(player))
                /*
                 * TODO handling wenn der Spieler den Server verlässt und neu betritt
                 * beim Verlassen muss das aktuelle Level "gestoppt" werden,
                 * je nachdem wie viele Probleme das bereitet mittem im Level aufzuhören
                 * beim Einloggen muss wieder an der gleichen Stelle weitergemacht werden
                 * muss beim Testen ermittelt werden welche Sonderfälle beachtet werden müssen
                 */
                return;
        });

        // Tutorial erstellen falls Spieler neu ist und zu Start teleportieren
        // saveManager.getTutorialList().add(new Tutorial(CONSOLE, event.getPlayer(),
        // saveManager.getProgress(event.getPlayer())));
        saveManager.getTutorialList().add(new Tutorial(CONSOLE, event.getPlayer(), 0)); // <- nur zum testen
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 0, -45, 170));
        event.getPlayer().setFlying(false);
        saveManager.getTutorialList().getLast().initialize();

        player.setInvulnerable(true);
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    /**
     * Handles teleporting the player back to the start point if he falls off the
     * island.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (location.getY() <= MIN_HEIGHT) {
            for (Tutorial t : saveManager.getTutorialList()) {
                if (t.getPlayer().equals(player)) {
                    player.teleport(t.getCurrentLevel().getStartLocation());
                    return;
                }
            }
            player.teleport(player.getRespawnLocation());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        for (Tutorial t : saveManager.getTutorialList()) {
            if (t.getPlayer().equals(player)) {
                t.getCurrentLevel().handleChatEvent(event);
                return;
            }
        }
    }

    public static AlgDatDamen getInstance() {
        return instance;
    }
}
