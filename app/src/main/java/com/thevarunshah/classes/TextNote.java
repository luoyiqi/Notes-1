package com.thevarunshah.classes;

import com.thevarunshah.classes.Note;

public class TextNote extends Note {

    private String notes = null;

    public TextNote(int id, String name){

        super(id, name);
        this.notes = "";
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
