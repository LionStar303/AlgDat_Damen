package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Sound;

import net.kyori.adventure.text.format.NamedTextColor;

public enum NPCTrack {

    // INTRO
    NPC_001_INTRO(Sound.ENTITY_ARMADILLO_AMBIENT,
            "Ah willkommen, du möchtest wissen, wie man am besten N Damen mit Hilfe "
            + "des Backtracking-Algorithmus löst? Lass mich dir erklären, wie man ein "
            + "Schachspiel Feld mit Damen ausfüllt, ohne dass sie sich gegenseitig schlagen."),

    // Level 1
    NPC_101_EXPLAIN_CHESSBOARD(Sound.ENTITY_ARMADILLO_BRUSH,
            "Aber was heißt das fragst du? Keine Sorge, alles zu seiner Zeit. "
            + "Zuerst, hier ist ein Schachbrett. Wir betrachten das Problem in unterschiedlichen "
            + "Variablen. Das hier ist erst einmal ein 8x8 großes Schachbrett, es ist unterteilt in "
            + "zweifarbige Quadrate auf denen sich pro Quadrat jeweils nur eine Figur "
            + "befinden kann."),
    NPC_102_EXPLAIN_QUEEN(Sound.ENTITY_ARMADILLO_DEATH,
            "Im Schach gibt es insgesamt sechs verschiedene Arten von Figuren. Aber heute "
            + "werden uns zwei reichen. Zuerst diese Figur, man nennt sie Dame"),
    NPC_103_EXPLAIN_MOVEMENT(Sound.ENTITY_ARMADILLO_EAT,
            "Die Dame kann sich beliebig weit in alle Diagonalen und Geraden "
            + "bewegen. "),
    NPC_104_EXPLAIN_THREATS(Sound.ENTITY_ARMADILLO_HURT,
            "Sollte sich eine andere Figur im Bewegungsbereich befindet, gilt sie "
            + "als bedroht und kann geschlagen werden. "),
    NPC_105_MOVEMENT_MARKING(Sound.BLOCK_ANVIL_FALL,
            "Nun versuche selbst das Bewegungsmuster der Dame einzuzeichen. "),

    // Level 2
    NPC_201_INTRO(Sound.ENTITY_ARMADILLO_HURT_REDUCED,
            "Jetzt werde ich dir zeigen wie ich ein 8x8 Feld mit Damen fülle, "
            + "ohne dass sich eine der Damen gegenseitig bedroht."),
    NPC_202_EXPLAIN_THREATS_1(Sound.ENTITY_ARMADILLO_LAND,
            "Denn, wie du sehen kannst, wenn ich die Dame so platziere, bedroht "
            + "sie folgende Felder. "),
    NPC_203_EXPLAIN_THREATS_2(Sound.ENTITY_ARMADILLO_PEEK,
            "Und würde ich jetzt eine weitere Dame platzieren. Ungefähr so… "),
    NPC_204_EXPLAIN_PROBLEM(Sound.ENTITY_ARMADILLO_ROLL,
            "Dann würde meine erste Dame die zweite schlagen können und das "
            + "wollen wir hier vermeiden. Also entfernen wir die zweite Dame "
            + "wieder und setzen sie neu."),
    NPC_205_SOLVE(Sound.ENTITY_ARMADILLO_SCUTE_DROP,
            "Nun bedrohen sich die beiden Damen nicht mehr. Aber so ist das Brett "
            + "natürlich noch nicht gefüllt. Lass mich die restlichen Damen auch "
            + "noch platzieren. "),
    NPC_206_EXPLAIN_SOLUTION(Sound.ENTITY_ARMADILLO_UNROLL_FINISH,
            "Wenn du jetzt selbst einen Blick auf das Feld wirfst, solltest du "
            + "feststellen können, dass wir keine weitere Dame mehr platzieren "
            + "können, ohne dass sie bedroht wird, aber auch kein unbedrohtes Feld "
            + "übrig bleibt."
            + "Hierbei handelt es sich um das sogenannte N-Damen-Problem. "
            + "Eine Herausforderung, bei der N Damen auf einem N x N Schachbrett "
            + "platziert werden müssen, ohne dass sich zwei Damen gegenseitig "
            + "schlagen."
            + "Und wie du auch gesehen hast, haben wir das Problem soeben gelöst. "
            + "Also lass uns nun dazu übergehen wie das passiert ist. "),
    NPC_207_EXPLAIN_3X3_1(Sound.ENTITY_ARMADILLO_STEP,
            "Hier ein weitaus kleineres Feld, aber leider ist dieses Feld zu "
            + "klein als dass man hier ein Lösung finden könnte. "),
    NPC_208_EXPLAIN_3X3_2(Sound.ENTITY_ARMADILLO_UNROLL_START,
            "Egal wie ich diese drei Damen platziere, es würden sich immer "
            + "mindestens zwei gegenseitig schlagen. "
            + "Also, das N-Damen Problem kann man erst ab einer Größe von 4x4 lösen. "),
    NPC_209_EXPLAIN_4x4_SOLUTION(Sound.ENTITY_AXOLOTL_ATTACK,
            "Dieses Schachbrett bietet also genug Platz, dass allen Damen ein "
            + "Platz zugewiesen werden kann, ohne dass sie sich gegenseitig schlagen können."),
    NPC_210_SUMMARY(Sound.ENTITY_AXOLOTL_DEATH, "Also fassen wir zusammen. "
            + "Also fassen wir zusammen: Auf einem Schachbrett der Größe 3x3 gibt es keine gültige Lösung. "
            + "Erst ab einer Größe von 4x4 ist eine zu finden. "),

