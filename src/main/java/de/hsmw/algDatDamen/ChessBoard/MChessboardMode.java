package de.hsmw.algDatDamen.ChessBoard;

public enum MChessboardMode {

    INACTIVE, // keine Spieler Interaktion
    NORMAL, // normale Spieler Interaktion, alle Damen werden gesetzt
    TESTED, // nur getestete Damen werden gesetzt
    EXPLODING, // wie TESTED, falsche Damen explodieren
    TUTORIAL; // zeigt an, auf welche Felder der Backtracking Algorithmus die nächste Dame stellen würde

}
