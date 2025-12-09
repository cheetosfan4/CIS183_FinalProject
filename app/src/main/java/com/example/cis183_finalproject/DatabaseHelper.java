package com.example.cis183_finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "ColorApp.db";
    private static final String users_table_name = "Users";
    private static final String colors_table_name = "Colors";
    private static final String palettes_table_name = "Palettes";

    public DatabaseHelper(Context c) {
        super(c, database_name, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //palettes will have a comma separated string of hex values for colorList
        //users will have a comma separated string of paletteIDs for paletteList
        //will have a function that parses the comma separated strings into actual lists
        db.execSQL("CREATE TABLE " + users_table_name + " (username varchar(50) primary key not null, password varchar(50), paletteList varchar(255), favColor varchar(50), foreign key (favColor) references " + colors_table_name + " (hex));");
        db.execSQL("CREATE TABLE " + colors_table_name + " (hex varchar(50) primary key not null, name varchar(50), author varchar(50), foreign key (author) references " + users_table_name + " (username));");
        db.execSQL("CREATE TABLE " + palettes_table_name + " (paletteID integer primary key autoincrement not null, colorList varchar(255), author varchar(50), foreign key (author) references " + users_table_name + " (username));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + users_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + colors_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + palettes_table_name + ";");
        onCreate(db);
    }

    //====== dummy data functions ==================================================================
    public void fakeData() {
        fakeUsers();
        fakeColors();
        fakePalettes();
    }

    private void fakeUsers() {
        if (countRecordsFromTable(users_table_name) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            //2,1,2: 2 palettes, paletteID=1, paletteID=2
            db.execSQL("INSERT INTO " + users_table_name + " (username, password, paletteList, favColor) VALUES ('testusername', 'testpassword', '2,1,2', 'FFFFFF');");

            db.close();
        }
    }

    private void fakeColors() {
        if (countRecordsFromTable(colors_table_name) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("INSERT INTO " + colors_table_name + " (hex, name, author) VALUES ('FFFFFF', 'White', 'testusername');");

            db.close();
        }
    }

    private void fakePalettes() {
        if (countRecordsFromTable(palettes_table_name) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("INSERT INTO " + palettes_table_name + " (colorList, author) VALUES ('2,FFFFFF,000000', 'testusername');");
            db.execSQL("INSERT INTO " + palettes_table_name + " (colorList, author) VALUES ('4,45DE12,AB12DE,1245DE,DEAB12', 'testusername');");

            db.close();
        }
    }

    private int countRecordsFromTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int records = (int) DatabaseUtils.queryNumEntries(db, tableName);

        db.close();
        return records;
    }

    //====== user functions ========================================================================

    public Boolean checkCredentials(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        //returns 1 when the username and password match an entry in the database
        String query = "SELECT 1 FROM " + users_table_name + " WHERE username = '" + user + "' AND password = '" + pass + "' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        boolean found = cursor.moveToFirst();
        cursor.close();
        db.close();
        return found;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + users_table_name + " WHERE username = '" + username + "' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();
        //placeholder palettelist and favcolor until i add in the ability to read them from the database
        List<Palette> paletteList = null;
        ColorData favColor = null;

        if (cursor.moveToFirst()) {
            do {
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setPaletteList(paletteList);
                user.setFavColor(favColor);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return user;
    }

    public void addUserToDatabase(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String username = user.getUsername();
        String password = user.getPassword();

        String information = "'" + username + "', '" + password + "'";
        db.execSQL("INSERT INTO " + users_table_name + " (username, password) VALUES (" + information + ");");
        db.close();
    }

    //====== color functions =======================================================================

    public void addColorToDatabase(ColorData color) {
        SQLiteDatabase db = this.getWritableDatabase();
        String hex = color.getHex();
        String name = color.getName();
        String author = color.getAuthor().getUsername();

        String information = "'" + hex + "', '" + name + "', '" + author +"'";
        db.execSQL("INSERT INTO " + colors_table_name + " (hex, name, author) VALUES (" + information + ");");
        db.close();
    }

    public ColorData getColor(String hex) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + colors_table_name + " WHERE hex = '" + hex + "' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        ColorData color = new ColorData();

        if (cursor.moveToFirst()) {
            do {
                color.setHex(cursor.getString(0));
                color.setName(cursor.getString(1));
                color.setAuthor(getUser(cursor.getString(2)));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return color;
    }

    //====== palette functions =====================================================================



    //====== getters ===============================================================================
    public String getUsersTableName() {
        return users_table_name;
    }
    public String getColorsTableName() {
        return colors_table_name;
    }
    public String getPalettesTableName() {
        return palettes_table_name;
    }
}