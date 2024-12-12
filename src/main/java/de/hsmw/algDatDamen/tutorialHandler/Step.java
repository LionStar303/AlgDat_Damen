package de.hsmw.algDatDamen.tutorialHandler;

public class Step {

    private Step prev;
    private Step next;
    private Runnable onStart;
    private Runnable onReset;
    protected boolean completed;

    public Step(Runnable onStart, Runnable onReset) {
        this(null, null, onStart, onReset);
    }
    public Step(Step prev, Step next, Runnable onStart, Runnable onReset) {
        // standard Initialisierung mit completed = false
        this(next, prev, onStart, onReset, false);
    }
    public Step(Step prev, Step next, Runnable onStart, Runnable onReset, boolean completed) {
        this.prev = prev;
        this.next = next;
        this.onStart = onStart;
        this.onReset = onReset;
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
        completed = true;
    };
    public void reset() {
        onReset.run();
        completed = false;
    };
}
