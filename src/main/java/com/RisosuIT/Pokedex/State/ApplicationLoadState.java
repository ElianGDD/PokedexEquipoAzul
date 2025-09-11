package com.RisosuIT.Pokedex.State;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApplicationLoadState {

    private final AtomicInteger loaded = new AtomicInteger(0);
    private final AtomicInteger total = new AtomicInteger(0);
    private volatile boolean done = false;

    public void reset(int total) {
        this.total.set(total);
        this.loaded.set(0);
        this.done = false;
    }

    public void increment() {
        this.loaded.incrementAndGet();
    }

    public void markDone() {
        this.done = true;
    }

    public int getLoaded() {
        return loaded.get();
    }

    public int getTotal() {
        return total.get();
    }

    public boolean isDone() {
        return done;
    }
}
