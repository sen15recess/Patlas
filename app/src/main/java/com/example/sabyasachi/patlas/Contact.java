package com.example.sabyasachi.patlas;

//fetching data via class
public class Contact {

    public int id;
    public String name = "";

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // constructor
    public Contact (int id, String name){
        this.id = id;

        this.name = name;
    }
    public Contact (String name,int id){
        this.id = id;

        this.name = name;
    }

    public Contact (){

    }
}
