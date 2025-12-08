package com.example.cis183_finalproject;

public class SessionData {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User u) {
        currentUser = u;
    }
}
