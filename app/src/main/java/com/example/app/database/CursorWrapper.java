package com.example.app.database;

import android.database.Cursor;

import com.example.app.ImageItem;

import java.util.UUID;

public class CursorWrapper extends android.database.CursorWrapper {
    public CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ImageItem getItem() {
        String uuidString = getString(getColumnIndex(Countries.Table.Cols.id));
        String name = getString(getColumnIndex(Countries.Table.Cols.country_name));
        String continent = getString(getColumnIndex(Countries.Table.Cols.continents));
        int photo  = getInt(getColumnIndex(Countries.Table.Cols.photo));
        int isfav =getInt( getColumnIndex(Countries.Table.Cols.isFav));
        UUID id = null;
        if (uuidString != null) {
            try {
                id = UUID.fromString(uuidString);
            } catch (IllegalArgumentException ignored) {
            }
        }
        ImageItem item = new ImageItem(id);
        item.setText(name != null ? name : "");
        item.setContinent(continent != null ? continent : "");
        item.setFavorite(isfav==1);
        item.setImage(photo);
        return item;
    }
}
