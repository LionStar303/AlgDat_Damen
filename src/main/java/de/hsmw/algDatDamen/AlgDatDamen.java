package de.hsmw.algDatDamen;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.menu.Menu;
import de.hsmw.algDatDamen.menu.MenuCommand;
import de.hsmw.algDatDamen.saveManager.TutorialSaveManager;
import de.hsmw.algDatDamen.tutorialHandler.NPC;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import de.hsmw.algDatDamen.tutorialHandler.TutorialCommand;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
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
    public static final boolean CONSOLE = false;
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
        Location startLocation = new Location(event.getPlayer().getWorld(), 5.5, -45, 176.5, -90f, -5f);
        NPC startMessageNPC = new NPC(startLocation, false);

        // Spieler erst teleportieren, damit er das Audio hört
        player.teleport(startLocation);
        startMessageNPC.playTrack(NPCTrack.NPC_001_INTRO);
        player.setRespawnLocation(startLocation, true);

        player.setInvulnerable(true);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        player.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        player.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        player.getWorld().setDifficulty(Difficulty.PEACEFUL);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setFlying(false);

        spawnStartMessage(player);

        // Tutorial initialisieren falls es schon existiert
        saveManager.getTutorialList().forEach((tutorial) -> {
            if (tutorial.getPlayer().equals(player)) {
                tutorial.initialize();
                return;
            }
        });

        // Tutorial erstellen falls Spieler neu ist
        saveManager.getTutorialList().add(new Tutorial(CONSOLE, event.getPlayer(),
                saveManager.getProgress(event.getPlayer())));
        saveManager.getTutorialList().getLast().initialize();
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

    public void spawnStartMessage(Player player) {
        Location location = new Location(player.getWorld(), -3, -43.5, 166);
        ArrayList<ArmorStand> stands = new ArrayList<ArmorStand>();

        // stands spawnen
        ArmorStand stand1 = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ArmorStand stand2 = (ArmorStand) player.getWorld().spawnEntity(location.clone().add(new Vector(0, -0.5, 0)),
                EntityType.ARMOR_STAND);
        ArmorStand stand3 = (ArmorStand) player.getWorld().spawnEntity(location.clone().add(new Vector(0, -1, 0)),
                EntityType.ARMOR_STAND);
        ArmorStand stand4 = (ArmorStand) player.getWorld().spawnEntity(location.clone().add(new Vector(0, -1.5, 0)),
                EntityType.ARMOR_STAND);

        // Text setzen
        stand1.customName(Component.text("Öffne den Chat mit der Taste t", NamedTextColor.AQUA));
        stand2.customName(Component.text("und starte das Tutorial mit \"/starttutorial\".", NamedTextColor.AQUA));
        stand3.customName(Component.text("Oder benutze ganz einfach den Knopf hinter dir.", NamedTextColor.AQUA));
        stand4.customName(Component.text("Du kannst das Tutorial jederzeit neustarten!", NamedTextColor.AQUA));

        // Standeigenschaften setzen
        stands.add(stand1);
        stands.add(stand2);
        stands.add(stand3);
        stands.add(stand4);
        stands.forEach((stand) -> {
            stand.setAI(false);
            stand.setGravity(false);
            stand.setInvisible(true);
            stand.setCustomNameVisible(true);
        });
    }

    public static AlgDatDamen getInstance() {
        return instance;
    }
}
