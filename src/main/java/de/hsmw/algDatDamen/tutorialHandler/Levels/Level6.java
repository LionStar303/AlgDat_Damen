package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

public class Level6 extends Level {

    private final static String LEVEL_NAME = "Level 5 - Amazonas";
    private final static String LEVEL_DESCRIPTION = "Lösung auf 4x4-Schachbrett mit normalen und invertierten Hilfestellungen";

    public Level6(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -153, -19, 45, 140, -10), new Location(player.getWorld(), -188, -20, -7), false,  parent);
    }

    public Level6(boolean console, Player player, Location startLocation, Location teleporterLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void configureChessBoards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'configureChessBoards'");
    }

    @Override
    protected void initializeSteps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeSteps'");
    }

}