    // Level 3
    NPC_301_INTRO(Sound.ENTITY_AXOLOTL_HURT,
            "Aber jetzt sollst du mal dich versuchen. Hier ist ein 4x4 Brett. "
            + "Setze vier Damen so, dass keine eine andere bedroht. "),
    NPC_302_DIFFERENT_SOLUTIONS(Sound.ENTITY_AXOLOTL_IDLE_AIR,
            "Aber es hätte auch eine andere Lösung gegeben. Bestimme wie die "
            + "Lösungen auf diesen beiden Brettern sich unterscheiden. "
            + "Gib nun deine Lösung in das Textfeld ein. "
            + "Um das Textfeld zu öffnen, drücke die T-Taste."),
    NPC_303_SECOND_TASK(Sound.ENTITY_AXOLOTL_IDLE_WATER,
            "Jetzt wirst du erneut versuchen, vier Damen zu platzieren, aber "
            + "diesmal ohne bedrohte Felder. Achte wieder darauf, dass du die Damen "
            + "so setzt, dass sie sich nicht gegenseitig schlagen. "),

    // Level 4
    NPC_401_INTRO(Sound.ENTITY_PIG_AMBIENT,
            "Lass uns nun das Ganze nochmal auf einem größeren Feld betrachten. "),
    NPC_402_EXPLAIN_PROBLEM(Sound.ENTITY_AXOLOTL_SPLASH,
            "Löse nun das Schachbrett! "
            + "Du wirst feststellen, dass es nicht so einfach ist. Ich glaube aber, "
            + "dass du es schaffen kannst. "
            + "Falls du es nicht schaffst, ist das auch nicht so schlimm. Nach einiger "
            + "Zeit kannst du einfach weiterklicken. "),
    NPC_403_EXPLAIN_BACKTRACKING_1(Sound.ENTITY_AXOLOTL_SWIM,
            "Der Backtracking-Algorithmus ist eine Methode, um Lösungen für "
            + "Probleme zu finden, "
            + "indem man systematisch alle möglichen Optionen ausprobiert und "
            + "zurückgeht, wenn man auf eine Sackgasse stößt. "
            + "Lass mich dir zeigen, wie das funktioniert."),
    NPC_405_EXPLAIN_BACKTRACKING_2(Sound.ENTITY_BAT_DEATH,
            "Jedes Mal, wenn eine Dame platziert wird, prüfen wir, ob diese "
            + "Position es erlaubt, eine weitere zu stellen. "
            + "Ansonsten nehmen wir die Dame wieder vom Brett und versuchen sie an "
            + "einer anderen Stelle zu platzieren, wo wir dann wieder prüfen, ob sie "
            + "gültig ist."),

