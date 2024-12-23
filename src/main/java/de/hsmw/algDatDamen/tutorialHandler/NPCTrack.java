package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Sound;

public enum NPCTrack {

    // Level 1
    NPC_101_EXPLAIN_CHESSBOARD(Sound.ENTITY_ARMADILLO_BRUSH ,"Aber was soll das heißen fragst du? Nun gut zuerst, hier ist ein Schachbrett. Wir betrachten das Problem in unterschiedlichen Variablen. Das hier ist ein 8*8 Schachbrett, es ist unterteilt in bikolorierten Quadraten auf denen sich pro Quadrat nur eine Figur befinden kann."),
    NPC_102_EXPLAIN_QUEEN(Sound.ENTITY_ARMADILLO_DEATH ,"Im Schach gibt es sechs verschiedene Arten von Figuren. Aber heute sollen uns zwei reichen. Diese Figur nennt man Dame."),
    NPC_103_EXPLAIN_MOVEMENT(Sound.ENTITY_ARMADILLO_EAT ,"Die Dame kann sich beliebig weit in alle Diagonale und Graden bewegen."),
    NPC_104_EXPLAIN_THREATS(Sound.ENTITY_ARMADILLO_HURT ,"Wenn sich eine andere Figur im Bewegungsbereich befindet, ist sie bedroht und kann von der sich bewegenden Figur geschlagen werden."),

    // Level 2
    NPC_201_INTRO(Sound.ENTITY_ARMADILLO_HURT_REDUCED ,"Jetzt will ich dir zeigen wie ich ein 8x8 Feld fülle ohne das sich eine der Damen gegenseitig bedroht."),
    NPC_202_EXPLAIN_THREATS_1(Sound.ENTITY_ARMADILLO_LAND ,"Denn, wie du sehen kannst, wenn ich die Dame so platziere bedroht sie folgende Felder."),
    NPC_203_EXPLAIN_THREATS_2(Sound.ENTITY_ARMADILLO_PEEK ,"Und würde ich jetzt eine weitere Dame platzieren. Ungefähr so…"),
    NPC_204_EXPLAIN_PROBLEM(Sound.ENTITY_ARMADILLO_ROLL ,"Dann würde meine erste Dame die zweite schlagen können und das wollen wir hier vermeiden. Also entfernen wir die zweite Dame wieder und setzen sie neu."),
    NPC_205_SOLVE(Sound.ENTITY_ARMADILLO_SCUTE_DROP ,"Nun bedrohen sich die beiden Damen nicht mehr. Aber so ist das Brett natürlich noch nicht gefüllt. Lass mich die restlichen Damen auch noch platzieren."),
    NPC_206_EXPLAIN_SOLUTION(Sound.ENTITY_ARMADILLO_UNROLL_FINISH ,"Wenn du jetzt selbst einen Blick auf das Feld wirfst solltest du feststellen können das wir keine weitere Dame mehr platzieren können, ohne das sie bedroht wird, aber auch kein unbedrohtes Feld übrig bleibt.\r\n" +
                "Hierbei handelt es sich um das sogenannte N-Damen-Problem. Eine Herausforderung, bei der N Damen auf einem N x N Schachbrett platziert werden müssen, ohne dass sich zwei Damen gegenseitig angreifen.\r\n" +
                "Und wie du auch gesehen hast haben wir das Problem soeben gelöst.\r\n" +
                "Also lass mich nun dazu über gehen wie das passiert ist.\r\n"),
    NPC_207_EXPLAIN_3X3_1(Sound.ENTITY_ARMADILLO_STEP ,"Hier ist ein weitaus kleineres Feld, aber leider ist dieses Feld zu klein als das man hier ein Lösung finden könnte."),
    NPC_208_EXPLAIN_3X3_2(Sound.ENTITY_ARMADILLO_UNROLL_START ,"Egal wie ich diese drei Damen platziere, es würden sich immer mindestens zwei gegenseitig schlagen.\r\n" + //
                "Denn das N-Damen Problem kann man erst ab einer Größe von 4x4 lösen.\r\n"),
    NPC_206_EXPLAIN_4x4_SOLUTION(Sound.ENTITY_AXOLOTL_ATTACK ,"Ab 4x4 kann ich allerdings wieder allen Damen einen Platz zuweisen ohne das sie sich gegenseitig bedrohen."),

    // Level 3
    NPC_301_INTRO(Sound.ENTITY_AXOLOTL_HURT ,"Aber jetzt sollst du mal dich versuchen. Hier ist ein 4x4 Brett. Setzte vier Damen so, dass keine eine andere bedroht."),
    NPC_302_DIFFERENT_SOLUTIONS(Sound.ENTITY_AXOLOTL_IDLE_AIR ,"Aber es hätte auch eine andere Lösung gegeben. Bestimme wie die Lösungen auf diesen beiden Brettern sich unterscheiden."),
    NPC_303_SECOND_TASK(Sound.ENTITY_AXOLOTL_IDLE_WATER ,"Jetzt wirst du erneut versuchen, vier Damen zu platzieren, aber diesmal ohne bedrohte Felder. Achte wieder darauf, dass du die Damen so setzt, dass sie sich nicht gegenseitig schlagen.")

    // Level 4
    NPC_401_INTRO(Sound.ENTITY_AXOLOTL_SPLASH ,"");

    private Sound sound;
    private String text;

    private NPCTrack(Sound sound, String text) {
        this.sound = sound;
        this.text = text;
    }

    public Sound getSound() { return this.sound; }
    public String getText() { return this.text; }
}
