package com.example.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app.database.Countries;
import com.example.app.database.CursorWrapper;
import com.example.app.databinding.ActivityContinentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ContinentActivity extends AppCompatActivity implements CountryAdapter.OnItemClickListener {

    private static final String DATA_KEY = "data_key";
    private static final int REQUEST_CODE_ADD = 124;
    private SQLiteDatabase database;
    private String continent;
    private CountryAdapter adapter;

    public static Intent newIntent(Context packageContext, String continent) {
        Intent intent = new Intent(packageContext, ContinentActivity.class);
        intent.putExtra(DATA_KEY, continent);
        return intent;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContinentBinding binding = ActivityContinentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = ((App) getApplication()).getDatabase();
        continent = getIntent().getStringExtra(DATA_KEY);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recView.setLayoutManager(layoutManager);
        binding.add.setOnClickListener(v -> {
            Intent intent = AddActivity.newIntent(this);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
        if (getItems(continent) != null) {
            adapter = new CountryAdapter(database, getItems(continent), this);
            binding.recView.setAdapter(adapter);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter() {
        adapter.setData(getItems(continent));
        adapter.notifyDataSetChanged();
    }

    public void add(ImageItem a) {
        ContentValues values = getContentValues(a);
        database.insert(Countries.Table.name, null, values);
    }

    public void update(ImageItem a) {
        ContentValues values = getContentValues(a);
        String selection = Countries.Table.Cols.id + " = ?";
        String[] selectionArgs = {a.getId().toString()};
        database.update(Countries.Table.name, values, selection, selectionArgs);
    }

    public CursorWrapper query(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                Countries.Table.name,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapper(cursor);
    }

    public List<ImageItem> getItems(String cont) {
        ArrayList<ImageItem> list = new ArrayList<>();
        try (CursorWrapper cursor = query(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getItem() != null && Objects.equals(cursor.getItem().getContinent(), cont)) {
                    list.add(cursor.getItem());
                }
                cursor.moveToNext();
            }
        }
        return list;
    }


    public static ContentValues getContentValues(ImageItem item) {
        ContentValues values = new ContentValues();
        values.put(Countries.Table.Cols.id, item.getId().toString());
        values.put(Countries.Table.Cols.country_name, item.getText());
        values.put(Countries.Table.Cols.continents, item.getContinent());
        values.put(Countries.Table.Cols.isFav, item.isFavorite() ? 1 : 0);
        values.put(Countries.Table.Cols.photo, item.getImage());
        return values;
    }

    @Override
    public void onItemClick(ImageItem item) {
        Intent i = CountryActivity.newIntent(getApplicationContext(), item.getId().toString());
        startActivity(i);
    }

    public void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (data != null) {
            if (req == REQUEST_CODE_ADD) {
                UUID uuid = UUID.fromString(data.getStringExtra("id"));
                String text = data.getStringExtra("text");
                boolean isFav = data.getIntExtra("isFav", 0) == 1;
                ImageItem a = new ImageItem(UUID.randomUUID());
                int photo = data.getIntExtra("photo", 0);
                a.setImage(photo);
                a.setId(uuid);
                a.setText(text);
                a.setContinent(continent);
                a.setFavorite(isFav);
                add(a);
            }
        }
        updateAdapter();

    }
    @Override
    public  void onResume(){
        super.onResume();
        updateAdapter();
    }
}
