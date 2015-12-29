package com.thevarunshah.backend;

import java.util.ArrayList;

public class ListNote extends Note{

    private ArrayList<String> list = null;

    public ListNote(int id, String name){

        super(id, name);
        this.list = new ArrayList<String>();
    }

    public ArrayList<String> getList() {
        return this.list;
    }
}
