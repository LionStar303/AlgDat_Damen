package de.hsmw.algDatDamen;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Class for handling the command '/schachmenu'.
 */
public class MenuCommand implements CommandExecutor {

    private final AlgDatDamen plugin;

    /**
     * Constructor to initialize the command once.
     * 
     * @param plugin The executing plugin object
     */
    public MenuCommand(AlgDatDamen plugin) {
        this.plugin = plugin;
    }

    /**
     * Overridden function which handles the execution of the command.
     * Gets called automatically when triggering the command.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s,
            @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }

        Menu menu = new Menu(plugin);
        menu.openInventory(player);

        return true;
    }
}
