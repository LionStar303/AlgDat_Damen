package de.hsmw.algDatDamen.tutorialHandler;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TutorialCommand implements CommandExecutor {

    ArrayList<Tutorial> allTutorials;

    public TutorialCommand(ArrayList<Tutorial> tutorials) {
        this.allTutorials = tutorials;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        try {
            if (sender instanceof Player) {
                for (Tutorial t : allTutorials) {
                    if (t.getPlayer().equals((Player) sender)) {
                        if (t.getCurrentLevel().isActive()) {
                            t.setProgress(0);
                            t.initialize();
                            t.start();
                        } else {
                            t.start();
                        }
                        return true;
                    }
                    return false;
                }
                return false;
            } else {
                // Fehlermeldung, falls der Sender kein Spieler ist
                sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
