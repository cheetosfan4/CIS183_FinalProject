package com.example.cis183_finalproject;

public class ColorData {
    String hex;
    String name;
    User author;

    public ColorData() {

    }

    public ColorData(String h, String n, User a) {
        hex = h;
        name = n;
        author = a;
    }

    //getters
    public String getHex() {
        return hex;
    }
    public String getName() {
        return name;
    }
    public User getAuthor() {
        return author;
    }

    //setters
    public void setHex(String h) {
        hex = h;
    }
    public void setName(String n) {
        name = n;
    }
    public void setAuthor(User a) {
        author = a;
    }
}