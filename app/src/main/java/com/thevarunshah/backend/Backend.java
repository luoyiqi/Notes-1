package com.thevarunshah.backend;

import com.thevarunshah.classes.Note;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Backend {

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
