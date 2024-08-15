package com.example.app.database;

import android.database.Cursor;

import com.example.app.CountryItem;

import java.util.UUID;

public class CursorWrapper extends android.database.CursorWrapper {
    public CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public CountryItem getItem() {
        String uuidString = getString(getColumnIndex(Countries.Table.Cols.id));
        String name = getString(getColumnIndex(Countries.Table.Cols.country_name));
        String continent = getString(getColumnIndex(Countries.Table.Cols.continents));
        int photo = getInt(getColumnIndex(Countries.Table.Cols.photo));
        int isFav = getInt(getColumnIndex(Countries.Table.Cols.isFav));
        UUID id = null;
        if (uuidString != null) {
            try {
                id = UUID.fromString(uuidString);
            } catch (IllegalArgumentException ignored) {
            }
        }
        CountryItem item = new CountryItem(id);
        item.setName(name != null ? name : "");
        item.setContinent(continent != null ? continent : "");
        item.setFavorite(isFav == 1);
        item.setImage(photo);
        return item;
    }
}
