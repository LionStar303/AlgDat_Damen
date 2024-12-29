package de.hsmw.algDatDamen.tutorialHandler;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TutorialCommand implements CommandExecutor{

    ArrayList<Tutorial> allTutorials;
    Tutorial tutorial;
    ArrayList<Player> players;  // List of players already executed the command successfully

    public TutorialCommand(ArrayList<Tutorial> tutorials) {
        this.allTutorials = tutorials;
        players = new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        try {
            if(sender instanceof Player) {
                if (players.contains((Player) sender)) {
                    sender.sendMessage("Du hast das Tutorial bereits gestartet.");
                    return false;
                }
                for(Tutorial t : allTutorials) {
                    if(t.getPlayer().equals((Player) sender)) {
                        t.start();
                        sender.sendMessage("Tutorial erfolgreich gestartet.");
                        players.add((Player) sender);
                        return true;
                    }
                    sender.sendMessage("Tutorial existiert nicht.");
                    return false;
                }
                sender.sendMessage("keine Tutorials vorhanden.");
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
