package com.thevarunshah.backend;

import java.util.ArrayList;

public class Checklist extends Note{

    private ArrayList<String> list = null;

    public Checklist(int id, String name){

        super(id, name);
        this.list = new ArrayList<String>();
    }

    public ArrayList<String> getList() {
        return this.list;
    }
}
