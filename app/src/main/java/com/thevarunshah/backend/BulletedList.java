package com.thevarunshah.backend;

import java.util.ArrayList;

public class BulletedList extends Note{

    private ArrayList<String> list = null;

    public BulletedList(int id, String name){

        super(id, name);
        this.list = new ArrayList<String>();
    }

    public ArrayList<String> getList() {
        return this.list;
    }
}
