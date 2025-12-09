package com.example.cis183_finalproject;

import java.io.Serializable;

public class ColorData implements Serializable {
    private String hex;
    private String name;
    private User author;

    public ColorData() {

    }

    public ColorData(String h, String n, User a) {
        hex = h;
        name = n;
        author = a;
    }

    public static String RGBtoHex(int r, int g, int b) {
        String hex = "";

        hex += Character.forDigit((r / 16), 16);
        hex += Character.forDigit((r % 16), 16);
        hex += Character.forDigit((g / 16), 16);
        hex += Character.forDigit((g % 16), 16);
        hex += Character.forDigit((b / 16), 16);
        hex += Character.forDigit((b % 16), 16);

        hex = hex.toUpperCase();

        return hex;
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