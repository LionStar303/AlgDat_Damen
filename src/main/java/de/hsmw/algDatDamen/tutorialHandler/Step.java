package de.hsmw.algDatDamen.tutorialHandler;

import java.util.function.Predicate;

public class Step {

    private Step prev;
    private Step next;
    private final Runnable onStart;
    private final Runnable onReset;
    private final Predicate<Void> checkCompletion;
    protected boolean completed;

    public Step(Runnable onStart, Runnable onReset) {
        this(onStart, onReset, null);

    }
    public Step(Runnable onStart, Runnable onReset, Predicate<Void> checkCompletion) {
        this(null, null, onStart, onReset, checkCompletion);
    }
    public Step(Step prev, Step next, Runnable onStart, Runnable onReset, Predicate<Void> checkCompletion) {
        // standard Initialisierung mit completed = false
        this(next, prev, onStart, onReset, checkCompletion, false);
    }
    public Step(Step prev, Step next, Runnable onStart, Runnable onReset, Predicate<Void> checkCompletion, boolean completed) {
        this.prev = prev;
        this.next = next;
        this.onStart = onStart;
        this.onReset = onReset;
        this.checkCompletion = checkCompletion;
        this.completed = completed;
    }

    public boolean completed() { return completed; }

    public void setPrev(Step prev) { this.prev = prev; }
    public void setNext(Step next) { this.next = next; }
    public Step getPrev() { return prev; }
    public Step getNext() { return next; }

    public void backLink() {
        next.setPrev(this);
        if(next.getNext() != null) next.backLink();
    }
    public void start() {
        onStart.run();
        if(checkCompletion == null) completed = true;
        if(checkCompletion == null) completed = true;
    };
    public void reset() {
        onReset.run();
        completed = false;
    };
    public boolean checkForCompletion() {
        // wenn kein checkCompletion definiert wurde, ist der step automatisch completed
        if(checkCompletion == null) {
            this.completed = true;
            return true;
        }
        completed = checkCompletion.test(null);
        return completed;
    }
}
