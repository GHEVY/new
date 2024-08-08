package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app.database.Countries;
import com.example.app.database.CursorWrapper;
import com.example.app.databinding.ActivityFavBinding;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity implements CountryAdapter.OnItemClickListener {
    private static final String extra = "extra";
    private static final int requestChange = 221;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFavBinding binding;
        binding = ActivityFavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recView1.setLayoutManager(layoutManager);
        adapter = new CountryAdapter(((App) getApplication()).getDatabase(), getItems(), this);
        binding.recView1.setAdapter(adapter);
        updateAdapter();
        if (!getItems().isEmpty()) {
            TextView a = findViewById(R.id.note);
            a.setVisibility(View.GONE);

        }
    }


    @Override
    public void onItemClick(ImageItem data) {
        Intent i = new Intent(FavActivity.this, CountryActivity.class);
        i.putExtra(extra, data.getId().toString());
        startActivityForResult(i, requestChange);
    }

    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        updateAdapter();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateAdapter() {
        adapter.setData(getItems());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView a = findViewById(R.id.note);
        if (getItems().isEmpty()) {
            a.setVisibility(View.VISIBLE);
        }
        else {
            a.setVisibility(View.INVISIBLE);

        }
        updateAdapter();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public ArrayList<ImageItem> getItems() {
        ArrayList<ImageItem> list = new ArrayList<>();
        try (CursorWrapper cursor = query()) {
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

    private CursorWrapper query() {
        Cursor cursor = ((App) getApplication()).getDatabase().query(
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