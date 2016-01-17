package com.thevarunshah.classes;

import com.thevarunshah.classes.Note;

import java.util.Calendar;
import java.util.Date;

public class Reminder extends Note {

    private Date due;
    private int reminder;
    private String notes;

    public Reminder(int id, String name){

        super(id, name);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        this.due = cal.getTime();
        this.reminder = 5;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
