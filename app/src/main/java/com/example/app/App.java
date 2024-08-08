package com.example.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.database.DataBaseHelper;

public class App extends Application {
    private SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = new DataBaseHelper(getApplicationContext()).getWritableDatabase();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        database.close();
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }
}
