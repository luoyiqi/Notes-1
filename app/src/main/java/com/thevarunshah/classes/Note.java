package com.thevarunshah.classes;

public class Note{

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
