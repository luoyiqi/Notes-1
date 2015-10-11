package com.thevarunshah.backend;

public class PlainText extends Note{

    private String notes = null;

    public PlainText(String name){

        super(name);
        this.notes = "";
    }

    public String getNotes() {
        return this.notes;
    }
}
