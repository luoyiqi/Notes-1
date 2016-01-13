package com.thevarunshah.classes;

import com.thevarunshah.classes.Note;

import java.util.ArrayList;

public class Checklist extends Note {

    private ArrayList<ChecklistItem> list = null;

    public Checklist(int id, String name){

        super(id, name);
        this.list = new ArrayList<ChecklistItem>();
    }

    public ArrayList<ChecklistItem> getList() {
        return this.list;
    }
}
