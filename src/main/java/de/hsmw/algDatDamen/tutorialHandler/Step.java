package de.hsmw.algDatDamen.tutorialHandler;

public abstract class Step {

    private Step prev;
    private Step next;
    protected boolean completed;

    public Step(Step prev, Step next) {
        this(next, prev, false);
    }
    public Step(Step prev, Step next, boolean completed) {
        this.prev = prev;
        this.next = next;
        this.completed = completed;
    }

    public void setPrev(Step prev) { this.prev = prev; }
    public void setNext(Step next) { this.next = next; }
    public Step getPrev() { return prev; }
    public Step getNext() { return next; }
}
