package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.event.Listener;

public class Step implements Listener{

    Step prev;
    Step next;
    String description;
    boolean completed;

    public Step(Step prev, Step next, String description, boolean completed) {
        this.prev = prev;
        this.next = next;
    }
    public Step(Step prev, Step next, String description) {
        this(prev, next, description, false);
    }
    public Step(Step prev, Step next) {
        this(prev, next, null, false);
    }

    public void play() {
        
    }

    public boolean getCompleted() {
        return completed;
    }
}
