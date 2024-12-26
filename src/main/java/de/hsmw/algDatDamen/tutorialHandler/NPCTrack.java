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
    NPC_209_EXPLAIN_4x4_SOLUTION(Sound.ENTITY_AXOLOTL_ATTACK ,"Ab 4x4 kann ich allerdings wieder allen Damen einen Platz zuweisen ohne das sie sich gegenseitig bedrohen."),
    NPC_210_SUMMARY(Sound.ENTITY_AXOLOTL_DEATH ,"Also fassen wir zusammen.\r\n"+
                 "Auf einem Schachbrett der Größe 3x3 gibt es keine gültige Lösung, erst ab einer Größe von 4x4 ist eine zu finden."),

    // Level 3
    NPC_301_INTRO(Sound.ENTITY_AXOLOTL_HURT ,"Aber jetzt sollst du mal dich versuchen. Hier ist ein 4x4 Brett. Setzte vier Damen so, dass keine eine andere bedroht."),
    NPC_302_DIFFERENT_SOLUTIONS(Sound.ENTITY_AXOLOTL_IDLE_AIR ,"Aber es hätte auch eine andere Lösung gegeben. Bestimme wie die Lösungen auf diesen beiden Brettern sich unterscheiden."),
    NPC_303_SECOND_TASK(Sound.ENTITY_AXOLOTL_IDLE_WATER ,"Jetzt wirst du erneut versuchen, vier Damen zu platzieren, aber diesmal ohne bedrohte Felder. Achte wieder darauf, dass du die Damen so setzt, dass sie sich nicht gegenseitig schlagen."),

    // Level 4
    // TODO falsch benannt
    NPC_401_INTRO(Sound.ENTITY_PIG_AMBIENT ,"Lass uns das ganze jetzt wieder auf einem acht mal acht Feld betrachten und den Backtracking-Algoritmus verwenden."),
    NPC_402_EXPLAIN_PROBLEM(Sound.ENTITY_AXOLOTL_SPLASH ,"Wie du sehen kannst, ist es nicht so einfach, alle Damen auf einem 8x8 Schachbrett zu platzieren.\r\n"+ 
                            "Manchmal führt eine falsche Platzierung dazu, dass wir nicht weiterkommen.\r\n"+ 
                            "Das ist der Punkt, an dem der Backtracking-Algorithmus ins Spiel kommt."),
    NPC_403_EXPLAIN_BACKTRACKING_1(Sound.ENTITY_AXOLOTL_SWIM ,"Der Backtracking-Algorithmus ist eine Methode, um Lösungen für Probleme zu finden, indem man systematisch alle möglichen Optionen ausprobiert und zurückgeht, wenn man auf eine Sackgasse stößt.\r\n" + 
                            "Lass mich dir zeigen, wie das funktioniert."),
    @Deprecated
    NPC_404_STEP_BY_STEP(Sound.ENTITY_BAT_AMBIENT ,"Lass uns den Algorithmus nun Schritt für Schritt durchlaufen."),
    NPC_405_EXPLAIN_BACKTRACKING_2(Sound.ENTITY_BAT_DEATH ,"Jedes mal wenn eine Dame platziert wird, prüfen wir ob diese Position es erlaubt eine weiter zu stellen, ansonsten nehmen wir die Dame wieder vom Brett und versuchen sie an einer anderen Stelle zu platzieren, wo wir dann wieder prüfen, ob sie gültig ist."),

    // Level 5
    // TODO für sechs Damen anpassen
    NPC_501_INTRO(Sound.ENTITY_BAT_HURT ,"Prüfe nun wieder selbst, ob du den Algorithmus anwenden kannst.\r\n" +
                            "Setze die sechs Damen wieder auf dem Brett nach den bekannten Vorgaben, aber nutze diesmal dein Wissen über den Backtracking-Algorithmus."),
    NPC_502_EXPLAIN_1(Sound.ENTITY_BAT_LOOP ,"Schaffst du es auch, wenn wir die Farben tauschen?"),
    
    // Level 6
    // TODO Intro für Level 6
    NPC_601_INTRO(Sound.ENTITY_BAT_TAKEOFF ,"Das ging ja schneller als gedacht, hier darfst du dich an einem richtigen Schachbrett versuchen."),
    // TODO NPC_602_HELP und NPC_603_EXPLAIN_2 zusammenfassen (ENTITY_BAT_TAKEOFF wird für Intro verwendet)
    // NPC_602_HELP(Sound.ENTITY_BAT_TAKEOFF ,""),
    NPC_602_EXPLAIN_2(Sound.ENTITY_BEE_DEATH ,"Versuch jetzt das Brett zu vervollständigen. Aber keine Panik (diesbezüglich), ich gebe dir etwas Hilfestellung."),
    // TODO abgeänderter Text wenn genug Zeit ist
    NPC_603_EXPLAIN_3(Sound.ENTITY_BEE_HURT ,"Jetzt musst du noch den Anfang versuchen, setze nur die ersten drei Damen."),
    @Deprecated // die fliegt raus
    NPC_605_EXPLAIN_4(Sound.ENTITY_BEE_LOOP ,"Hier sind zwei 8x8 Felder, eins ist gelöst, das andere nicht. Welches ist welches?"),

    // Level 7
    NPC_701_INTRO(Sound.ENTITY_BEE_LOOP_AGGRESSIVE ,"Jetzt werden wir das N-Damen-Problem auf einem noch größeren Schachbrett betrachten. Du wirst sehen, wie sich die Herausforderungen mit der Größe des Schachbretts ändern."),
    NPC_606_EXPLAIN(Sound.ENTITY_BEE_POLLINATE ,"Aber fühl dich nicht allzu eingeschüchtert. Du musst nur sechs der zehn Damen setzen."),
    // TODO NPC_701_OLD mit NPC_702_KNIGHT_1 zusammenfassen
    NPC_701_OLD(Sound.ENTITY_BEE_STING ,"Kommen wir nun zur zweiten Figur, die wir hier nutzen wollen."),
    NPC_702_KNIGHT_1(Sound.ENTITY_BLAZE_AMBIENT ,"Der Springer bewegt sich in einem L-förmigen Muster. Er kann zwei Felder in eine Richtung und dann ein Feld in eine senkrechte Richtung springen oder umgekehrt."),
    NPC_703_KNIGHT_2(Sound.ENTITY_BLAZE_BURN ,"Ziemlich genauso würde das dann aussehen."),
    NPC_704_KNIGHT_AND_QUEEN_1(Sound.ENTITY_BLAZE_DEATH ,"Jetzt wollen wir beide kombinieren. Platziere Damen und Springer so, dass sich niemand gegenseitig schlägt."),
    NPC_705_KNIGHT_AND_QUEEN_2(Sound.ENTITY_BLAZE_HURT ,"Ich habe vollstes Vertrauen, dass du diese Herausforderung lösen kannst."),
    NPC_706_SUPERQUEEN_INTRO(Sound.ENTITY_BLAZE_SHOOT ,"Und da du das nun verstanden hast, kommen wir zum letzten Punkt, den ich dir zeigen möchte."),
    NPC_707_SUPERQUEEN_EXPLAIN(Sound.ENTITY_CAMEL_AMBIENT ,"Wenn wir die Eigenschaften von Dame und Springer vereinen, haben wir eine neue Figur die wir Superdame nennen wollen."),
    NPC_708_SUPERQUEEN_MOVE_1(Sound.ENTITY_CAMEL_DASH ,"Hier siehst du, wie sich die Superdame auf dem Schachbrett bewegen kann. Beachte die Vielzahl an Möglichkeiten, die sie hat."),
    NPC_709_SUPERQUEEN_MOVE_2(Sound.ENTITY_CAMEL_DASH_READY ,"Wie die Dame bewegt sich die Superdame also in alle Graden und Diagonalen, kann aber zusätzlich wie ein Springer springen.\r\n"+
                             "Versuche nun dieses Brett mit Superdamen zu füllen."),
    NPC_710_END(Sound.ENTITY_CAMEL_DEATH ,"Ich hoffe, dass dir dies beim Verstehen des N-Damen-Problems und der Lösung durch die Anwendung des Backtracking-Algorithmus geholfen hat, und ich wünsche dir weiterhin viel Erfolg."),

    // Positive Response
    NPC_POSITIVE_1(Sound.ENTITY_CAMEL_EAT ,"Richtig!"),
    NPC_POSITIVE_2(Sound.ENTITY_CAMEL_HURT ,"Korrekt!"),
    NPC_POSITIVE_3(Sound.ENTITY_CAMEL_SADDLE ,"Ganz genau!"),
    NPC_POSITIVE_4(Sound.ENTITY_CAMEL_SIT ,"Genau so!"),
    NPC_POSITIVE_5(Sound.ENTITY_CAMEL_STAND ,"Ich hätte es selber nicht besser machen können!"),
    NPC_POSITIVE_6(Sound.ENTITY_CAMEL_STEP ,"Hervorragend!"),

    // Negative Response
    NPC_NEGATIVE_1(Sound.ENTITY_ELDER_GUARDIAN_CURSE ,"Nicht so."),
    NPC_NEGATIVE_2(Sound.ENTITY_ELDER_GUARDIAN_DEATH ,"Schaue es dir nochmal an."),
    NPC_NEGATIVE_3(Sound.ENTITY_ELDER_GUARDIAN_DEATH_LAND ,"Versuch es nochmal."),
    NPC_NEGATIVE_4(Sound.ENTITY_ELDER_GUARDIAN_FLOP ,"So ist das leider nicht richtig."),
    NPC_NEGATIVE_5(Sound.ENTITY_ELDER_GUARDIAN_HURT ,"Du findest bestimmt die richtige Lösung.");

    
    private Sound sound;
    private String text;

    private NPCTrack(Sound sound, String text) {
        this.sound = sound;
        this.text = text;
    }

    public Sound getSound() { return this.sound; }
    public String getText() { return this.text; }
}
