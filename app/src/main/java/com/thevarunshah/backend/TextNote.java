package com.thevarunshah.backend;

public class TextNote extends Note{

    private String notes = null;

    public TextNote(int id, String name){

        super(id, name);
        this.notes = "";
    }

    public String getNotes() {
        return this.notes;
    }
}
