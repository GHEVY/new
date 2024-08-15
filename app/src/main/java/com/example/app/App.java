package com.example.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.database.DataBaseHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
