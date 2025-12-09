package com.example.cis183_finalproject;

import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Palette> paletteList;
    private ColorData favColor;

    public User() {

    }

    public User(String u, String p, List<Palette> pL, ColorData fC) {
        username = u;
        password = p;
        paletteList = pL;
        favColor = fC;
    }

    public void removeFromPaletteList(Palette p) {
        paletteList.remove(p);
    }
    public void addToPaletteList(Palette p) {
        paletteList.add(p);
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public List<Palette> getPaletteList() {
        return paletteList;
    }
    public ColorData getFavColor() {
        return favColor;
    }

    //setters
    public void setUsername(String u) {
        username = u;
    }
    public void setPassword(String p) {
        password = p;
    }
    public void setPaletteList(List<Palette> pL) {
        paletteList = pL;
    }
    public void setFavColor(ColorData fC) {
        favColor = fC;
    }
}