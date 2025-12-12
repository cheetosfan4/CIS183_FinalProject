package com.example.cis183_finalproject;

import android.graphics.Color;

import java.io.Serializable;
import java.util.List;

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

    public static int getRedFromHex(String hex) {
        int first = Character.digit(hex.charAt(0),16);
        int second = Character.digit(hex.charAt(1), 16);

        return (first * 16) + second;
    }
    public static int getGreenFromHex(String hex) {
        int first = Character.digit(hex.charAt(2),16);
        int second = Character.digit(hex.charAt(3), 16);

        return (first * 16) + second;
    }
    public static int getBlueFromHex(String hex) {
        int first = Character.digit(hex.charAt(4),16);
        int second = Character.digit(hex.charAt(5), 16);

        return (first * 16) + second;
    }

    public static int colorToInt(ColorData color) {
        String hex = color.getHex();
        int r = ColorData.getRedFromHex(hex);
        int g = ColorData.getGreenFromHex(hex);
        int b = ColorData.getBlueFromHex(hex);
        return Color.rgb(r, g, b);
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