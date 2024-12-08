package de.hsmw.algDatDamen.tutorialHandler.Levels.level1;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.Levels.level1.steps.Step1;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Level1 extends Level{

    private final static String DESCRIPTION = "Erklärung des Aufbaus eines Schachbretts, sowie der Damenfigur und deren Bewegungsmuster";
    
    public Level1(Player player) {
        // Schachbrett für Level 1 erstellen
        // TODO location festlegen
        this(new MChessBoard(new Location(player.getWorld(), -40, -44, 128), 8, player), player);
    }

    public Level1(MChessBoard chessBoard, Player player) {
        this(chessBoard, player, new Location(player.getWorld(), -30, -43, 143));
    }

    public Level1(MChessBoard chessBoard, Player player, Location startLocation) {
        this(chessBoard, player, startLocation, false);
    }

    public Level1(MChessBoard chessBoard, Player player, Location startLocation, boolean completed) {
        // ruft den Konstruktor der Elternklasse Level auf
        super("Level 1 - Einführung", DESCRIPTION, chessBoard, player, startLocation, completed);
    }

    protected void setInventory() {
        player.getInventory().clear();
        // Erstellen des Emerald-Items
        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta meta = emerald.getItemMeta();

        if (meta != null) {
            // Setzen des Namens und der Verzauberung
            meta.displayName(Component.text("Developer Menü", NamedTextColor.BLUE));
            meta.addEnchant(Enchantment.FORTUNE, 1, true);  // Verzauberung hinzufügen
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);   // Versteckt das Verzauberungs-Glitzern

            emerald.setItemMeta(meta);
        }

        player.getInventory().setItem(4, emerald);
    }

    @Override
    public void start() {
        super.start();
        steps[0] = new Step1(null);
        // TODO Steps initialisieren
        
        // TODO Erklärung des Schachbretts durch NPC
        // Setzen einer Dame auf Schachbrett durch Computer
        // Erklärung der Dame durch NPC
        // Anzeigen der Bewegungsmuster der Dame
        // Erklärung der Bewegungsmuster durch NPC
        // Setzen einer weiteren Dame durch Computer
        // Anzeigen der Bedrohungen der Damen
        // Erklärung der Bedrohungen durch NPC
        // Löschen aller Damen von Schachbrett
    }  
}
