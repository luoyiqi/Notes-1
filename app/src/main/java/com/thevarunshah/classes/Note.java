package com.thevarunshah.classes;

import java.io.Serializable;

public class Note implements Serializable{

    private static final long serialVersionUID = 1L; //for serializing data

    private int id;
    private String name = "";

    public Note(int id, String name){

        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