    // Level 5
    NPC_501_INTRO(Sound.ENTITY_BAT_HURT, "Prüfe nun wieder selbst, ob du den Algorithmus anwenden kannst. "
            + "Setze die sechs Damen wieder auf dem Brett nach den bekannten "
            + "Vorgaben, aber nutze diesmal dein Wissen über den Backtracking-Algorithmus. "),
    NPC_502_EXPLAIN_1(Sound.ENTITY_BAT_LOOP, "Schaffst du es auch, wenn wir die Farben tauschen? "),

    // Level 6
    NPC_601_INTRO(Sound.ENTITY_BAT_TAKEOFF, "Das ging schneller als gedacht, hier darfst du dich an einem "
            + "richtigen Schachbrett versuchen. "),
            // TODO diesbezüglich
    NPC_602_EXPLAIN_2(Sound.ENTITY_BEE_DEATH, "Versuch jetzt das Brett zu vervollständigen. Aber keine Panik "
            + "(diesbezüglich), ich gebe dir etwas Hilfestellung. "),
    NPC_603_EXPLAIN_3(Sound.ENTITY_BEE_HURT, "Jetzt musst du noch den Anfang versuchen, setze nur die "
            + "ersten drei Damen. "),
    @Deprecated // die fliegt raus
    NPC_605_EXPLAIN_4(Sound.ENTITY_BEE_LOOP, "Hier sind zwei 8x8 Felder, eins ist gelöst, das andere "
            + "nicht. Welches ist welches? "),
    @Deprecated // die auch
    NPC_606_EXPLAIN(Sound.ENTITY_BEE_POLLINATE, "Aber fühl dich nicht allzu eingeschüchtert. Du musst nur "
        + "sechs der zehn Damen setzen. "),

    // Level 7
    NPC_701_INTRO(Sound.ENTITY_BEE_LOOP_AGGRESSIVE, "Jetzt werden wir das N-Damen-Problem auf einem noch größeren "
            + "Schachbrett betrachten. Du wirst sehen, wie sich die Herausforderungen "
            + "mit der Größe des Schachbretts ändern. "),
    NPC_702_KNIGHT_1(Sound.ENTITY_BLAZE_AMBIENT, "Kommen wir nun zur zweiten Figur, die wir hier nutzen wollen."
            + "Der Springer bewegt sich in einem L-förmigen Muster. Er kann "
            + "zwei Felder in eine Richtung und dann ein Feld in eine senkrechte "
            + "Richtung springen oder umgekehrt. "),
    NPC_703_KNIGHT_2(Sound.ENTITY_BLAZE_BURN, "Ziemlich genauso würde das dann aussehen. "),
    // TODO huh?
    NPC_704_KNIGHT_AND_QUEEN_1(Sound.ENTITY_BLAZE_DEATH, "Jetzt wollen wir beide kombinieren. Platziere Damen und "
            + "Springer so, dass sich niemand gegenseitig schlägt. "),
    NPC_705_KNIGHT_AND_QUEEN_2(Sound.ENTITY_BLAZE_HURT, "Ich habe vollstes Vertrauen, dass du diese Herausforderung "
            + "lösen kannst."),
    NPC_706_SUPERQUEEN_INTRO(Sound.ENTITY_BLAZE_SHOOT, "Und da du das nun verstanden hast, kommen wir zum letzten "
            + "Punkt, den ich dir zeigen möchte."),
    NPC_707_SUPERQUEEN_EXPLAIN(Sound.ENTITY_CAMEL_AMBIENT, "Wenn wir die Eigenschaften von Dame und Springer vereinen, "
            + "haben wir eine neue Figur, die wir Superdame nennen wollen. "),
    NPC_708_SUPERQUEEN_MOVE_1(Sound.ENTITY_CAMEL_DASH, "Hier siehst du, wie sich die Superdame auf dem Schachbrett "
            + "bewegen kann. Beachte die Vielzahl an Möglichkeiten, die sie hat. "),
    NPC_709_SUPERQUEEN_MOVE_2(Sound.ENTITY_CAMEL_DASH_READY, "Wie die Dame bewegt sich die Superdame also in alle Graden "
            + "und Diagonalen, kann aber zusätzlich wie ein Springer springen. "
            + "Versuche nun dieses Brett mit Superdamen zu füllen. "),
    NPC_710_END(Sound.ENTITY_CAMEL_DEATH, "Ich hoffe, dass dir dies beim Verstehen des N-Damen-Problems "
            + "und der Lösung durch die Anwendung des Backtracking-Algorithmus "
            + "geholfen hat, und ich wünsche noch viel Erfolg. "),
    NPC_801_INTRO(Sound.BLOCK_AZALEA_HIT, "Glückwunsch; du hast das Ende erreicht, nun kannst du dich selbst nach Belieben am N-Damen Problem ausprobieren."
            + "Nutze dazu das DevMenu, auf das du nun Zugriff hast. Viel Spaß"),

