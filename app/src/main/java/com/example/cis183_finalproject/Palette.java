package com.example.cis183_finalproject;

import java.io.Serializable;
import java.util.List;

public class Palette implements Serializable {
    private int paletteID;
    private List<ColorData> colorList;
    private User author;

    public Palette() {

    }

    public Palette(int ID, List<ColorData> cL, User a) {
        paletteID = ID;
        colorList = cL;
        author = a;
    }

    public void removeFromColorList(ColorData c) {
        colorList.remove(c);
    }
    public void addToColorList(ColorData c) {
        colorList.add(c);
    }

    //getters
    public int getPaletteID() {
        return paletteID;
    }
    public List<ColorData> getColorList() {
        return colorList;
    }
    public User getAuthor() {
        return author;
    }

    //setters
    public void setPaletteID(int ID) {
        paletteID = ID;
    }
    public void setColorList(List<ColorData> cL) {
        colorList = cL;
    }
    public void setAuthor(User a) {
        author = a;
    }
}
