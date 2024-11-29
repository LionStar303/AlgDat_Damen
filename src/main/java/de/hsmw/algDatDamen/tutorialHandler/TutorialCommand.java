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

    ArrayList<Tutorial> tutorials;
    MChessBoard chessBoard;

    public TutorialCommand(AlgDatDamen algDatDamen) {
        this.tutorials = algDatDamen.getTutorials();
        this.chessBoard = algDatDamen.getCBSa();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        if(sender instanceof Player) {
            // neues Tutorial hinzufügen und starten
            tutorials.add(new Tutorial((Player) sender));
            tutorials.getLast().initialize(null);();
            return true;
        } else {
            // Fehlermeldung, falls der Sender kein Spieler ist
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return false;
        }
    }
    
}
