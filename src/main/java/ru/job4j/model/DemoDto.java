package ru.job4j.model;

public class DemoDto {

    private boolean done;

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "DemoDto{" +
                "done=" + done +
                '}';
    }
}