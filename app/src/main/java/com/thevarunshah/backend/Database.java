package com.thevarunshah.backend;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {

    private static AtomicInteger idCounter = new AtomicInteger();

    private static ArrayList<Note> notes = new ArrayList<Note>();

    public static int getNextID() {
        return idCounter.incrementAndGet();
    }

    public void setIdCounter(int lastID){
        this.idCounter.set(lastID);
    }

    public static ArrayList<Note> getNotes() {
        return notes;
    }
}
