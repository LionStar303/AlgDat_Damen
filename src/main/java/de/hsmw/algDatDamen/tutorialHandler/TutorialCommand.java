package de.hsmw.algDatDamen.tutorialHandler;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;

public class TutorialCommand implements CommandExecutor{

    ArrayList<Tutorial> allTutorials;
    Tutorial tutorial;

    public TutorialCommand(ArrayList<Tutorial> tutorials) {
        this.allTutorials = tutorials;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        if(sender instanceof Player) {
            for(Tutorial t : allTutorials) {
                if(t.getPlayer().equals((Player) sender)) {
                    t.start();
                    sender.sendMessage("Tutorial erfolgreich gestartet.");
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
    }
    
}
