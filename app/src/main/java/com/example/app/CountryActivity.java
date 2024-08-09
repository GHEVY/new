package com.example.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.database.Countries;
import com.example.app.database.CursorWrapper;
import com.example.app.databinding.ActivityCountryBinding;

import java.util.UUID;

public class CountryActivity extends AppCompatActivity {
    private static final String extra = "extra";
    private ActivityCountryBinding binding;
    private CountryItem countryItem;
    private String newName;
    private static SQLiteDatabase database;

    public static Intent newIntent(Context packageContext, String a) {
        Intent intent = new Intent(packageContext, CountryActivity.class);
        intent.putExtra(extra, a );
        return intent;
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = ((App)getApplication()).getDatabase();
        UUID id = UUID.fromString(getIntent().getStringExtra(extra));
        countryItem = getItem(id);
        binding.info.setText("The continent of " + countryItem.getName() + " is " + countryItem.getContinent());
        binding.nameText.setText(countryItem.getName());
        binding.flagButton.setImageResource(countryItem.getImage());
        if (countryItem.isFavorite()) {
            binding.FavBut.setChecked(true);
        }
        binding.FavBut.setOnClickListener(v -> {
            if (countryItem.isFavorite()) {
                countryItem.setFavorite(false);
                binding.FavBut.setChecked(false);
                Toast.makeText(getBaseContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
            } else {
                countryItem.setFavorite(true);
                binding.FavBut.setChecked(true);
                Toast.makeText(getBaseContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.link.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com"));
            startActivity(browserIntent);
        });
        newName = countryItem.getName();
        binding.nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newName = s.toString();
            }
        });
        binding.saveButton.setOnClickListener(v -> {
            if (newName != null) {
                String changedName = newName.replace(" ", "");
                if (!changedName.isEmpty()) {
                    countryItem.setName(newName);
                    update(countryItem);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), R.string.name_is_incorrect, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), R.string.name_is_incorrect, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CountryItem getItem(UUID id) {
        if (id == null) {
            return null;
        }
        try (CursorWrapper cursor = query(
                new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getItem();
        }
    }

    private CursorWrapper query(String[] whereArgs) {
        Cursor cursor = database.query(
                Countries.Table.name,
                null,
                "id = ?",
                whereArgs,
                null,
                null,
                null
        );
        return new CursorWrapper(cursor);
    }

    private static ContentValues getContentValues(CountryItem item) {
        ContentValues values = new ContentValues();
        values.put(Countries.Table.Cols.id, item.getId().toString());
        values.put(Countries.Table.Cols.country_name, item.getName());
        values.put(Countries.Table.Cols.continents, item.getContinent());
        values.put(Countries.Table.Cols.isFav, item.isFavorite()? 1 : 0);
        return values;
    }


    public static void update(CountryItem a) {
        ContentValues values = getContentValues(a);
        String selection = Countries.Table.Cols.id + " = ?";
        String[] selectionArgs = {a.getId().toString()};
        database.update(Countries.Table.name, values, selection, selectionArgs);
    }
}

