package com.thevarunshah.classes;

import com.thevarunshah.classes.Note;

import java.util.Date;

public class Reminder extends Note {

    private Date due;
    private String notes;

    public Reminder(int id, String name){

        super(id, name);
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