    // Positive Response
    NPC_POSITIVE_1(Sound.ENTITY_CAMEL_EAT, "Richtig!", NamedTextColor.GREEN),
    NPC_POSITIVE_2(Sound.ENTITY_CAMEL_HURT, "Korrekt!", NamedTextColor.GREEN),
    NPC_POSITIVE_3(Sound.ENTITY_CAMEL_SADDLE, "Ganz genau!", NamedTextColor.GREEN),
    NPC_POSITIVE_4(Sound.ENTITY_CAMEL_SIT, "Genau so!", NamedTextColor.GREEN),
    NPC_POSITIVE_5(Sound.ENTITY_CAMEL_STAND, "Ich hätte es selber nicht besser machen können!", NamedTextColor.GREEN),
    NPC_POSITIVE_6(Sound.ENTITY_CAMEL_STEP, "Hervorragend!", NamedTextColor.GREEN),

    // Negative Response
    NPC_NEGATIVE_1(Sound.ENTITY_ELDER_GUARDIAN_CURSE, "Nicht so.", NamedTextColor.RED),
    NPC_NEGATIVE_2(Sound.ENTITY_ELDER_GUARDIAN_DEATH, "Schaue es dir nochmal an.", NamedTextColor.RED),
    NPC_NEGATIVE_3(Sound.ENTITY_ELDER_GUARDIAN_DEATH_LAND, "Versuch es nochmal.", NamedTextColor.RED),
    NPC_NEGATIVE_4(Sound.ENTITY_ELDER_GUARDIAN_FLOP, "So ist das leider nicht richtig.", NamedTextColor.RED),
    NPC_NEGATIVE_5(Sound.ENTITY_ELDER_GUARDIAN_HURT, "Du findest bestimmt die richtige Lösung.", NamedTextColor.RED);

    private final Sound sound;
    private final String text;
    private final NamedTextColor color;

    NPCTrack(Sound sound, String text) {
        this(sound, text, null);
    }
    NPCTrack(Sound sound, String text, NamedTextColor color) {
        this.sound = sound;
        this.text = text;
        this.color = color;
    }

    public Sound getSound() {
        return sound;
    }

    public String getText() {
        return insertLineBreaks(text);
    }

    public NamedTextColor getColor() {
        return this.color;
    }

    public static String insertLineBreaks(String input) {
        StringBuilder formattedString = new StringBuilder();
        int lineLength = 60;
    
        int i = 0;
        while (i + lineLength < input.length()) {
            int end = i + lineLength;
            while (end > i && input.charAt(end) != ' ') {
                end--;
            }
            if (end == i) {
                end = i + lineLength;
            }
            formattedString.append(input, i, end);
            formattedString.append("\n");
            i = end;
            while (i < input.length() && input.charAt(i) == ' ') {
                i++;
            }
        }
        formattedString.append(input.substring(i));
        return formattedString.toString();
        }
}
