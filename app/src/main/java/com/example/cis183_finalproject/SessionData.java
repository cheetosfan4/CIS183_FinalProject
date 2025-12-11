package com.example.cis183_finalproject;

public class SessionData {
    private static User currentUser;
    private static boolean returnToSearch = false;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User u) {
        currentUser = u;
    }

    public static void setPaletteReturn(boolean b) {
        returnToSearch = b;
    }

    public static boolean getPaletteReturn() {
        return returnToSearch;
    }
}
