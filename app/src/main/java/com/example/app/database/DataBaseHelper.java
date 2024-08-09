package com.example.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "CountriesBase.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Countries.Table.name + "(" +
                " _id integer primary key autoincrement, " +
                Countries.Table.Cols.id + "," +
                Countries.Table.Cols.continents + ", " +
                Countries.Table.Cols.country_name + ", " +
                Countries.Table.Cols.isFav + "," +
                Countries.Table.Cols.photo +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
