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
    private ImageItem imageItem;
    private String newText;
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
        imageItem = getItem(id);
        binding.info.setText("The continent of " + imageItem.getText() + " is " + imageItem.getContinent());
        binding.imagetext.setText(imageItem.getText());
        binding.imagebutton.setImageResource(imageItem.getImage());
        if (imageItem.isFavorite()) {
            binding.Favbut.setChecked(true);
        }
        binding.Favbut.setOnClickListener(v -> {
            if (imageItem.isFavorite()) {
                imageItem.setFavorite(false);
                binding.Favbut.setChecked(false);
                Toast.makeText(getBaseContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
            } else {
                imageItem.setFavorite(true);
                binding.Favbut.setChecked(true);
                Toast.makeText(getBaseContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.link.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com"));
            startActivity(browserIntent);
        });
        newText = imageItem.getText();
        binding.imagetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                newText = s.toString();
            }
        });
        binding.but.setOnClickListener(v -> {
            if (newText != null) {
                String b = newText.replace(" ", "");
                if (!b.isEmpty()) {
                    imageItem.setText(newText);
                    update(imageItem);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Write the name of a country!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ImageItem getItem(UUID id) {
        if (id == null) {
            Toast.makeText(getBaseContext(),"s",Toast.LENGTH_SHORT).show();
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

    private static ContentValues getContentValues(ImageItem item) {
        ContentValues values = new ContentValues();
        values.put(Countries.Table.Cols.id, item.getId().toString());
        values.put(Countries.Table.Cols.country_name, item.getText());
        values.put(Countries.Table.Cols.continents, item.getContinent());
        values.put(Countries.Table.Cols.isFav, item.isFavorite()? 1 : 0);
        return values;
    }


    public static void update(ImageItem a) {
        ContentValues values = getContentValues(a);
        String selection = Countries.Table.Cols.id + " = ?";
        String[] selectionArgs = {a.getId().toString()};
        database.update(Countries.Table.name, values, selection, selectionArgs);
    }
}

