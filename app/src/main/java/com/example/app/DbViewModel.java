package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.example.app.database.Countries;
import com.example.app.database.CursorWrapper;
import com.example.app.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.UUID;

public class DbViewModel extends ViewModel {
    private SQLiteDatabase database;
    public Observable observable = new Observable();
    public void closeDb() {
        database.close();
    }

    private CursorWrapper query(String[] whereArgs) {
        Cursor cursor = database.query(
                Countries.Table.name,
                null,
                "id = ?",
                whereArgs,
                null,
                null,
                null);
        return new CursorWrapper(cursor);
    }

    public ContentValues getContentValues(CountryItem item) {
        ContentValues values = new ContentValues();
        values.put(Countries.Table.Cols.id, item.getId().toString());
        values.put(Countries.Table.Cols.country_name, item.getName());
        values.put(Countries.Table.Cols.continents, item.getContinent());
        values.put(Countries.Table.Cols.isFav, item.isFavorite() ? 1 : 0);
        values.put(Countries.Table.Cols.photo, item.getImage());
        return values;
    }


    public void update(Context context, CountryItem a) {
        initDb(context);

        ContentValues values = getContentValues(a);
        String selection = Countries.Table.Cols.id + " = ?";
        String[] selectionArgs = {a.getId().toString()};
        database.update(Countries.Table.name, values, selection, selectionArgs);
        observable.notifyObservers();
    }
    public void addToDb(Context context,CountryItem item){
        initDb(context);
        ContentValues values = getContentValues(item);
        database.insert(Countries.Table.name,null,values);
        observable.notifyObservers();
    }

    private void initDb(Context context){
        if (database == null) {
            database = new DataBaseHelper(context).getWritableDatabase();
        }
    }

    @Nullable
    public CountryItem getItem(Context context, UUID id) {

        initDb(context);

        if (id == null) {
            return null;
        }
        try (CursorWrapper cursor = query(new String[]{id.toString()})) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getItem();
        }
    }
    
    public List<CountryItem> getItems(Context context,String cont) {
        initDb(context);
        ArrayList<CountryItem> list = new ArrayList<>();
        try (CursorWrapper cursor = queryFavorites()) {
            cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    if (cursor.getItem() != null && Objects.equals(cursor.getItem().getContinent(), cont)) {
                        list.add(cursor.getItem());
                    }
                    cursor.moveToNext();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CountryItem> getFavoriteItems(Context context) {
        initDb(context);

        ArrayList<CountryItem> list = new ArrayList<>();
        try (CursorWrapper cursor = queryFavorites()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getItem().isFavorite()) {
                    list.add(cursor.getItem());
                }
                cursor.moveToNext();
            }
        }
        return list;
    }

    private CursorWrapper queryFavorites() {
        Cursor cursor = database.query(
                Countries.Table.name,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new CursorWrapper(cursor);
    }
}