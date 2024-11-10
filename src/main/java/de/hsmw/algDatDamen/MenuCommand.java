package de.hsmw.algDatDamen;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Class for handling the command '/schachmenu'.
 */
public class MenuCommand implements CommandExecutor {

    final Menu menu;

    /**
     * Constructor to initialize the command once.
     * 
     * @param menu The executing plugin object
     */
    public MenuCommand(Menu menu) {
        this.menu = menu;
    }

    /**
     * Overridden function which handles the execution of the command.
     * Gets called automatically when triggering the command.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Prüfen, ob der CommandSender ein Spieler ist
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Erstellen des Emerald-Items
            ItemStack emerald = new ItemStack(Material.EMERALD);
            ItemMeta meta = emerald.getItemMeta();

            if (meta != null) {
                // Setzen des Namens und der Verzauberung
                meta.displayName(Component.text(NamedTextColor.BLUE + "Developer Menü"));
                meta.addEnchant(Enchantment.FORTUNE, 1, true);  // Verzauberung hinzufügen
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);   // Versteckt das Verzauberungs-Glitzern

                emerald.setItemMeta(meta);
            }

            // Hinzufügen des Emeralds zum Inventar des Spielers
            player.getInventory().addItem(emerald);
            player.sendMessage(NamedTextColor.GREEN + "Du hast das Developer Menü erhalten!");

            return true;

        } else {
            // Fehlermeldung, falls der Sender kein Spieler ist
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return false;
        }

    }
}
