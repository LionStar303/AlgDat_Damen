package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Sound;

import net.kyori.adventure.text.format.NamedTextColor;

public enum NPCTrack {

    // Level 1
    NPC_101_EXPLAIN_CHESSBOARD(Sound.entity_armadillo_brush,
            "Aber was soll das heißen fragst du? Nun gut zuerst, hier ist ein "
            + "Schachbrett. Wir betrachten das Problem in unterschiedlichen "
            + "Variablen. Das hier ist ein 8*8 Schachbrett, es ist unterteilt in "
            + "bikolorierten Quadraten auf denen sich pro Quadrat nur eine Figur "
            + "befinden kann."),
    NPC_102_EXPLAIN_QUEEN(Sound.entity_armadillo_death,
            "Im Schach gibt es sechs verschiedene Arten von Figuren. Aber heute "
            + "sollen uns zwei reichen. Diese Figur nennt man Dame. "),
    NPC_103_EXPLAIN_MOVEMENT(Sound.entity_armadillo_eat,
            "Die Dame kann sich beliebig weit in alle Diagonale und Graden "
            + "bewegen. "),
    NPC_104_EXPLAIN_THREATS(Sound.entity_armadillo_hurt,
            "Wenn sich eine andere Figur im Bewegungsbereich befindet, ist sie "
            + "bedroht und kann von der sich bewegenden Figur geschlagen werden. "),
    NPC_105_MOVEMENT_MARKING(Sound.block_anvil_fall,
            "Nun versuche selbst das Bewegungsmuster der Dame einzuzeichnen. "),
    // Level 2
    NPC_201_INTRO(Sound.entity_armadillo_hurt_reduced,
            "Jetzt will ich dir zeigen wie ich ein 8x8 Feld fülle ohne das sich "
            + "eine der Damen gegenseitig bedroht."),
    NPC_202_EXPLAIN_THREATS_1(Sound.entity_armadillo_land,
            "Denn, wie du sehen kannst, wenn ich die Dame so platziere bedroht "
            + "sie folgende Felder. "),
    NPC_203_EXPLAIN_THREATS_2(Sound.entity_armadillo_peek,
            "Und würde ich jetzt eine weitere Dame platzieren. Ungefähr so… "),
    NPC_204_EXPLAIN_PROBLEM(Sound.entity_armadillo_roll,
            "Dann würde meine erste Dame die zweite schlagen können und das "
            + "wollen wir hier vermeiden. Also entfernen wir die zweite Dame "
            + "wieder und setzen sie neu."),
    NPC_205_SOLVE(Sound.entity_armadillo_scute_drop,
            "Nun bedrohen sich die beiden Damen nicht mehr. Aber so ist das Brett "
            + "natürlich noch nicht gefüllt. Lass mich die restlichen Damen auch "
            + "noch platzieren. "),
    NPC_206_EXPLAIN_SOLUTION(Sound.entity_armadillo_unroll_finish,
            "Wenn du jetzt selbst einen Blick auf das Feld wirfst solltest du "
            + "feststellen können das wir keine weitere Dame mehr platzieren "
            + "können, ohne das sie bedroht wird, aber auch kein unbedrohtes Feld "
            + "übrig bleibt."
            + "Hierbei handelt es sich um das sogenannte N-Damen-Problem."
            + "Eine Herausforderung, bei der N Damen auf einem N x N Schachbrett "
            + "platziert werden müssen, ohne dass sich zwei Damen gegenseitig "
            + "angreifen."
            + "Und wie du auch gesehen hast haben wir das Problem soeben gelöst. "
            + "Also lass mich nun dazu über gehen wie das passiert ist. "),
    NPC_207_EXPLAIN_3X3_1(Sound.entity_armadillo_step,
            "Hier ist ein weitaus kleineres Feld, aber leider ist dieses Feld zu "
            + "klein als das man hier ein Lösung finden könnte. "),
    NPC_208_EXPLAIN_3X3_2(Sound.entity_armadillo_unroll_start,
            "Egal wie ich diese drei Damen platziere, es würden sich immer "
            + "mindestens zwei gegenseitig schlagen. "
            + "Denn das N-Damen Problem kann man erst ab einer Größe von 4x4 lösen. "),
    NPC_209_EXPLAIN_4x4_SOLUTION(Sound.entity_axolotl_attack,
            "Ab 4x4 kann ich allerdings wieder allen Damen einen Platz zuweisen "
            + "ohne das sie sich gegenseitig bedrohen. "),
    NPC_210_SUMMARY(Sound.entity_axolotl_death, "Also fassen wir zusammen. "
            + "Auf einem Schachbrett der Größe 3x3 gibt es keine gültige Lösung, "
            + "erst ab einer Größe von 4x4 ist eine zu finden. "),
    // Level 3
   NPC_301_INTRO(Sound.entity_axolotl_hurt,
            "4 Brett. setze vier Damen so, dass keine eine andere bedroht. "),
    NPC_302_DIFFERENT_SOLUTIONS(Sound.entity_axolotl_idle_air,
            "Aber es hätte auch eine andere Lösung gegeben. Bestimme wie die "
            + "Lösungen auf diesen beiden Brettern sich unterscheiden. "),
    NPC_303_SECOND_TASK(Sound.entity_axolotl_idle_water,
            "Jetzt wirst du erneut versuchen, vier Damen zu platzieren, aber "
            + "diesmal ohne bedrohte Felder. Achte wieder darauf, dass du die Damen "
            + "so setzt, dass sie sich nicht gegenseitig schlagen. "),
    /*
     * TODO
     * Diese Texte müssen nochmal eingesprochen werden:
     * NPC_401_INTRO -> ENTITY_PIG_AMBIENT
     * Lass uns nun das Ganze nochmal auf einem größeren Feld betrachten.
     * 
     * NPC_402_EXPLAIN_PROBLEM -> ENTITY_AXOLOTL_SPLASH
     * Löse nun das Schachbrett! Du wirst feststellen, dass es nicht so einfach ist.
     * Ich glaube aber, dass du es schaffen kannst. Falls du es nicht schaffst, ist
     * das auch nicht schlimm. Nach einiger Zeit kannst du einfach weiterklicken.
     * 
     * NPC_404_STEP_BY_STEP -> Sound.ENTITY_BAT_AMBIENT ist unwichtig
     * --> man könnte folgende Variationen tauschen:
     * -> NPC_401_INTRO -> ENTITY_AXOLOTL_SPLASH
     * -> NPC_402_EXPLAIN_PROBLEM -> ENTITY_AXOLOTL_SWIM
     * -> NPC_403_EXPLAIN_BACKTRACKING_1 -> ENTITY_BAT_AMBIENT
     * -> NPC_404_EXPLAIN_BACKTRACKING_2 -> ENTITY_BAT_DEATH
     * Ich habe es schon im Quelltext hier drunter angepasst.
     */
    NPC_401_INTRO(Sound.entity_axolotl_splash,
            "Lass uns nun das Ganze nochmal auf einem größeren Feld betrachten. "),
    NPC_402_EXPLAIN_PROBLEM(Sound.entity_axolotl_swim,
            "Löse nun das Schachbrett! "
            + "Du wirst feststellen, dass es nicht so einfach ist. Ich glaube aber, "
            + "dass du es schaffen kannst. "
            + "Falls du es nicht schaffst, ist das auch nicht schlimm. Nach einiger "
            + "Zeit kannst du einfach weiterklicken. "),
    NPC_403_EXPLAIN_BACKTRACKING_1(Sound.entity_bat_ambient,
            "Der Backtracking-Algorithmus ist eine Methode, um Lösungen für "
            + "Probleme zu finden, "
            + "indem man systematisch alle möglichen Optionen ausprobiert und "
            + "zurückgeht, wenn man auf eine Sackgasse stößt. "
            + "Lass mich dir zeigen, wie das funktioniert. "),
    @Deprecated
    NPC_404_STEP_BY_STEP(Sound.entity_bat_ambient, "Lass uns den Algorithmus nun Schritt für Schritt durchlaufen. "),
    NPC_405_EXPLAIN_BACKTRACKING_2(Sound.entity_bat_death,
            "Jedes Mal, wenn eine Dame platziert wird, prüfen wir, ob diese "
            + "Position es erlaubt, eine weitere zu stellen. "
            + "Ansonsten nehmen wir die Dame wieder vom Brett und versuchen sie an "
            + "einer anderen Stelle zu platzieren, wo wir dann wieder prüfen, ob sie "
            + "gültig ist. "),
    // Level 5
    // TODO für sechs Damen anpassen
    NPC_501_INTRO(Sound.entity_bat_hurt, "Prüfe nun wieder selbst, ob du den Algorithmus anwenden kannst. "
            + "Setze die sechs Damen wieder auf dem Brett nach den bekannten "
            + "Vorgaben, aber nutze diesmal dein Wissen über den Backtracking-Algorithmus. "),
    NPC_502_EXPLAIN_1(Sound.entity_bat_loop, "Schaffst du es auch, wenn wir die Farben tauschen? "),
    // Level 6
    // TODO Intro für Level 6
    NPC_601_INTRO(Sound.entity_bat_takeoff, "Das ging ja schneller als gedacht, hier darfst du dich an einem "
            + "richtigen Schachbrett versuchen. "),
    // TODO NPC_602_HELP und NPC_603_EXPLAIN_2 zusammenfassen (ENTITY_BAT_TAKEOFF wird für Intro verwendet)
    // NPC_602_HELP(Sound.ENTITY_BAT_TAKEOFF ,""),
    NPC_602_EXPLAIN_2(Sound.entity_bee_death, "Versuch jetzt das Brett zu vervollständigen. Aber keine Panik "
              + "(diesbezüglich), ich gebe dir etwas Hilfestellung. "),
    // TODO abgeänderter Text wenn genug Zeit ist
    NPC_603_EXPLAIN_3(Sound.entity_bee_hurt, "Jetzt musst du noch den Anfang versuchen, setze nur die "
            + "ersten drei Damen. "),
    @Deprecated // die fliegt raus
    NPC_605_EXPLAIN_4(Sound.entity_bee_loop, "Hier sind zwei 8x8 Felder, eins ist gelöst, das andere "
            + "nicht. Welches ist welches? "),
    // Level 7
    NPC_701_INTRO(Sound.entity_bee_loop_aggressive, "Jetzt werden wir das N-Damen-Problem auf einem noch größeren "
            + "Schachbrett betrachten. Du wirst sehen, wie sich die Herausforderungen "
            + "mit der Größe des Schachbretts ändern. "),
    NPC_606_EXPLAIN(Sound.entity_bee_pollinate, "Aber fühl dich nicht allzu eingeschüchtert. Du musst nur "
            + "sechs der zehn Damen setzen. "),
    // TODO NPC_701_OLD mit NPC_702_KNIGHT_1 zusammenfassen
    NPC_701_OLD(Sound.entity_bee_sting, "Kommen wir nun zur zweiten Figur, die wir hier nutzen wollen. "),
    NPC_702_KNIGHT_1(Sound.entity_blaze_ambient, "Der Springer bewegt sich in einem L-förmigen Muster. Er kann "
            + "zwei Felder in eine Richtung und dann ein Feld in eine senkrechte "
            + "Richtung springen oder umgekehrt. "),
    NPC_703_KNIGHT_2(Sound.entity_blaze_burn, "Ziemlich genauso würde das dann aussehen. "),
    NPC_704_KNIGHT_AND_QUEEN_1(Sound.entity_blaze_death, "Jetzt wollen wir beide kombinieren. Platziere Damen und "
            + "Springer so, dass sich niemand gegenseitig schlägt. "),
    NPC_705_KNIGHT_AND_QUEEN_2(Sound.entity_blaze_hurt, "Ich habe vollstes Vertrauen, dass du diese Herausforderung "
            + "lösen kannst."),
    NPC_706_SUPERQUEEN_INTRO(Sound.entity_blaze_shoot, "Und da du das nun verstanden hast, kommen wir zum letzten "
            + "Punkt, den ich dir zeigen möchte."),
    NPC_707_SUPERQUEEN_EXPLAIN(Sound.entity_camel_ambient, "Wenn wir die Eigenschaften von Dame und Springer vereinen, "
            + "haben wir eine neue Figur die wir Superdame nennen wollen. "),
    NPC_708_SUPERQUEEN_MOVE_1(Sound.entity_camel_dash, "Hier siehst du, wie sich die Superdame auf dem Schachbrett "
            + "bewegen kann. Beachte die Vielzahl an Möglichkeiten, die sie hat. "),
    NPC_709_SUPERQUEEN_MOVE_2(Sound.entity_camel_dash_ready, "Wie die Dame bewegt sich die Superdame also in alle Graden "
            + "und Diagonalen, kann aber zusätzlich wie ein Springer springen. "
            + "Versuche nun dieses Brett mit Superdamen zu füllen. "),
    NPC_710_END(Sound.entity_camel_death, "Ich hoffe, dass dir dies beim Verstehen des N-Damen-Problems "
            + "und der Lösung durch die Anwendung des Backtracking-Algorithmus "
            + "geholfen hat, und ich wünsche dir weiterhin viel Erfolg. "),
    // Positive Response
    NPC_POSITIVE_1(Sound.entity_camel_eat, "Richtig!", NamedTextColor.GREEN),
    NPC_POSITIVE_2(Sound.entity_camel_hurt, "Korrekt!", NamedTextColor.GREEN),
    NPC_POSITIVE_3(Sound.entity_camel_saddle, "Ganz genau!", NamedTextColor.GREEN),
    NPC_POSITIVE_4(Sound.entity_camel_sit, "Genau so!", NamedTextColor.GREEN),
    NPC_POSITIVE_5(Sound.entity_camel_stand, "Ich hätte es selber nicht besser machen können!", NamedTextColor.GREEN),
    NPC_POSITIVE_6(Sound.entity_camel_step, "Hervorragend!", NamedTextColor.GREEN),
    // Negative Response
    NPC_NEGATIVE_1(Sound.entity_elder_guardian_curse, "Nicht so.", NamedTextColor.RED),
    NPC_NEGATIVE_2(Sound.entity_elder_guardian_death, "Schaue es dir nochmal an.", NamedTextColor.RED),
    NPC_NEGATIVE_3(Sound.entity_elder_guardian_death_land, "Versuch es nochmal.", NamedTextColor.RED),
    NPC_NEGATIVE_4(Sound.entity_elder_guardian_flop, "So ist das leider nicht richtig.", NamedTextColor.RED),
    NPC_NEGATIVE_5(Sound.entity_elder_guardian_hurt, "Du findest bestimmt die richtige Lösung.", NamedTextColor.RED);

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
